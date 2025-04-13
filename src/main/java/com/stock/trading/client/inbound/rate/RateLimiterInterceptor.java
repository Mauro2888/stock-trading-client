package com.stock.trading.client.inbound.rate;

import com.stock.trading.client.inbound.exception.RateLimitExceededException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@Aspect
public class RateLimiterInterceptor {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final HttpServletRequest httpServletRequest;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RateLimiterInterceptor(HttpServletRequest httpServletRequest,
                                  RedisTemplate<String, Object> redisTemplate) {
        this.httpServletRequest = httpServletRequest;
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(rateLimiter)")
    public Object before(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) throws Throwable {
        int requests = rateLimiter.requests();
        long duration = rateLimiter.duration();

        var uri = httpServletRequest.getRequestURI();
        var key = httpServletRequest.getRemoteAddr() + ":" + uri;

        var currentRequests = redisTemplate.opsForValue().increment(key, 1);
        if (nonNull(currentRequests) && currentRequests == 1) {
            redisTemplate.expire(key, duration, TimeUnit.SECONDS);
            log.info(() -> "Expiring key %s for %s seconds".formatted(key, duration));
        }

        // Get TTL for headers
        Long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (isNull(ttl) || ttl < 0) {
            ttl = duration; // Default if TTL couldn't be determined
        }

        if (nonNull(currentRequests) && currentRequests > requests) {
            log.warning(() -> "Rate limit exceeded for key %s: %d requests in %d seconds".formatted(key, currentRequests, duration));
            var response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
            if (response != null) {
                // Add standard rate limiting headers
                response.setHeader("X-RateLimit-Limit", String.valueOf(requests));
                response.setHeader("X-RateLimit-Remaining", "0");
                response.setHeader("X-RateLimit-Reset", String.valueOf(ttl));
                response.setHeader("Retry-After", String.valueOf(ttl));
            }

            throw new RateLimitExceededException("Rate limit exceeded");
        }

        // Add headers for successful requests too
        var response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        if (response != null) {
            response.setHeader("X-RateLimit-Limit", String.valueOf(requests));
            response.setHeader("X-RateLimit-Remaining", String.valueOf(requests - currentRequests));
            response.setHeader("X-RateLimit-Reset", String.valueOf(ttl));
        }

        return joinPoint.proceed();
    }
}

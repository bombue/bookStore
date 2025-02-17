package ru.akiselev.bookStore.untils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(name = "aop.logging.enabled", matchIfMissing = false)
public class LoggingAspect {

    // будем логировать вход/выход всех паблик методов
    @Pointcut("execution(public * ru.akiselev.bookStore.*.*.*(..))")
    private void everyPublicMethodForLogging() {
    }

    @Around(value = "everyPublicMethodForLogging()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.debug(">> {}() - {}", methodName, Arrays.toString(args));
        Object result = joinPoint.proceed();
        log.debug("<< {}() - {}", methodName, result);
        return result;
    }
}

/**
 * Method logging aspect. This contains portions of code from http://five.agency/logging-with-spring-aop.
 */
package com.tonitealive.server.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MethodLoggingAspect {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Around("execution(* *(..)) && @annotation(com.tonitealive.server.annotations.DebugLog)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object retVal = joinPoint.proceed();

        stopWatch.stop();

        StringBuilder message = new StringBuilder();
        message.append(joinPoint.getTarget().getClass().getName());
        message.append(".");
        message.append(joinPoint.getSignature().getName());
        message.append("(");

        // Append args
        Object[] args = joinPoint.getArgs();
        for (Object arg : args)
            message.append(arg).append(",");

        if (args.length > 0)
            message.deleteCharAt(message.length() - 1);

        message.append(")");
        message.append(" execution time: ");
        message.append(stopWatch.getTotalTimeMillis());
        message.append(" ms");

        log.debug(message.toString());

        return retVal;
    }

}

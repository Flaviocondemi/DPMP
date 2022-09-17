package com.flavio.dpmp.Utils;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ExecutionTimerTrackerAdvice {

    @Autowired
    MeterRegistry meterRegistry;

    List<Long> list;

    public ExecutionTimerTrackerAdvice(){
        list = new ArrayList<>();
    }


    @Around("@annotation(com.flavio.dpmp.Utils.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getMethod().getName();

        Gauge gauge = Gauge
                .builder("response_time_" + methodName, list, value -> list.get(list.size() - 1))
                .tags("response time endpoints: " + methodName, "list of response time of an endpoint")
                .register(meterRegistry);

        long startTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        list.add(responseTime);

        System.out.println("Endpoint  " + methodName + " - " + " Response time: " + responseTime + "ms");
        System.out.println("Gauge value: " + gauge.value());
        return obj;
    }
}

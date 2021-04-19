package ShallWe.Refactoring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AopTimeLogger {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private long timer = 0L;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void GetMapping() {

    }

    @Before("GetMapping()")
    public void before(JoinPoint joinPoint) {
        timer = System.currentTimeMillis();
    }

    @AfterReturning(pointcut = "GetMapping()", returning = "result")
    public void AfterReturning(JoinPoint joinPoint, Object result) {
        long runtime = System.currentTimeMillis() - timer;
        String className = joinPoint.getTarget().toString();
        String methodName = joinPoint.getSignature().getName();
        logger.info(className);
        logger.info(methodName + " Running Time : " + runtime);
    }
}

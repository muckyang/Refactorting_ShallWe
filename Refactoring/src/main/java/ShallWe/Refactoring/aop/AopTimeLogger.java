package ShallWe.Refactoring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    public void afterGet(JoinPoint joinPoint, Object result) {
        long runtime = System.currentTimeMillis() - timer;
        String className = joinPoint.getTarget().toString();
        String methodName = joinPoint.getSignature().getName();
        logger.info(className);
        logger.info(methodName + " Running Time : " + runtime);
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void PostMapping() {

    }

    @Around("PostMapping()")
    public Object AroundPost(ProceedingJoinPoint joinPoint) throws Throwable {
        timer = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long runtime = System.currentTimeMillis() - timer;
            String className = joinPoint.getTarget().toString();
            String methodName = joinPoint.getSignature().getName();
            logger.info(className);
            logger.info(methodName + " Running Time : " + runtime);
        }
    }

 

}

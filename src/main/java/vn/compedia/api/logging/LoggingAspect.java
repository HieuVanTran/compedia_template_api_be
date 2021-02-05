package vn.compedia.api.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

@Aspect
public class LoggingAspect {

    @Around(value = "@within(vn.compedia.api.logging.Loggable) || @annotation(vn.compedia.api.logging.Loggable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Loggable loggableMethod = method.getAnnotation(Loggable.class);

        Loggable loggableClass = proceedingJoinPoint.getTarget().getClass().getAnnotation(Loggable.class);

        //get current log level
        Loggable.Level level = loggableMethod != null ? loggableMethod.level() : Loggable.Level.INFO;


        //before
        Class clazz = proceedingJoinPoint.getTarget().getClass();
        Logger logger = getLogger(proceedingJoinPoint);
        long startTime = System.currentTimeMillis();
        logger.info("{} execute with method = {} start: {}", clazz, method.getName(), startTime);

        //show params
        if (proceedingJoinPoint.getArgs() != null && proceedingJoinPoint.getArgs().length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < proceedingJoinPoint.getArgs().length; i++) {
                sb.append(method.getParameterTypes()[i].getName() + ":" + proceedingJoinPoint.getArgs()[i]);
                if (i < proceedingJoinPoint.getArgs().length - 1)
                    sb.append(", ");
            }
            writeLog(logger, level, clazz, method, sb);
        }

        //start method execution
        Object result = proceedingJoinPoint.proceed();

        //show results
        if (result != null) {
            boolean showResults = loggableMethod != null ? loggableMethod.result() : loggableClass.result();
            if (showResults) {
                logger.info("{} execute with method = {} result: {}", clazz, method.getName(), result);
            }
        }

        //show after
        long endTime = System.currentTimeMillis();
        logger.info("{} execute with method = {} end: {} duration: {}", clazz, method.getName(), endTime, endTime - startTime);

        return result;
    }

    private Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
    }

    private void writeLog(Logger logger, Loggable.Level level, Class clazz, Method method, Object sb) {
        switch (level) {
            case DEBUG:
                logger.debug("{} execute with method = {} content: {}", clazz, method.getName(), sb);
                break;
            case ERROR:
                logger.error("{} execute with method = {} content: {}", clazz, method.getName(), sb);
                break;
            case WARN:
                logger.warn("{} execute with method = {} content: {}", clazz, method.getName(), sb);
                break;
            default:
                logger.info("{} execute with method = {} content: {}", clazz, method.getName(), sb);
                break;
        }
    }
}

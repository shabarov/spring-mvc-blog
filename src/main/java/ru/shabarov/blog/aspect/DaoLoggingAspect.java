package ru.shabarov.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class DaoLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* ru.shabarov.blog.dao.AbstractDao.create(..))")
    public void create() {
    }

    @Before("create()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before create() method");
        logger.info(joinPoint.getArgs()[0].toString());
    }

    @After("create()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("After create() method");
    }
}

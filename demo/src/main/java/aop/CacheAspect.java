package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class CacheAspect {
    private Map<Long,Object> cache = new HashMap<>();

    @Pointcut("execution(public * chap07..*(long))")
    public void cacheTarget(){
    }

    @Around("cacheTarget()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        Long num = (Long) joinPoint.getArgs()[0];
        if(cache.containsKey(num)){
            System.out.println("cache "+num);
            return cache.get(num);
        }

        Object result = joinPoint.proceed();
        cache.put(num,result);
        System.out.println("put cache "+num);
        return result;
    }
}

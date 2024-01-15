package config;

import aop.CacheAspect;
import aop.ExeTimeAspect;
import chap07.Calculator;
import chap07.ImpeCalculator;
import chap07.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

@Configuration
@EnableAspectJAutoProxy
public class AppCtxWithCache {
    @Bean
    public CacheAspect cacheAspect(){
        return new CacheAspect();
    }

    @Bean
    @Order
    public ExeTimeAspect exeTimeAspect(){
        return new ExeTimeAspect();
    }

    @Bean
    public Calculator calculator(){
        return new RecCalculator();
    }
}

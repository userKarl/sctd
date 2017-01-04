package com.sc.td.frame.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect 
public class ExceptionAspect {
	
		private Logger log = LoggerFactory.getLogger(getClass());
		
	 	@Pointcut("execution(* com.sc.td..*.*(..))")  
	    public void init(){  
	          
	    }  
	  
	    @AfterThrowing(pointcut="init()",throwing = "e")  
	    public void throwss(JoinPoint jp , Throwable e){  
	    	String className = jp.getThis().toString();  
	        String methodName = jp.getSignature().getName(); 
	        e.printStackTrace();
	        log.error(className+"."+methodName+" Exception:"+e.getMessage());	        
	    }   
}

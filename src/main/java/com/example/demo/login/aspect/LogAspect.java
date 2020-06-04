package com.example.demo.login.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	
	@Around("execution( * *..*.*Controller.*(..))")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("メソッド開始：" + jp.getSignature());
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了：" + jp.getSignature());
			return result;
		} catch(Exception e) {
			System.out.println("メソッド異常終了：" + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
	
<<<<<<< HEAD
	@Around("execution( * *..*.UserService.*(..))")
=======
	@Around("execution( * *..*.*Service.*(..))")
>>>>>>> origin/master
	public Object serviceLog(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("メソッド開始：" + jp.getSignature());
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了：" + jp.getSignature());
			return result;
		} catch(Exception e) {
			System.out.println("メソッド異常終了：" + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
	
<<<<<<< HEAD
	@Around("execution( * *..*.*UserDao*.*(..))")
=======
	@Around("execution( * *..*.*Dao*.*(..))")
>>>>>>> origin/master
	public Object daoLog(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("メソッド開始：" + jp.getSignature());
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了：" + jp.getSignature());
			return result;
		} catch(Exception e) {
			System.out.println("メソッド異常終了：" + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
<<<<<<< HEAD
=======
	
	@Around("execution( * *..*.UserMapper*.*(..))")
	public Object mapperLog(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("メソッド開始：" + jp.getSignature());
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了：" + jp.getSignature());
			return result;
		} catch(Exception e) {
			System.out.println("メソッド異常終了：" + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
>>>>>>> origin/master
}

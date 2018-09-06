package com.liupeiiqng.spring.cloud.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 *  请求认证切面，验证自定义请求header的authtoken是否合法
 * @author liupeiqing
 * @data 2018/8/10 12:07
 *
 * 解释：执行到核心业务方法或者类时，会先执行AOP。
 * 在aop的逻辑内，先走@Around注解的方法。
 * 然后是@Before注解的方法，然后这两个都通过了，走核心代码，核心代码走完，无论核心有没有返回值，
 * 都会走@After方法。
 * 然后如果程序无异常，
 * 正常返回就走@AfterReturn,
 * 有异常就走@AfterThrowing。
 */
@Aspect  //表示是一个切面
@Component  //放入容器
public class PermissionAspect {

    /**
     * 常用的Pointcut定义有 execution 和 @annotation 两种。
     * execution 定义对方法无侵入，用于实现比较通用的切面。
     * @annotation 可以作为注解加到特定的方法上，例如Spring的Transaction注解。也可以是自定义的的注解
     */
    @Pointcut(value = "@annotation(com.liupeiiqng.spring.cloud.annotation.Permission)")  //特定的注解上
    public void requestMapping(){}

    /**
     * execution()是最常用的切点函数，其语法如下所示：
     *
     *  整个表达式可以分为五个部分：
     *
     *  1、execution(): 表达式主体。
     *
     *  2、第一个*号：表示返回类型，*号表示所有的类型。
     *
     *  3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.liupeiiqng.spring.cloud.controller.包、
     *         如果是两个点com.liupeiiqng.spring.cloud.controller..子孙包下所有类的方法。
     *
     *  4、第二个*号：表示类名，*号表示所有的类。
     *
     *  5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
     */
    @Pointcut("execution(* com.liupeiiqng.spring.cloud.controller.*Controller.*(..))")
    public void methodPointCut() {
    }

    //环绕的测试
    @Around("requestMapping()")  //引入切点  实现切点方法
    public String doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.print("执行业务代码-around！");
        return (String) pjp.proceed();//继续执行被拦截的方法  去掉这个 后续的dobefore不会执行
    }

    //执行前
    @Before("requestMapping()")
    public String doBefore(JoinPoint joinPoint){
        System.out.print("doBefore方法认证开始...");
        Class type = joinPoint.getSignature().getDeclaringType();
        System.out.print("doBefore方法认证结束...");
        return "dobefore";
    }
}

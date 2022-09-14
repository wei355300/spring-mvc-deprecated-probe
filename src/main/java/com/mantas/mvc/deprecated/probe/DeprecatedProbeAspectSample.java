package com.mantas.mvc.deprecated.probe;

import com.mantas.mvc.deprecated.probe.saver.DeprecatedProbeSaver;
import com.mantas.mvc.deprecated.probe.saver.NullDeprecatedProbeSaver;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Objects;

/**
 * <b>样例代码, 不要用于正式业务(不要创建Bean的实例)</b> <br />
 *
 * 可参考代码, 自定义 Aspect 的实现 <br />
 *
 *
 *
 * 查找项目中特定标记的方法(api接口) <br />
 *
 * 查找
 *  1. com.mantas package及sub-package内 <br />
 *  2. @Controller 标记的Class <br />
 *  2. public 方法 <br />
 *  2. @Deprecated 标记的方法 <br />
 *  3. @DeprecatedProbe 标记的方法 <br />
 *
 *
 * @See: <a href="https%3A%2F%2Fdocs.spring.io%2Fspring-framework%2Fdocs%2Fcurrent%2Freference%2Fhtml%2Fcore.html%23aop-pointcuts">Declaring a Pointcut</a>
 */
@Slf4j
@Aspect
public class DeprecatedProbeAspectSample {

    @Setter
    private DeprecatedProbeSaver deprecatedProbeSaver;

    public DeprecatedProbeAspectSample() {
        this.deprecatedProbeSaver = new NullDeprecatedProbeSaver();
    }

    public DeprecatedProbeAspectSample(DeprecatedProbeSaver deprecatedProbeSaver) {
        if (Objects.isNull(deprecatedProbeSaver)) {
            deprecatedProbeSaver = new NullDeprecatedProbeSaver();
        }
        this.deprecatedProbeSaver = deprecatedProbeSaver;
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethodPointcut() {}

    @Pointcut("@target(org.springframework.stereotype.Controller)")
    public void controllerClassPointcut() {}

    @Pointcut("@annotation(Deprecated)")
    public void deprecatedPointcut() {}

    @Pointcut("@target(DeprecatedProbe)")
    public void deprecatedProbePointcut() {}

    @Pointcut("execution(* com.mantas..*.*(..))")
    public void inPackagePointcut() {}

    @Before("inPackagePointcut() && deprecatedProbePointcut() && publicMethodPointcut() && controllerClassPointcut() && deprecatedPointcut()")
    public void startDeprecatedMethod(JoinPoint jp) {
        Signature signature = jp.getSignature();
        if (signature instanceof MethodSignature) {
            dealMethodSignature((MethodSignature) signature);
        }
    }

    private void dealMethodSignature(MethodSignature signature) {
        deprecatedProbeSaver.save(signature);
    }
}

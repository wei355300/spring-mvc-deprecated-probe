# spring-mvc-deprecated-probe

记录调用了被标记为Deprecated的接口

基于spring-aop? | spring-mvc-handler-interceptor? 判断接口地址, 如果是被 @Deprecated 标注的接口, 则持久化存储起来

# 1. spring-aop

通过 Pointcut 指定 annotation
(被 @Deprecated 标注的method, 并且是 @Controller 的class)

```
@Pointcut("@annotation(java.lang.Deprecated)")
public void deprecatedPointcut() {}

@Pointcut("@target(org.springframework.stereotype.Controller)")
public void controllerClassPointcut() {}

@Before("deprecatedPointcut() && controllerClassPointcut()")
public void deprecatedMethod(ProceedingJoinPoint pjp) {
    // 通过 ProceedingJoinPoint 获取目标对象信息
    // 存储记录
}
```

[参考](https://www.baeldung.com/spring-aop-pointcut-tutorial)

## 用法

[参考](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop)

激活使用:

在 spring的配置文件中, 定义:
```
deprecated:
    probe: true
```
> DeprecatedProbeConfiguration.java 配置类, 基于 ConditionalOnProperty 条件初始化相应的Bean

### 启用spring的aop

- 在spring的 Configuration 上增加注解: `@EnableAspectJAutoProxy`  

- 实例化 被 @Aspect 注解 的Bean

```
@Configuration
@EnableAspectJAutoProxy
@SpringBootApplication
class SpringApplication {
    @Bean
    public DeprecatedAspect deprecatedAspect() {
        ...    
    }
}
```

### 持久化存储

自定义实现 `Saver` 接口, 在各自的业务系统中保存数据.


# 2. handler-interceptor

- 2.1 @Deprecated接口解析

通过 `RequestMappingHandlerMapping` 获取所有的 mapping,  
解析 mapping 记录的接口对应的method及被标注的annotation  
如果被 @Deprecated 标注了, 则将 method + uri 单独记录到map中,

```
@Bean
public AnalyBean analy(@Autowird RequestMappingHandlerMapping mapping) {
    Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
    ...
    
     //过滤出被 @Deprecated 标记的 method
    handlerMethod.getMethod().getDeclaredAnnotations();
    ...
    
    //过滤出 url
    requestMappingInfo.getPatternsCondition().getPatterns()
        .stream()
        .findFirst()
        .ifPresent(interfaceResources::setUrl);
}
```

- 2.2 request 拦截

自定义 HandlerInterceptor, 在处理请求时, 判断 request 的 url 及 http-method 是否匹配上 `2.1` 记录的map,  
如果能够匹配, 则存储记录

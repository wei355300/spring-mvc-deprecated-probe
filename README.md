# spring-mvc-deprecated-probe
记录调用了被标记为Deprecated的接口

基于spring-aop? | spring-mvc-handler-interceptor? 判断接口地址, 如果是被 @Deprecated 标注的接口, 则持久化存储起来


# 1. spring-aop

通过 Pointcut 指定 annotation 
(被 @Deprecated 标注的method, 并且是 @Controller 的class)  

```
@Pointcut("@annotation(java.lang.Deprecated)")
public void deprecatedPc() {}

@Before("deprecatedPc()")
public void deprecatedMethod() {
    // 存储记录
}
```

[参考](https://www.baeldung.com/spring-aop-pointcut-tutorial)


# 2. handler-interceptor

- 2.1 @Deprecated接口解析 

通过 `RequestMappingHandlerMapping` 获取所有的 mapping,  
解析 mapping 记录的接口对应的method及被标注的annotation  
如果被 @Deprecated 标注了, 则将 method + uri 单独记录到map中,  

- 2.2 request 拦截

自定义 HandlerInterceptor, 在处理请求时, 判断 request 的 url 及 http-method 是否匹配上 `2.1` 记录的map,  
如果能够匹配, 这存储记录

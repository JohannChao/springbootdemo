package com.johann.springbootaop.aspectConfig;

import cn.hutool.json.JSONUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: AopLog
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-16 17:10
 **/

@Slf4j
@Component
@Aspect
public class AopLog {

    private static final String START_TIME = "request-start";

    /** 
    * @Description: 切点
    * @Param: [] 
    * @return: void 
    * @Author: Johann 
    * @Date: 2020/9/16 
    */
    @Pointcut("execution(public * com.johann.springbootaop.controller.*Controller.*(..))")
    public void pointCutOfLog(){

    }

    /** 
    * @Description: 切点前置操作
    * @Param: [point] 
    * @return: void 
    * @Author: Johann 
    * @Date: 2020/9/16 
    */ 
    @Before("pointCutOfLog()")
    public void beforePointCut(JoinPoint point){

        log.info("######## Before Start ################");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        log.info("【请求 URL】：{}", request.getRequestURL());
        log.info("【请求 IP】：{}", request.getRemoteAddr());
        log.info("【请求 类名】：{}，【请求 方法名】：{}", point.getSignature().getDeclaringTypeName(),point.getSignature().getName());

        Map<String,String[]> paramMap = request.getParameterMap();
        log.info("【请求参数】：{}", JSONUtil.toJsonStr(paramMap));

        Long start = System.currentTimeMillis();
        request.setAttribute(START_TIME,start);
        log.info("######## Before End ################");
    }
    
    /** 
    * @Description: 环绕切点操作
    * @Param: [point] 
    * @return: java.lang.Object 
    * @Author: Johann 
    * @Date: 2020/9/16 
    */ 
    @Around("pointCutOfLog()")
    public Object arroundPointCut(ProceedingJoinPoint point){
        log.info("######## Around Start ################");
        Object o = null;
        try {
            o = point.proceed();
            log.info("【返回值】：{}",JSONUtil.toJsonStr(o));
        } catch (Throwable throwable) {
            log.error("【arroundPointCut Throwable】：{}"+throwable);
            throwable.printStackTrace();
        }finally {
            log.info("######## Around End ################");
            return  o;
        }
    }

    
    /** 
    * @Description: 切点后置操作
    * @Param: [] 
    * @return: void 
    * @Author: Johann 
    * @Date: 2020/9/16 
    */ 
    @After("pointCutOfLog()")
    public void afterPointCut(){
        log.info("######## After Start ################");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        Long start = (Long) request.getAttribute(START_TIME);
        Long end = System.currentTimeMillis();
        log.info("【请求耗时】：{}毫秒",end - start);

        String header = request.getHeader("User-Agent");

        UserAgent userAgent = UserAgent.parseUserAgentString(header);

        log.info("【浏览器类型】：{}，【操作系统】：{}，【原始User-Agent】：{}",userAgent.getBrowser().toString(),userAgent.getOperatingSystem().toString(),header);
        log.info("######## After End ################");
    }
}

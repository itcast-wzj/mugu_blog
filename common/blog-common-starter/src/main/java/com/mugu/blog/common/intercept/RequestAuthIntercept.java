//package com.mugu.blog.common.intercept;
//
//import com.mugu.blog.common.annotation.AuthInjection;
//import com.mugu.blog.core.model.BaseParam;
//import com.mugu.blog.core.model.LoginVal;
//import com.mugu.blog.core.model.RequestConstant;
//import com.mugu.blog.core.utils.RequestContextUtils;
//import com.sun.deploy.util.ArgumentParsingUtil;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Objects;
//
//@Component
//public class RequestAuthIntercept implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //获取用户信息，如果存在，则表示是通过令牌访问的，反之是白名单的，无需校验
//        Object obj = RequestContextUtils.getRequest().getAttribute(RequestConstant.LOGIN_VAL_ATTRIBUTE);
//        if (Objects.isNull(obj))
//            return true;
//        if (handler instanceof HandlerMethod){
//            HandlerMethod method=(HandlerMethod)handler;
//            LoginVal loginVal=(LoginVal)obj;
//            AuthInjection authInjection = method.getMethodAnnotation(AuthInjection.class);
//            if (Objects.isNull(authInjection))
//                return true;
//            //获取参数
//            Class<?>[] parameterTypes = method.getMethod().getParameterTypes();
//            if (parameterTypes.length==0)
//                return true;
//            Object instance = parameterTypes[0].newInstance();
//            if (!(instance instanceof BaseParam))
//                return true;
//            BaseParam baseParam=(BaseParam)instance;
//            baseParam.setUserId(loginVal.getUserId());
//        }
//        return true;
//    }
//}

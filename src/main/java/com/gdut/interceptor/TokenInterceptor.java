package com.gdut.interceptor;

import com.gdut.annotation.RequireRole;
import com.gdut.pojo.AdminOperationLog;
import com.gdut.service.AdminLogService;
import com.gdut.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    
    @Autowired
    private AdminLogService adminLogService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        
        // 只拦截 Controller 方法请求
        if (!(handler instanceof HandlerMethod)) {
            log.info("当前访问不是 Controller 方法，直接放行");
            return true;
        }

        //获取当前方法对象
        Method method = ((HandlerMethod) handler).getMethod();

        //判断是否有注解
        RequireRole requireRole = method.getAnnotation(RequireRole.class);

        if(requireRole == null){
            log.info("当前方法不需要权限，直接放行");
            return true;
        }else {  //需要权限

            String[] roles = requireRole.value();
            log.info("当前方法需要权限：{}",String.join(",", roles));

            //获取token并获得当前角色
            String token = request.getHeader("token");
            log.info("令牌:{}",token);

            //判断是否携带token
            if(token == null || token.isEmpty()){
                log.info("令牌为空，响应401");
                response.setStatus(401);
                return false;
            }

            //创建一个map集合接收token的自定义信息
            Map<String, Object> claims ;

            //判断令牌是否有效
            try{
                claims = JwtUtil.parseToken(token);

            }catch (Exception e){
                log.info("令牌无效，响应401");
                response.setStatus(401);
                return false;
            }

            //令牌有效
            if (JwtUtil.needRefresh( token)) {
                //刷新令牌
                String newToken = JwtUtil.refreshToken(token);
                if(newToken != null){
                    log.info("刷新令牌成功");
                    response.setHeader("X-New-Token",newToken);
                }

            }

            String role = claims.get("role").toString();
            log.info("当前用户角色：{}",role);

            if(roles[0].equals("*")){
                log.info("当前接口任意角色都可以访问");
                recordAdminOperationLog(request, method, claims);
                return true;
            }

            for (String r: roles){
                if(r.equals(role)){
                    log.info("当前用户角色匹配，放行");
                    recordAdminOperationLog(request, method, claims);
                    return true;
                }
            }
            //角色不匹配
            log.info("当前用户角色不匹配，响应 403");
            response.setStatus(403);
            return false;
        }

    }
    
    private void recordAdminOperationLog(HttpServletRequest request, Method method, Map<String, Object> claims) {
        String role = claims.get("role").toString();
        //判断是不是管理员
        if (!"admin".equals(role)) {
            return;
        }
        
        try {
            // 获取操作信息
            String operation = method.getDeclaringClass().getSimpleName() + "." + method.getName();
            String httpMethod = request.getMethod();
            //如果是查询操作的话就不保存
            if(httpMethod.equals("GET")){
                return  ;
            }
            String url = request.getRequestURI();
            String params = getRequestParamString(request);
            String ip = getClientIp(request);
            String adminId = claims.get("id").toString();
            //创建日志对象并添加到数据库
            AdminOperationLog logEntry = new AdminOperationLog(adminId, operation, httpMethod, url, params, ip);
            adminLogService.insert(logEntry);
            log.info("记录管理员操作日志：adminId={}, operation={}, url={}", adminId, operation, url);
        } catch (Exception e) {
            log.error("记录操作日志失败", e);
        }
    }
    
    private String getRequestParamString(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        Map<String, String[]> paramMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            if (!params.isEmpty()) {
                params.append("&");
            }
            params.append(entry.getKey()).append("=");
            String[] values = entry.getValue();
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    if (i > 0) {
                        params.append(",");
                    }
                    params.append(values[i]);
                }
            }
        }
        return params.toString();
    }
    
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

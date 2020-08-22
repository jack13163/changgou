package com.changgou.oauth.interceptor;

import com.changgou.oauth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Service;

/**
 * 解决微服务之间调用的认证问题
 * 在Feign调用之前执行apply方法，生成admin令牌
 * 并放入到请求头中
 */
@Service
public class TokenRequestInterceptor implements RequestInterceptor {
    /**
     * 在Feign调用之前执行
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 创建管理员令牌
        String adminToken = AdminToken.createAdminToken();
        // 将令牌放入到请求头中
        requestTemplate.header("Authorization", "bearer " + adminToken);
    }
}

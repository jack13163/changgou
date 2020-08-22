package com.changgou.gateway.filter;

import com.changgou.gateway.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器：需要实现GlobalFilter接口和Ordered接口
 * 用户权限鉴别过滤器
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    // 令牌头的名字
    private static final String AUTHORIZE_TOCKEN = "Authorization";

    /**
     * 全局过滤
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取Request、Response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 判断当前请求是否需要认证
        String uri = request.getURI().toString();
        if(!UrlFilter.hasAuthorize(uri)){
            // 若不需要认证，则直接放行
            return chain.filter(exchange);
        }

        // 获取用户令牌信息：可能存在于请求头、请求参数和Cookie中
        String jwt = request.getHeaders().getFirst(AUTHORIZE_TOCKEN);
        boolean jwtInHeader = true;// 标记jwt是否在请求头
        if (StringUtils.isEmpty(jwt)) {
            jwtInHeader = false;
            jwt = request.getQueryParams().getFirst(AUTHORIZE_TOCKEN);
            if (StringUtils.isEmpty(jwt)) {
                HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOCKEN);
                if (cookie != null) {
                    jwt = cookie.getValue();
                }
            }
        }

        // 没有令牌，则拦截
        if (StringUtils.isEmpty(jwt)) {
            // 设置401状态码，提示用户没有权限，用户收到该提示后需要重定向到登陆页面
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        } else {
            // 有令牌，判断令牌是否以bearer开头，若不是，则加上
            if(!jwt.startsWith("bearer") && !jwt.startsWith("bearer")){
                jwt = "bearer " + jwt;
            }
            // 判断令牌是否在请求头中，若不在，则将jwt令牌放入到请求头中，传递给其他微服务
            if(!jwtInHeader) {
                // 令牌正常解析，为了方便在其他微服务进行认证，这里将jwt放入到请求头中
                request.mutate().header(AUTHORIZE_TOCKEN, jwt);
            }
            // 放行
            return chain.filter(exchange);
        }
    }

    /**
     * 排序，越小越先执行
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
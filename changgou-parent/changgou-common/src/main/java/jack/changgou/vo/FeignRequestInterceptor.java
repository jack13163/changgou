package jack.changgou.vo;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * 解决微服务之间调用的认证问题
 * 在Feign调用之前执行apply方法，生成admin令牌
 * 并放入到请求头中
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    /**
     * 在Feign调用之前执行
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 记录了当前用户的所有请求数据，包括请求头和请求参数
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取请求头中的令牌数据
        Enumeration<String> headerNames = requestAttributes.getRequest().getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headerKey = headerNames.nextElement();
            String headerValue = requestAttributes.getRequest().getHeader(headerKey);
            System.out.println(headerKey + ":" + headerValue);

            // 将header信息封装请求头中，使用Feign调用时，将会传递给下一个微服务
            requestTemplate.header(headerKey, headerValue);
        }

    }
}

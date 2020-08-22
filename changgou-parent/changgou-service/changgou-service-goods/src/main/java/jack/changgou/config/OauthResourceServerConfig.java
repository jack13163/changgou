package jack.changgou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * 资源授权配置
 * 客户端访问需要携带Authorization请求头：bearer 令牌
 */
@Configuration
@EnableResourceServer // 开启资源校验服务->令牌校验
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)// 激活方法上的PreAuthorize注解
public class OauthResourceServerConfig extends ResourceServerConfigurerAdapter {

    // 公钥
    private static final String PUBLIC_KEY = "public.key";

    /**
     * 定义JwtTokenStore
     *
     * @param jwtAccessTokenConverter
     * @return
     */
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * 定义转换器，指定解密Jwt令牌的公钥
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 关键，设置解密公钥
        converter.setVerifierKey(getPublicKey());
        return converter;
    }

    /**
     * 读取公钥密码
     *
     * @return
     */
    private String getPublicKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Http安全配置，对每一个到达系统的http请求链接进行校验
     * Spring Security
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 所有请求必须认证通过
        http.authorizeRequests()
//                .antMatchers("/user/add", "/user/load/*").permitAll()// 当前地址【用户注册】放行
                .anyRequest().authenticated();// 其他地址需要认证授权
    }
}

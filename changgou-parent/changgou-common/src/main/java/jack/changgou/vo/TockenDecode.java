package jack.changgou.vo;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class TockenDecode {
    /**
     * 获取用户信息
     * @return
     * @throws IOException
     */
    public static Map<String, String> getUserInfo() throws IOException {
        // 先获取认证详情
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        // 并从中获取令牌
        String token = details.getTokenValue();
        // 解析令牌后得到用户信息
        return parseToken(token);
    }

    /**
     * 解析令牌
     * @param token
     * @return
     * @throws IOException
     */
    private static Map<String, String> parseToken(String token) throws IOException {
        Resource resource = new ClassPathResource("public.key");
        InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String publicKey = bufferedReader.lines().collect(Collectors.joining("\n"));
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String tokenStr = jwt.getClaims();
        return JSON.parseObject(tokenStr, Map.class);
    }
}

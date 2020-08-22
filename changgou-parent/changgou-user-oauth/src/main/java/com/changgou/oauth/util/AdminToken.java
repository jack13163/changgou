package com.changgou.oauth.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员令牌发放
 */
public class AdminToken {
    /**
     * 创建具有管理员权限的令牌，解决微服务之间的feign调用认证问题
     * @return
     */
    public static String createAdminToken(){

        // 加载证书
        ClassPathResource resource = new ClassPathResource("changgou.jks");

        // 读取证书
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "changgou".toCharArray());

        // 获取私钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("changgou", "changgou".toCharArray());
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 创建令牌，使用RSA算法进行加盐加密
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "admin");
        payload.put("authorities", new String[]{"admin", "oauth"});// 管理员权限
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(payload), new RsaSigner(privateKey));
        return jwt.getEncoded();
    }
}

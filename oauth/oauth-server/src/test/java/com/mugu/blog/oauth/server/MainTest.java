package com.mugu.blog.oauth.server;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
@Slf4j
public class MainTest {


    public static void main(String[] args) {
//        createJwtToken();
        parseJwtToken();
    }

    public static void parseJwtToken(){
        //JWT的token
        String JWTToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjoxLCJ1c2VybmFtZSI6IuWFrOS8l-WPt--8mueggeeMv-aKgOacr-S4k-agjyJ9.FSNqocYTBD8kjAolbztDic09GIk3VQt6JP0f7CoeYl-j4I5Jsfo26N2tBRpxmqCzdBljCgLBk2kAl2uFbhchuh5HhE5m8euhUzgep0ymMX-5EZkl4B_HTe2JQm_puZJ8q8SrvPJyfP3cawWIvZvWcCRNBUEaOtgAPyTgzGiH0abF24g1TFLTrB1IalOtFRjAYAc6G_36O2MQB1gjBy8mnDw2UNvurMoEnAR353INnIdPKcLlscWhRb0d--o6AjD39lEEkxz3FjcDBkmAm1189DUbyi0g9WF-b4hKzQgvmBHD-zEgWss_372fgu3aBvwRUJEtA15QVyjt-uW-cPTxhQ";
        //公钥验证
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg3aVYpmO+Y6Auriw30bk\n" +
                "l4DjmUOMb0p4/PS8WBz4IzzGfhjRI2hxGMdQryLhBtiLi0AT31TSXSUDmAAqMQyl\n" +
                "7/vw8CahLSRYM2Ms2ahk0Fg6t28T3ueDCjtFXBjUjqa2vG8Jo9FYnfEDHpZH0reR\n" +
                "hL9Fqq+Dp4n77qR8y5XRnhtO8IGu/w/ucAwv3tDx4bXcJK7A2yrVApWymYwkNMEs\n" +
                "JRbvr8maV4zJNPDY4Fm1qjFfGV7xHAw0d8fmUnBLUFGkGGkpm1qCOyksBwsrb1w6\n" +
                "V0aJbUpzOMPpgxwPe4CYd9KCsUQcpfdo23bzK86E+zS8J8K3dt3JEsDwiWdWzvgc\n" +
                "iQIDAQAB\n" +
                "-----END PUBLIC KEY-----";
        //解密和验证令牌
        Jwt jwt = JwtHelper.decodeAndVerify(JWTToken, new RsaVerifier(publicKey));
        //获取载荷数据
        String claims = jwt.getClaims();
        System.out.println(claims);
    }


    public static void createJwtToken(){
        //加载证书
        ClassPathResource classPathResource = new ClassPathResource("mugu_blog.jks");
        //密钥库
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "mugu_blog".toCharArray());
        //获取秘钥对
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("mugu_blog", "mugu_blog".toCharArray());
        //获取私钥 , 私钥加密，公钥验证，是谓签名
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();

        //准备载荷数据
        Map<String,Object> data = new HashMap<>();
        data.put("id",1L);
        data.put("username","公众号：码猿技术专栏");
        data.put("role","ROLE_ADMIN");
        //创建令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(data), new RsaSigner(privateKey));
        //获取创建的令牌
        String token = jwt.getEncoded();
        System.out.println(token);
    }
}

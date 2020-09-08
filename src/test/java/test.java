import com.alibaba.fastjson.JSONObject;
import com.liyuan.component.jwt.JwtConfiguration;
import com.liyuan.component.jwt.JwtProperties;
import com.liyuan.component.jwt.JwtTokenGenerator;
import com.liyuan.component.jwt.JwtTokenPair;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class test {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toString());
        System.out.println(localDateTime.toLocalDate());
        System.out.println(localDateTime.toLocalTime());
    }
    @Test
    public void test1(){
        Map<String,String> additional = new HashMap<>();
        additional.put("1","2");
        additional.put("2","3");
        Map<String,String> payload = new HashMap<>();
        payload.put("a","b");
        payload.put("additional",JSONObject.toJSONString(additional));
        String additional1 = payload.get("additional");
        Map<String,String> parse = (Map<String, String>) JSONObject.parse(additional1);
        System.out.println(parse.get("1"));
//        System.out.println(payload);

    }
    @Test public void test2(){
        JwtConfiguration jwtConfiguration = new JwtConfiguration();
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setEnabled(true);
        jwtProperties.setIssuer("Liyuan");
        jwtProperties.setKeyLocation("liyuan.jks");
        jwtProperties.setKeyAlias("Liyuan123");
        jwtProperties.setKeyPass("123456");
        jwtProperties.setSubscriber("all");
        jwtProperties.setTokenExpirationDays(10);
        jwtProperties.setRefreshTokenExpirationDays(30);
        JwtTokenGenerator jwtTokenGenerator = jwtConfiguration.jwtTokenGenerator(jwtProperties);
        JwtTokenPair jwtTokenPair = jwtTokenGenerator.jwtTokenPair("1987151116@qq.com", new HashSet<String>(), null);
        String accessToken = jwtTokenPair.getAccessToken();
        String refreshToken = jwtTokenPair.getRefreshToken();

        JSONObject jsonObject = jwtTokenGenerator.decodeAndVerify(accessToken);
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        for(Map.Entry<String,Object> entry:entries){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
}

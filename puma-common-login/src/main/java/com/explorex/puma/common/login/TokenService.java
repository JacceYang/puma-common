package com.explorex.puma.common.login;

import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 */
@Component
public class TokenService {
    // 令牌自定义标识
    @Value("${token.header:Authorization}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret:jSUtIcdnDOuzTiTXVD5KmF8UbahfkDvtP4f5PnhmBIU=}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime:30}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;


    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            LoginUser user = RedisCache.getCacheObject(userKey);
            if (user == null) {
                Long useId=0L;
                if ( claims.get("uid") instanceof Integer){
                    useId=((Integer) claims.get("uid")).longValue();
                }else if(claims.get("uid") instanceof Long){
                    useId= (Long) claims.get("uid");
                }
                LoginUser tmp = new LoginUser();
                tmp.setUserName((String) claims.get("userName"));
                tmp.setToken(token);
                tmp.setLoginTime((Long) claims.get("createTime"));
                tmp.setUser(new User(useId, (String) claims.get("userName"), 0));
                user = tmp;
                RedisCache.setCacheObject(userKey,user,0,TimeUnit.MINUTES);
            }
            return user;
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (Objects.nonNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            RedisCache.deleteObject(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        //重新生成uuid
        String token = IdUtils.fastUUID();
        //将UUID设置为我们的唯一标识
        loginUser.setToken(token);
        //设置用户代理
        setUserAgent(loginUser);
        //刷新令牌
        refreshToken(loginUser);
        //令牌指令加生的uuid
        Map<String, Object> claims = new HashMap<>();
        // LOGIN_USER_KEY = "login_user_key"; 令牌前缀，就是UUID
        claims.put(Constants.LOGIN_USER_KEY, token);
        //存放非敏感信息
        claims.put("userName", loginUser.getUserName());
        claims.put("uid", loginUser.getUser().getUid());
        claims.put("createTime", loginUser.getUser().getCreateTime());
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser) {
        //获取令牌中的过期时间
        long expireTime = loginUser.getExpireTime();
        //获取当前的系统时间
        long currentTime = System.currentTimeMillis();
        //如果二者的时间差小于20分钟
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        //获取令牌，设置令牌的时间为当前的系统时间
        loginUser.setLoginTime(System.currentTimeMillis());
        //再为令牌增加默认的30分钟
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存，拿到用户修改之后的token
        String userKey = getTokenKey(loginUser.getToken());
        //重新将缓存中的用户数据刷新
        RedisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 设置用户代理信
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(IpUtils.getRealAddressByIP(ServletUtils.getRequest()));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
//        final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        //返回令牌
        String token = Jwts.builder()
                .setClaims(claims)
                //在这里生成我们的tokne,同时用我们的签名进行了加密
                //这里面放了姓名，创建时间
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return token;
    }

    public static void main(String[] args) {

        final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String raw = "exporex@shanghai";
        final String s1 = Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
        final String s = secretKey.toString();
        System.out.println(s);

    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.isNotEmpty(cookie.getValue()) && cookie.getValue().startsWith(Constants.TOKEN_PREFIX)) {
                    return cookie.getValue().replace(Constants.TOKEN_PREFIX, "");
                }
            }
        }

        return StringUtils.EMPTY;

    }

    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + ":" + uuid;
    }
}
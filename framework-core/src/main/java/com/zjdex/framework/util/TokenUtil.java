package com.zjdex.framework.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zjdex.framework.exception.CodeException;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lindj
 * @create 2018/9/10
 * @desc 功能描述
 **/
public class TokenUtil {

    /**
     * jwt秘钥
     */
    public static final String SECRET = "ZJDEX";

    /**
     * 创建token
     *
     * @param uid Long 用户唯一标识
     * @return
     * @throws Exception
     */
    public static String createToken(Long uid) {
        Date iatDate = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, (int) ConstantUtil.TIMEOUT);
        Date expiresDate = nowTime.getTime();

        //jwt头部信息
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create().withHeader(map).withClaim("iss", "service")
                .withClaim("aud", "web")
                .withClaim("uid", null == uid ? null : uid.toString())
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    /**
     * token 校验、解密
     *
     * @param token String
     * @return Map
     */
    public static Map<String, Claim> parseToken(String token) {
        if (StringUtil.isEmpty(token)) {
            throw new CodeException(ResultCode.Codes.NOT_LOGIN);
        }
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CodeException(ResultCode.Codes.NOT_LOGIN);
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取uid
     *
     * @param token
     * @return uid
     */
    public static Long getUid(String token) {
        Map<String, Claim> claims = parseToken(token);
        Claim uidClaim = claims.get("uid");
        if (null == uidClaim || StringUtils.isEmpty(uidClaim.asString())) {
            return null;
        }
        return Long.valueOf(uidClaim.asString());
    }

    public static void main(String[] args){

        String token = TokenUtil.createToken(132L);
        TokenUtil.parseToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ3ZWIiLCJ1aWQiOiIxMjQiLCJpc3MiOiJzZXJ2aWNlIiwiZXhwIjoxNTM5NzU5MzgzLCJpYXQiOjE1Mzk3NTc1ODN9.aYNp6rDs0WTkiw3-NJt7JQ00lHgCLx59e7Gzh9GaLEQ");
        System.out.println(TokenUtil.getUid("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ3ZWIiLCJ1aWQiOiIxMjQiLCJpc3MiOiJzZXJ2aWNlIiwiZXhwIjoxNTM5NzU5MzgzLCJpYXQiOjE1Mzk3NTc1ODN9.aYNp6rDs0WTkiw3-NJt7JQ00lHgCLx59e7Gzh9GaLEQ"));
    }

}

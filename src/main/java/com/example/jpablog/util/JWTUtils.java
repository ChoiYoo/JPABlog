package com.example.jpablog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberLoginToken;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Date;

@UtilityClass
public class JWTUtils {

    private static final String KEY = "zerobase";
    private static final String CLAIM_MEMBER_ID = "member_id";

    public static MemberLoginToken createToken(Member member){

        if(member == null) {
            return null;
        }

        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expireDate = java.sql.Timestamp.valueOf(expiredDateTime);
        String token = JWT.create()
                .withExpiresAt(expireDate)
                .withClaim(CLAIM_MEMBER_ID, member.getId())
                .withSubject(member.getUserName())
                .withIssuer(member.getEmail())
                .sign(Algorithm.HMAC512(KEY.getBytes()));

        return MemberLoginToken.builder()
                .token(token)
                .build();
    }

    public static String getIssuer(String token) {
        String issuer = JWT.require(Algorithm.HMAC512(KEY.getBytes()))
                .build()
                .verify(token)
                .getIssuer();

        return issuer;
    }
}

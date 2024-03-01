package com.ivanfang.fangblog;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

@SpringBootTest
public class JwtTest {

    @Test
    public void jwtGeneration() {
        String token = JWT.create()                                                                // create a jwt token
                .withClaim("username", "Lisa")                                       // payload
                .withClaim("id", 1)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365))  // expiration time
                .sign(Algorithm.HMAC256("ivanfang"));                                      // encryption algorithm & key (ivanfang)
        System.out.println(token);
    }

    @Test
    public void jwtParse() {
        // simulate the jwt token sent from front-end
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJpZCI6MSwiZXhwIjoxNzM3MjU3NzAyLCJ1c2VybmFtZSI6Ikxpc2EifQ" +
                ".DXqXC68UguPTnW9Gcp-K0IIAGgD4awmrRbe5dASNB6g";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("ivanfang")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        Map<String, Claim> claims = decodedJWT.getClaims();
        Claim username = claims.get("username");
        Claim id = claims.get("id");

        System.out.println("username = " + username);
        System.out.println("id = " + id);
    }

}

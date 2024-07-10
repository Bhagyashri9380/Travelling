package com.smartcard.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.smartcard.payload.LoginDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.algorithm.key}")
    private  String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiry.duration}")
    private int expiryTime;

    private Algorithm algorithm;
    private final String USER_NAME="username";
@PostConstruct
public void postConstruct() {
    algorithm = Algorithm.HMAC256(algorithmKey);
}
public String generateToken(LoginDto loginDto){
return JWT.create()
        .withClaim(USER_NAME,loginDto.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
        .withIssuer(issuer)              //help to generat token
        .sign(algorithm);                //here come jwt token
}
public String getUsername(String token){
    DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
    return decodedJWT.getClaim(USER_NAME).asString();        //verify the username
}
}







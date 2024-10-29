package com.example.mercadinho.service;

import com.example.mercadinho.domain.repository.model.UserEntity;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserEntity user) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                    .subject(user.getId())
                    .issuer("mercadinho").expirationTime(new Date(new Date().getTime() + 60 * 1000))
                    .build();
            SignedJWT signedJWT = new SignedJWT(header, claimSet);
            JWSSigner signer = new MACSigner(secret);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Error generating JWT Token.");
        }

    }

    public String validateToken(String token){
        try {
            // Parseia o token JWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Verifica a assinatura do token com a mesma chave secreta
            JWSVerifier verifier = new MACVerifier(secret);
            if (!signedJWT.verify(verifier)) {
                return signedJWT.serialize();
            }

            // Checa as claims do token
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();
            Date now = new Date();

            if (expirationTime == null || expirationTime.before(now)) {
                return signedJWT.serialize();
            }

            return claims.getSubject();

        } catch (ParseException | JOSEException e) {
            return "Erro ao validar o token: " + e.getMessage();
        }
    }

}

package com.example.mercadinho.service.user;

import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            // Parse o token JWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Verifique a assinatura do token usando a chave secreta
            if (!signedJWT.verify(new MACVerifier(secret))) {
                return "assinatura for inválida"; // Retorna null se a assinatura for inválida
            }

            // Verifique se o emissor é "auth-token"
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            if (!"mercadinho".equals(claims.getIssuer())) {
                return "emissor for inválido"; // Retorna null se o emissor for inválido
            }

            // Retorne o assunto (subject) do token
            return claims.getSubject();
        } catch (Exception e) {
            // Trata erros de verificação e parsing do JWT
            return "parsing do JWT";
        }
    }

}

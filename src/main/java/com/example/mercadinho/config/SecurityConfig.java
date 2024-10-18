package com.example.mercadinho.config;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3FlqJr5TRskIQIgdE3Dd\n" +
            "7D9lboWdcTUT8a+fJR7MAvQm7XXNoYkm3v7MQL1NYtDvL2l8CAnc0WdSTINU6IRv\n" +
            "c5Kqo2Q4csNX9SHOmEfzoROjQqahEcve1jBXluoCXdYuYpx4/1tfRgG6ii4Uhxh6\n" +
            "iI8qNMJQX+fLfqhbfYfxBQVRPywBkAbIP4x1EAsbC6FSNmkhCxiMNqEgxaIpY8C2\n" +
            "kJdJ/ZIV+WW4noDdzpKqHcwmB8FsrumlVY/DNVvUSDIipiq9PbP4H99TXN1o746o\n" +
            "RaNa07rq1hoCgMSSy+85SagCoxlmyE+D+of9SsMY8Ol9t0rdzpobBuhyJ/o5dfvj\n" +
            "KwIDAQAB\n" +
            "-----END PUBLIC KEY-----")
    private RSAPublicKey key;
    @Value("-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDcWWomvlNGyQhA\n" +
            "iB0TcN3sP2VuhZ1xNRPxr58lHswC9Cbtdc2hiSbe/sxAvU1i0O8vaXwICdzRZ1JM\n" +
            "g1TohG9zkqqjZDhyw1f1Ic6YR/OhE6NCpqERy97WMFeW6gJd1i5inHj/W19GAbqK\n" +
            "LhSHGHqIjyo0wlBf58t+qFt9h/EFBVE/LAGQBsg/jHUQCxsLoVI2aSELGIw2oSDF\n" +
            "oiljwLaQl0n9khX5ZbiegN3OkqodzCYHwWyu6aVVj8M1W9RIMiKmKr09s/gf31Nc\n" +
            "3WjvjqhFo1rTuurWGgKAxJLL7zlJqAKjGWbIT4P6h/1Kwxjw6X23St3OmhsG6HIn\n" +
            "+jl1++MrAgMBAAECggEBAMf820wop3pyUOwI3aLcaH7YFx5VZMzvqJdNlvpg1jbE\n" +
            "E2Sn66b1zPLNfOIxLcBG8x8r9Ody1Bi2Vsqc0/5o3KKfdgHvnxAB3Z3dPh2WCDek\n" +
            "lCOVClEVoLzziTuuTdGO5/CWJXdWHcVzIjPxmK34eJXioiLaTYqN3XKqKMdpD0ZG\n" +
            "mtNTGvGf+9fQ4i94t0WqIxpMpGt7NM4RHy3+Onggev0zLiDANC23mWrTsUgect/7\n" +
            "62TYg8g1bKwLAb9wCBT+BiOuCc2wrArRLOJgUkj/F4/gtrR9ima34SvWUyoUaKA0\n" +
            "bi4YBX9l8oJwFGHbU9uFGEMnH0T/V0KtIB7qetReywkCgYEA9cFyfBIQrYISV/OA\n" +
            "+Z0bo3vh2aL0QgKrSXZ924cLt7itQAHNZ2ya+e3JRlTczi5mnWfjPWZ6eJB/8MlH\n" +
            "Gpn12o/POEkU+XjZZSPe1RWGt5g0S3lWqyx9toCS9ACXcN9tGbaqcFSVI73zVTRA\n" +
            "8J9grR0fbGn7jaTlTX2tnlOTQ60CgYEA5YjYpEq4L8UUMFkuj+BsS3u0oEBnzuHd\n" +
            "I9LEHmN+CMPosvabQu5wkJXLuqo2TxRnAznsA8R3pCLkdPGoWMCiWRAsCn979TdY\n" +
            "QbqO2qvBAD2Q19GtY7lIu6C35/enQWzJUMQE3WW0OvjLzZ0l/9mA2FBRR+3F9A1d\n" +
            "rBdnmv0c3TcCgYEAi2i+ggVZcqPbtgrLOk5WVGo9F1GqUBvlgNn30WWNTx4zIaEk\n" +
            "HSxtyaOLTxtq2odV7Kr3LGiKxwPpn/T+Ief+oIp92YcTn+VfJVGw4Z3BezqbR8lA\n" +
            "Uf/+HF5ZfpMrVXtZD4Igs3I33Duv4sCuqhEvLWTc44pHifVloozNxYfRfU0CgYBN\n" +
            "HXa7a6cJ1Yp829l62QlJKtx6Ymj95oAnQu5Ez2ROiZMqXRO4nucOjGUP55Orac1a\n" +
            "FiGm+mC/skFS0MWgW8evaHGDbWU180wheQ35hW6oKAb7myRHtr4q20ouEtQMdQIF\n" +
            "snV39G1iyqeeAsf7dxWElydXpRi2b68i3BIgzhzebQKBgQCdUQuTsqV9y/JFpu6H\n" +
            "c5TVvhG/ubfBspI5DhQqIGijnVBzFT//UfIYMSKJo75qqBEyP2EJSmCsunWsAFsM\n" +
            "TszuiGTkrKcZy9G0wJqPztZZl2F2+bJgnA6nBEV7g5PA4Af+QSmaIhRwqGDAuROR\n" +
            "47jndeyIaMTNETEmOnms+as17g==\n" +
            "-----END PRIVATE KEY-----")
    private RSAPrivateKey priv;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/authenticate").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(
                        conf -> conf.jwt(
                                jwt -> jwt.decoder(jwtDecoder())));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}

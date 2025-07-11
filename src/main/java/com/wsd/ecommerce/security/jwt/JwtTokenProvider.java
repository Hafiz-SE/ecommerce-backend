package com.wsd.ecommerce.security.jwt;

import com.wsd.ecommerce.constant.SecurityConstant;
import com.wsd.ecommerce.security.custom.user.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    public String generateToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + SecurityConstant.EXPIRATION_TIME);
        return Jwts.builder().setSubject(customUserDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSecretKey(), signatureAlgorithm).compact();
    }

    public String getEmailFromJWT(String token) {
        return getJwtParser().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken) {

        try {
            getJwtParser().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }

        return false;
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstant.SECRET));
    }
}
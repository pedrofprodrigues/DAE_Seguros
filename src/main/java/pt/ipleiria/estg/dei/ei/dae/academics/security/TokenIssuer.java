package pt.ipleiria.estg.dei.ei.dae.academics.security;

import io.jsonwebtoken.*;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.MockAPIBean;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TokenIssuer {

    protected static final byte[] SECRET_KEY = "secret".getBytes();
    protected static final String ALGORITHM = "DES";

    public static final long EXPIRY_MINS = 60L;

    public String issue(MockAPIBean mockAPIBean) {
        var expiryPeriod = LocalDateTime.now().plusMinutes(EXPIRY_MINS);

        var expirationDateTime = Date.from(
                expiryPeriod.atZone(ZoneId.systemDefault()).toInstant()
        );

        Key key = new SecretKeySpec(SECRET_KEY, ALGORITHM);

        return Jwts.builder()
                .setSubject(mockAPIBean.MockAPItoJSON(mockAPIBean))
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuedAt(new Date())
                .setExpiration(expirationDateTime)
                .compact();
    }
}

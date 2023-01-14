package pt.ipleiria.estg.dei.ei.dae.academics.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TokenIssuer {

    public static final long EXPIRY_MINS = 60L;
    protected static final byte[] SECRET_KEY = "secret".getBytes();
    protected static final String ALGORITHM = "DES";

    public String issue(UserAPIBean userAPIBean) {
        var expiryPeriod = LocalDateTime.now().plusMinutes(EXPIRY_MINS);

        var expirationDateTime = Date.from(
                expiryPeriod.atZone(ZoneId.systemDefault()).toInstant()
        );

        Key key = new SecretKeySpec(SECRET_KEY, ALGORITHM);

        return Jwts.builder()
                .setSubject(userAPIBean.getNif())
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuedAt(new Date())
                .setExpiration(expirationDateTime)
                .compact();
    }
}

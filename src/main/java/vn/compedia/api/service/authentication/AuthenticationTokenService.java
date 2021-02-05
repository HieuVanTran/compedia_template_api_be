package vn.compedia.api.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.compedia.api.config.Properties;
import vn.compedia.api.exception.authentication.TokenValidationException;
import vn.compedia.api.util.authentication.AuthenticationToken;

@Service
public class AuthenticationTokenService {

    private Properties properties;

    @Autowired
    private AuthenticationTokenService(Properties properties) {
        this.properties = properties;
    }

    public AuthenticationToken createToken(String token) throws TokenValidationException {
        return new AuthenticationToken(token, properties.getAccessTokenSecretKey());
    }
}

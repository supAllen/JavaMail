package CoreCode;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author allen
 * @Description:    用户登陆包装类
 * @date 18-4-23 下午8:37
 */
public final class AuthenticatorGenerator {
    public static Authenticator getAuthenticator(final String username, final String password){
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };
    }
}

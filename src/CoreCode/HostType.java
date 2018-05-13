package CoreCode;
import java.util.Properties;

/**
 * @author allen
 * @Description:    邮件服务器域名
 * @date 18-4-23 下午8:36
 */
public enum HostType {
    NETEASE {
        @Override
        public Properties getProperties() {
            Properties defaults = new Properties();
            defaults.put("mail.pop3.host", "pop.163.com");
            defaults.put("mail.imap.host", "imap.163.com");
            defaults.put("mail.smtp.host", "smtp.163.com");
            defaults.put("mail.store.protocol", "pop3"); // 默认使用pop3收取邮件
            return defaults;
        }

    },
    TENCENT {
        @Override
        public Properties getProperties() {
            Properties defaults = new Properties();
            defaults.put("mail.pop3.host", "pop.exmail.qq.com");
            defaults.put("mail.imap.host", "imap.exmail.qq.com");
            defaults.put("mail.store.protocol", "pop3"); // 默认使用pop3收取邮件
            return defaults;
        }
    },
    GMAIL {

        @Override
        public Properties getProperties() {
            Properties defaults = new Properties();
            defaults.put("mail.pop3.host", "pop.gmail.com");
            defaults.put("mail.pop3.port", "995");
            defaults.put("mail.imap.host", "imap.gmail.com");
            defaults.put("mail.imap.port", "465");
            defaults.put("mail.store.protocol", "pop3"); // 默认使用pop3收取邮件
            return defaults;
        }

    };

    public abstract Properties getProperties();
}

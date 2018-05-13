package CoreCode;

/**
 * @author allen
 * @Description:    邮件配置信息类
 * @date 18-4-24 下午1:23
 */
public class UserInformation {
    /**
     * 邮箱的账号
     */
    private static final String filePath;

    /**
     * 密码
     */
    private static final boolean isSaveBody;

    /**
     * 邮件内容存储位置
     */
    private static final String name;

    /**
     * 邮件主体是否存盘
     */
    private static final String password;

    /**
     * 邮件附件
     */
    private static final String attachmentFilePath;

    /**
     * 邮件的附件是否存盘
     */
    private static final boolean isSaveAttachment;

    /**
     * 每次提取最新的邮件的个数
     */
    private static final int findFileCount;

    /**
     * 轮询的时间
     */
    private static final long time;

    static {    // 加载配置文件中配置信息
        InfoParse parse = InfoParse.getParse();
        name = parse.getInfo()[0];
        password = parse.getInfo()[1];
        filePath = parse.getInfo()[2];
        isSaveBody = Boolean.parseBoolean(parse.getInfo()[3]);
        attachmentFilePath = parse.getInfo()[4];
        isSaveAttachment = Boolean.parseBoolean(parse.getInfo()[5]);
        findFileCount = Integer.parseInt(parse.getInfo()[6]);
        time = Long.parseLong(parse.getInfo()[7]);
    }

    public static String getFilePath() {
        return filePath;
    }

    public static String getName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }

    public static boolean isIsSaveBody() {
        return isSaveBody;
    }

    public static String getAttachmentFilePath() {
        return attachmentFilePath;
    }

    public static boolean isIsSaveAttachment() {
        return isSaveAttachment;
    }

    public static int getFindFileCount() {
        return findFileCount;
    }

    public static long getTime() {
        return time;
    }

    public UserInformation() {
    }

}

package CoreCode;

import org.apache.commons.mail.util.MimeMessageParser;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.List;

/**
 * @author allen
 * @Description:    邮件解析类
 * @date 18-4-23 下午7:59
 */
public class MessageParser {
    /**
     * 邮件存放位置
     */
    private static final String folder;
    private static final boolean isSaveBody;
    private static final String attachmentFile;
    private static final boolean isSaveAttachment;
    private static final int mailCount;

    static {
        folder = UserInformation.getFilePath();
        isSaveBody = UserInformation.isIsSaveBody();
        attachmentFile = UserInformation.getAttachmentFilePath();
        isSaveAttachment = UserInformation.isIsSaveAttachment();
        mailCount = UserInformation.getFindFileCount();
    }

    private static void parse(Message message){
        try {
            MimeMessageParser parser = new MimeMessageParser((MimeMessage) message).parse();
//            String from = parser.getFrom(); //获取发件人地址
//            List<Address> cc = parser.getCc(); // 获取抄送人地址
//            List<Address> to = parser.getTo(); // 获取收件人地址
//            String replyTo = parser.getReplyTo(); // 获取回复邮件时的收件人

            String subject = parser.getSubject();  // 获取邮件主题
            StringBuilder sb = changePath(subject);
            String filename = folder+ File.separator+sb.toString();
            File file = new File(filename);
            if (file.exists()) {
                return;
            }
            if (isSaveBody){
                //String plainContent = parser.getPlainContent(); // 获取纯文本邮件内容
                String htmlContent = parser.getHtmlContent();   // 获取html内容
                // 获取邮件中的内容
                BufferedOutputStream out = null;        // 可以使用一个I/O池
                try {
                    out = new BufferedOutputStream(new FileOutputStream(filename));
                    if (htmlContent!=null)
                        out.write(htmlContent.getBytes());
                } finally {
                    if (out!=null)
                        out.close();
                }
            }
            if (isSaveAttachment)
                transfo(parser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析函数
     * @param messages
     * @throws Exception
     */
    public static void parse(Message[] messages) throws Exception {
        if (messages == null || messages.length == 0){
            System.out.println("没有任何邮件");
            return;
        }else {
            // 解析出来直接都相同就没有必要解析了
            if (!isParseNessary(messages[0]))
                return;
            // 这块应该做一些操作，使得能跳跃之前的邮件
            // 从后往前遍历就好了，然后取前10个
            int index = messages.length-1;
            for (int i = index; i >= index-mailCount ; --i) {
                parse(messages[i]);
            }
            // 最后关闭连接
            if (messages[0] == null){
                Folder folder = messages[0].getFolder();
                if (folder !=null){
                    try {
                        Store store = folder.getStore();
                        folder.close(false);    // 参数false 表示对邮件的修改不传送到服务器上
                        if (store!=null)
                            store.close();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 将邮件附件存盘
     * @param parser
     */
    private static void transfo(MimeMessageParser parser){
        List<DataSource> attachmentList = parser.getAttachmentList();   // 获取附件并写入磁盘
        for (DataSource ds :
                attachmentList) {
            BufferedOutputStream out = null;
            BufferedInputStream  in = null;
            try {
                StringBuilder sb = changePath(ds.getName());
                String filename = attachmentFile+ File.separator+sb.toString();
                out = new BufferedOutputStream(new FileOutputStream(filename));
                in = new BufferedInputStream(ds.getInputStream());
                byte[] data = new byte[2048];
                int length;
                while ((length = in.read(data)) != -1){
                    out.write(data,0,length);
                }
                out.flush();
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
            } catch (IOException e) {
                    e.printStackTrace();
            } finally {
                try {
                    if (in!=null)
                            in.close();
                    if (out!=null)
                            out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断是否需要解析
     * @param first
     * @return
     */
    public static boolean isParseNessary(Message first){
        try {
            // 如果最后一个邮件都相同则直接退出
            MimeMessageParser parser = new MimeMessageParser((MimeMessage) first).parse();
            String subject = parser.getSubject();  // 获取邮件主题
            String filename = folder+ File.separator+subject;
            File file = new File(filename);
            if (file.exists())
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static StringBuilder changePath(String subject){
        // 邮件主题名不符合文件命名规范的则进行处理
        String match = "[:*?><|]+";
        StringBuilder sb = new StringBuilder(subject);
        for (int i = 0; i < subject.length(); i++) {
            if (String.valueOf(subject.charAt(i)).matches(match) ||
                    subject.charAt(i) == '"' || subject.charAt(i) == '/'
                    || subject.charAt(i) == '\\')
                sb.delete(i,i+1);
        }
        return sb;
    }
}

package CoreCode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author allen
 * @Description:    定时任务类
 * @date 18-4-24 下午4:36
 */
public class TaskTimer{
    private static final String name;
    private static final String password;
    private static final long runTime;      // 配置文件中为分钟

    static {
        name = UserInformation.getName();
        password = UserInformation.getPassword();
        runTime = UserInformation.getTime()*60*1000;
    }

    public static void executeFixedRate(){
        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(1);
        long start = System.currentTimeMillis();
        executor.scheduleWithFixedDelay(
                () -> {
                    try {
                            MessageParser.parse(SimpleMailReceiver.fetchInbox(HostType.NETEASE.getProperties(),
                                    AuthenticatorGenerator.getAuthenticator(
                                            name,password)));
                            if (System.currentTimeMillis()-start > runTime)
                                executor.shutdownNow();     // 定时任务的终止措施
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                },
                0,
                100,
                TimeUnit.MILLISECONDS
        );
    }
}

package CoreCode;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * @author allen
 * @Description:    解析配置文件内容
 * @date 18-4-23 下午10:13
 */
public class InfoParse {
    private static InfoParse parse = new InfoParse();
    private static final int SIZE = 8;
    private String[] info;

    public String[] getInfo() {
        return info;
    }

    private InfoParse(){
        info = new String[SIZE];
        build();
    }

    private void build() {
        try {
            // 创建解析器
            SAXReader saxReader = new SAXReader();
            // 生成 Document 树
            Document document = saxReader.read("config/user.xml");
            // 获取xPath路径
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            int index = 0;
            for (Element e :
                    elements) {
                info[index++] = e.getText();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static InfoParse getParse(){
        return parse;
    }

    public static void main(String[] args) {
        System.out.println(getParse().info[0]);
    }
}

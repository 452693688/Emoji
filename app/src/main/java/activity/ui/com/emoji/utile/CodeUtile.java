package activity.ui.com.emoji.utile;

import java.net.URLDecoder;

/**
 * Created by Administrator on 2016/6/29.
 */
public class CodeUtile {

    public static String stringToUTF8(String obj) {
        String result = "";
        try {
            result = URLDecoder.decode(obj, "UTF-8");
        } catch (Exception e) {
            result = obj;
        }

        return result;
    }
}

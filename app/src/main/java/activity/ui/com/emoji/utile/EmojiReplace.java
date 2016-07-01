package activity.ui.com.emoji.utile;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2016/6/28.
 */
public class EmojiReplace {

    //表情替换
    public static String emojiTest(String msg) {
        String str = parseEmoji(msg);
        String[] code = stringToUnicode(msg);
        String result = msg + "\n表情替换：" + str +
                "\n" + code[0];
        Log.e("结果：", result);
        return result;
    }

    /***
     * 一般为输入框用
     * 内容查找是否包含 输入法输入的emoji，包含：替换成包内表情
     *
     * @param input
     * @return 非null存在emoji图标。
     */
    public static SpannableStringBuilder replaceEmoji(String input) {
        if (TextUtils.isEmpty(input)) {
            return null;
        }
        SpannableStringBuilder sBuilder = new SpannableStringBuilder();
         boolean isEmoji = false;
        int[] codePoints = toCodePointArray(input);
        List<Integer> key = null;
        HashMap<List<Integer>, String> convertMap = EmojiAnalysisXML.getInstance().getConvertMap();
        for (int i = 0; i < codePoints.length; i++) {
            key = new ArrayList<Integer>();
            if (i + 1 < codePoints.length) {
                key.add(codePoints[i]);
                key.add(codePoints[i + 1]);
                if (convertMap.containsKey(key)) {
                    String value = convertMap.get(key);
                    ImageSpan emojiIcon = EmojiIcon.emojiIcon(value);
                    if (emojiIcon != null) {
                        String tag = "[e]" + value + "[/e]";
                        sBuilder.append(tag);
                        int length = sBuilder.length() ;
                        sBuilder.setSpan(emojiIcon, (length-tag.length()), length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        isEmoji = true;
                    }
                    i++;
                    continue;
                }
            }
            key.clear();
            key.add(codePoints[i]);
            if (convertMap.containsKey(key)) {
                String value = convertMap.get(key);
                ImageSpan emojiIcon = EmojiIcon.emojiIcon(value);
                if (emojiIcon != null) {
                    String tag = "[e]" + value + "[/e]";
                    sBuilder.append(tag);
                    int length = sBuilder.length();
                    sBuilder.setSpan(emojiIcon, (length-tag.length()), length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    isEmoji = true;
                }
                continue;
            }
            String txt = new String(Character.toChars(codePoints[i]));
            sBuilder.append(txt);
        }
        if (!isEmoji) {
            sBuilder = null;
        }
        return sBuilder;
    }

    /**
     * 将表情替换成字符
     *
     * @param input 字符串
     * @return 如果有表情将替换成[e]1f3e0[/e]形式
     */
    public static String parseEmoji(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int[] codePoints = toCodePointArray(input);
        List<Integer> key = null;
        HashMap<List<Integer>, String> convertMap = EmojiAnalysisXML.getInstance().getConvertMap();
        for (int i = 0; i < codePoints.length; i++) {
            key = new ArrayList<Integer>();
            if (i + 1 < codePoints.length) {
                key.add(codePoints[i]);
                key.add(codePoints[i + 1]);
                if (convertMap.containsKey(key)) {
                    String value = convertMap.get(key);
                    if (value != null) {
                        result.append("[e]" + value + "[/e]");
                    }
                    i++;
                    continue;
                }
            }
            key.clear();
            key.add(codePoints[i]);
            if (convertMap.containsKey(key)) {
                String value = convertMap.get(key);
                if (value != null) {
                    result.append("[e]" + value + "[/e]");
                }
                continue;
            }
            result.append(Character.toChars(codePoints[i]));
        }
        String resultStr = result.toString();
        return resultStr;
    }

    private static int[] toCodePointArray(String str) {
        char[] ach = str.toCharArray();
        int len = ach.length;
        int[] acp = new int[Character.codePointCount(ach, 0, len)];
        int j = 0;
        for (int i = 0, cp; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(ach, i);
            acp[j++] = cp;
        }
        return acp;
    }
    //-------------转化为unicode值 字符串表示---------------------------------

    /**
     * 表情转化为unicode 和 utf-8
     *
     * @param emoji 一个表情
     * @return string形式的unicode码和utf-8码的 emoji
     */
    public static String[] stringToUnicode(String emoji) {
        int in;
        String unicode = "";
        String utf = "";
        for (int i = 0; i < emoji.length(); i++) {
            String obj = emoji.charAt(i) + "";
            in = emoji.codePointAt(i);
            unicode += "u" + Integer.toHexString(in).toUpperCase();
            try {
                utf += URLDecoder.decode(obj, "UTF-8");
            } catch (Exception e) {

            }
        }
        Log.e("stringToUnicode:结果", "unicode:" + unicode + " utf:" + utf);
        return new String[]{unicode, utf};
    }

    /**
     * @param emojiUnicode 只是一个表情
     * @return unicode值
     */
    public static int[] unicodeToImoji(String emojiUnicode) {
        if (TextUtils.isEmpty(emojiUnicode)) {
            return null;
        }
        Log.e("unicode的字符转unnicode值", emojiUnicode);
        //已string的形式存在的unicode
        String[] unicodes = emojiUnicode.split("u");
        int continueSize = 0;
        int unicodeJoysIndex = 0;
        int[] unicodeJoys = null;
        for (int i = 0; i < unicodes.length; i++) {
            String unicodestr = unicodes[i];
            if (TextUtils.isEmpty(unicodestr)) {
                continueSize++;
                continue;
            }
            if (unicodeJoys == null) {
                unicodeJoys = new int[unicodes.length - continueSize];
            }
            try {
                String unicodeStr = "0x" + unicodes[i];
                Log.e("unicode的字符", unicodeStr);
                Integer unicode = Integer.decode(unicodeStr);
                unicodeJoys[unicodeJoysIndex] = unicode;
                unicodeJoysIndex++;
            } catch (Exception e) {
                String msg = e.getMessage();
                Log.e("unicode字符转值报错", msg);
            }
        }
        return unicodeJoys;
    }


}

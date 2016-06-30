package activity.ui.com.emoji.utile;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 转换成图片
 * Created by Administrator on 2016/6/30.
 */
public class EmojiIcon {
    private static String regex = "\\[e\\](.*?)\\[/e\\]";

    /***
     * [e]1f3e0[/e]形式转化为 包内emoji图标
     *
     * @param content
     * @param context
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder convertToEmoji(String content, Context context) {
        Pattern pattern = Pattern.compile(regex);
        String emo = "";
        Matcher matcher = pattern.matcher(content);
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(content);
        Resources resources = null;
        while (matcher.find()) {
            emo = matcher.group();
            if (resources == null) {
                resources = context.getResources();
            }
            try {
                String name = "emoji_" + emo.substring(emo.indexOf("]") + 1, emo.lastIndexOf("["));
                String type = "mipmap";
                String defPackage = "activity.ui.com.emoji";
                int id = resources.getIdentifier(name, type, defPackage);
                if (id != 0) {
                    Drawable drawable = resources.getDrawable(id);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ImageSpan span = new ImageSpan(drawable);
                    sBuilder.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } catch (Exception e) {
                break;
            }
        }
        return sBuilder;
    }
    /**
     * Unicode转系统emoji图标  一个表情可能有2个unicode码
     *
     * @param unicodeJoys 只是一个表情 unicodeJoys int[]{0x0038,0x20E3}
     * @return emoji表情
     */
    public static String getEmojiString(int[] unicodeJoys) {
        if (unicodeJoys == null || unicodeJoys.length == 0) {
            return "";
        }
        String emojiString = "";
        for (int i = 0; i < unicodeJoys.length; i++) {
            int unicode = unicodeJoys[i];
            Log.e("Unicode值转emoji", "unicode值:" + unicode);
            if (unicode == 0) {
                continue;
            }
            // 直接使用Character.toChars()方法将unicode编码转换为一个char数组，
            // 再将这个char数组转换成为字符串就可以直接操作了，系统会自动将其解析为表情图片
            emojiString += new String(Character.toChars(unicode));
        }
        return emojiString;
    }
}

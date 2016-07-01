package activity.ui.com.emoji.utile;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import activity.ui.com.emoji.activity.MainApplication;

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
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder convertToEmoji(String content) {
        Pattern pattern = Pattern.compile(regex);
        String emo = "";
        Matcher matcher = pattern.matcher(content);
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(content);
        while (matcher.find()) {
            emo = matcher.group();
            String emojiUncode = emo.substring(emo.indexOf("]") + 1, emo.lastIndexOf("["));
            ImageSpan span = emojiIcon(emojiUncode);
            if (span != null) {
                sBuilder.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return sBuilder;
    }

    private static Resources resources;
    private static String pckName;

    /***
     * 获取一张emoji图片
     *
     * @param emojiUnicode
     * @return
     */
    public static ImageSpan emojiIcon(String emojiUnicode) {
        if (TextUtils.isEmpty(emojiUnicode)) {
            return null;
        }
        if (resources == null) {
            resources = MainApplication.context.getResources();
        }
        if (TextUtils.isEmpty(pckName)) {
            pckName = MainApplication.context.getPackageName();
        }
        ImageSpan span = null;
        String name = "emoji_" + emojiUnicode;
        String type = "mipmap";
        String defPackage = pckName;
        int id = resources.getIdentifier(name, type, defPackage);
        if (id != 0) {
            Drawable drawable = resources.getDrawable(id);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            span = new ImageSpan(drawable);
        }
        return span;
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

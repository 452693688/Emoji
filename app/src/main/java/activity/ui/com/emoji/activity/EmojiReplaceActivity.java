package activity.ui.com.emoji.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import activity.ui.com.emoji.R;

public class EmojiReplaceActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView importTv;
    private TextView import1Tv;
    private TextView import2Tv;
    private EditText testEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editext2);
        findViewById(R.id.main_import_btn).setOnClickListener(this);

        testEt = (EditText) findViewById(R.id.test_et);
        testEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    String j = URLDecoder.decode(s.toString(), "unicode");
                    importTv.setText(j);
                    CharSequence z = emojiFilter.filter(s, 0, 0, null, 0, 0);
                    import2Tv.setText(z);
                    //
                    String str = stringToUnicode(s.toString());
                    import1Tv.setText(str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        importTv = (TextView) findViewById(R.id.main_import_tv);
        import1Tv = (TextView) findViewById(R.id.main_import1_tv);
        import2Tv = (TextView) findViewById(R.id.main_import2_tv);
        //1F601 转表情
        String msg0 = String.valueOf(Character.toChars(Integer.parseInt("1F601", 16)));
        //U+0034 U+20E3 转表情
        String msg1 = String.valueOf(Character.toChars(Integer.parseInt("0034", 16)));
        String msg2 = String.valueOf(Character.toChars(Integer.parseInt("20E3", 16)));
        //1F601 转表情
        String msg3 = String.valueOf(Character.toChars(Integer.parseInt("D83D", 16)));
        String msg4 = String.valueOf(Character.toChars(Integer.parseInt("DE01", 16)));
         //D83D DE01
        importTv.setText(msg0 + (msg1 + msg2) + (msg3 + msg4));
        String m1 = decode("\\uD83D\\uDE0A");
        Log.e("uincoed 转中文1", m1);
        import2Tv.setText(m1);
        String m2 = getEmoji(this, "我的\\uD83D\\uDE0A123");
        import1Tv.setText(m2);
    }

    //String-->UniCode (表情 转 \uD83D\uDE0A)
    private String stringToUnicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            //"\\u只是代号，请根据具体所需添加相应的符号"
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    //Unicode 转中文(\uD83D\uDE0A 转表情)
    private String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) != '\\') {
                retBuf.append(unicodeStr.charAt(i));
                continue;
            }
            if ((i < maxLoop - 5)
                    && ((unicodeStr.charAt(i + 1) == 'u')
                    || (unicodeStr.charAt(i + 1) == 'U'))) {
                try {
                    retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                    i += 5;
                } catch (NumberFormatException localNumberFormatException) {
                    retBuf.append(unicodeStr.charAt(i));
                }
                continue;
            }
            retBuf.append(unicodeStr.charAt(i));
        }
        return retBuf.toString();
    }

    /**
     * 无效
     * 将表情字符串转换成表情
     *
     * @param str
     * @return
     */
    private String getEmoji(Context context, String str) {
        String string = str;
        String rep = "\\{(.*?)\\}";
        Pattern p = Pattern.compile(rep);
        Matcher m = p.matcher(string);
        while (m.find()) {
            String s1 = m.group().toString();
            String s2 = s1.substring(1, s1.length() - 1);
            String s3;
            try {
                s3 = String.valueOf((char) Integer.parseInt(s2, 16));
                string = string.replace(s1, s3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return string;
    }

    @Override
    public void onClick(View v) {

    }

    //表情过滤
    InputFilter emojiFilter = new InputFilter() {
        Pattern pattern = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|" +
                        "[\ud83d\udc00-\ud83d\udfff]|" +
                        "[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Pattern pattern1 = Pattern.compile(
                "[\u1F601-\u1F64F]",//1F601 - 1F64F
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                                   int dend) {
            String value = source.toString();
            if (value == null) {
                return value;
            }
            Matcher emojiMatcher = pattern1.matcher(source);
            while (emojiMatcher.find()) {
                String emoji = emojiMatcher.group(0);
                String emojiCode = stringToUnicode(emoji);
                Log.e("转换成功", emojiCode);
                value = value.replace(emoji, emojiCode);
                Log.e("替换成功", value);
            }
            return value;
        }
    };
}

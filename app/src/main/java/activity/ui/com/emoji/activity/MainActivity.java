package activity.ui.com.emoji.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import activity.ui.com.emoji.R;
import activity.ui.com.emoji.view.EmojiEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EmojiEditText importEt;
    private TextView importTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_import_btn).setOnClickListener(this);
        importEt = (EmojiEditText) findViewById(R.id.main_import_et);
        importTv = (TextView) findViewById(R.id.main_import_tv);

    }

    @Override
    public void onClick(View v) {
        String importText = importEt.getText().toString();
        importTv.setText(importText);
    }

    public String stringToUnicode(String s) {
        int in;
        String st = "";
        for (int i = 0; i < s.length(); i++) {
            in = s.codePointAt(i);
            st = st + "\\u" + Integer.toHexString(in).toUpperCase();
        }
        return st;
    }

    public String unicodeToString(String msg) {
        String result = "";
        String newMsg = msg.replace("\\u", "0x");
        String[] strs = newMsg.split("0x");
        int j = 0;
        for (int i = 0; i < 0; i++) {
            String unicode = strs[i];
            Log.e("转换", unicode);
            if (TextUtils.isEmpty(unicode)) {
                continue;
            }
            Integer n = Integer.decode("0x" + unicode);
            if (n == null) {
                continue;
            }
            j += n;
        }
        result += getEmojiStringByUnicode(j);

        return result;
    }

    private String getEmojiStringByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }


}

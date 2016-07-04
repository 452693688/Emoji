package activity.ui.com.emoji.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import activity.ui.com.emoji.R;
import activity.ui.com.emoji.utile.EmojiIcon;
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
        String importText = importEt.getSendInputMsg();
        SpannableStringBuilder result = new SpannableStringBuilder();
        result.append("显示为：");
        result.append(importEt.getShowInputMsg());
        result.append("\n发送转换为：" + importText);
        result.append("\n接收转换为：");
        result.append(EmojiIcon.convertToEmoji(importText));
        importTv.setText(result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CharSequence msg = importEt.getShowInputMsg();
        if(msg!=null){
            importEt.setText(msg);
        }
    }
}

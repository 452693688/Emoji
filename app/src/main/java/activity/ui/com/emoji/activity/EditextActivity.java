package activity.ui.com.emoji.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import activity.ui.com.emoji.R;
import activity.ui.com.emoji.utile.EmojiIcon;
import activity.ui.com.emoji.view.EmojiEditText;

public class EditextActivity extends AppCompatActivity implements View.OnClickListener {

    private EmojiEditText importEt;
    private TextView importTv;
    private TextView import1Tv;
    private TextView import2Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editext);
        findViewById(R.id.main_import_btn).setOnClickListener(this);
        importEt = (EmojiEditText) findViewById(R.id.main_import_et);
        importTv = (TextView) findViewById(R.id.main_import_tv);
        import1Tv = (TextView) findViewById(R.id.main_import1_tv);
        import2Tv = (TextView) findViewById(R.id.main_import2_tv);
    }

    @Override
    public void onClick(View v) {
        SpannableStringBuilder result = new SpannableStringBuilder();
        result.append("显示为：");
        result.append(importEt.getMsgSequence());
        importTv.setText(result);
        import1Tv.setText("发送转换为:" + importEt.getMsgSequence());
        SpannableStringBuilder result2 = new SpannableStringBuilder();
        result2.append("接收转换为：");
        result2.append(EmojiIcon.convertToEmoji(importEt.getMsgTxt()));
        import2Tv.setText(result2);
    }


}

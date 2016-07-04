package activity.ui.com.emoji.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import activity.ui.com.emoji.R;
import activity.ui.com.emoji.adapter.EmojiIconAdapter;
import activity.ui.com.emoji.utile.EmojiAnalysisXML;

public class EmojiActivity extends AppCompatActivity implements View.OnClickListener {

    private EmojiIconAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_icon);
        findViewById(R.id.icon_1_btn).setOnClickListener(this);
        findViewById(R.id.icon_2_btn).setOnClickListener(this);
        findViewById(R.id.icon_3_btn).setOnClickListener(this);
        findViewById(R.id.icon_4_btn).setOnClickListener(this);
        findViewById(R.id.icon_5_btn).setOnClickListener(this);
        GridView lv = (GridView) findViewById(R.id.lv);
        adapter = new EmojiIconAdapter(this);
        lv.setAdapter(adapter);
        onClick(R.id.icon_5_btn);
    }

    @Override
    public void onClick(View v) {
        onClick(v.getId());
    }
    private void onClick(int id){
        EmojiAnalysisXML emojiXML=  EmojiAnalysisXML.getInstance();
        switch (id) {
            case R.id.icon_1_btn:
                adapter.setData(emojiXML.getEmoMap().get("people"));
                break;
            case R.id.icon_2_btn:
                adapter.setData(emojiXML.getEmoMap().get("nature"));
                break;
            case R.id.icon_3_btn:
                adapter.setData(emojiXML.getEmoMap().get("objects"));
                break;
            case R.id.icon_4_btn:
                adapter.setData(emojiXML.getEmoMap().get("places"));
                break;
            case R.id.icon_5_btn:
                adapter.setData(emojiXML.getEmoMap().get("symbols"));
                break;
        }
    }
}

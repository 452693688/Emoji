package activity.ui.com.emoji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import activity.ui.com.emoji.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_emoji_btn).setOnClickListener(this);
        findViewById(R.id.main_editext_btn).setOnClickListener(this);
        findViewById(R.id.main_editext2_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent();
        switch (v.getId()){
            case R.id.main_emoji_btn:
                it.setClass(this,EmojiActivity.class);
                break;
            case R.id.main_editext_btn:
                it.setClass(this,EditextActivity.class);
                break;
            case R.id.main_editext2_btn:
                it.setClass(this,EmojiReplaceActivity.class);
                break;

        }
        startActivity(it);
    }
}

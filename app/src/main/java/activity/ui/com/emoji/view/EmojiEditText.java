package activity.ui.com.emoji.view;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import activity.ui.com.emoji.utile.EmojiReplace;

/**
 * Created by Administrator on 2016/6/30.
 */
public class EmojiEditText extends android.support.v7.widget.AppCompatEditText {
    private String tag = "EmojiEditText";

    public EmojiEditText(Context context) {
        super(context);
        initEditText(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditText(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEditText(context);
    }


    private Context context;

    // 初始化edittext 控件
    private void initEditText(Context context) {
        this.context = context;
        addTextChangedListener(new EditTextImportListener());
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);
    }

    private CharSequence inputMsg = "";

    //获取输入的内容 用于显示在TextView, 如果用string接收会自动转化为文本
    public CharSequence getMsgSequence() {
        return inputMsg;
    }

    //获取输入的内容 用于显示发送到服务器
    public String getMsgTxt() {
        String result = inputMsg.toString();
        return result;
    }

    @Override
    public Editable getText() {
        return super.getText();
    }

    class EditTextImportListener implements TextWatcher {
        private boolean isChange;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            CharSequence input = s.subSequence(start, start + count);
            Log.e(tag, "input1：" + input.toString());
            try {
                String  m = URLEncoder.encode(s.toString(), "UTF-8");
                Log.e(tag, "编码：" + m);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //解码
            try {
                String decodeStr = URLDecoder.decode(s.toString(), "UTF-8");
                Log.e(tag, "解码1：" + decodeStr);
                decodeStr = URLDecoder.decode(s.toString(), "UTF-8");
                Log.e(tag, "解码2：" + decodeStr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            inputMsg = s;
            if (TextUtils.isEmpty(input) || isChange) {
                isChange = false;
                return;
            }
            SpannableStringBuilder emojinIcon = EmojiReplace.replaceEmoji(input.toString());
            if (emojinIcon == null || emojinIcon.length() == 0) {
                return;
            }

            isChange = true;
            SpannableStringBuilder result = new SpannableStringBuilder(s);
            result.replace(start, start + count, emojinIcon);
            Log.e(tag, "------------");
            setText(result);
            Log.e(tag, "2：" + result);
            setSelection(start + (emojinIcon.length()));
            Log.e(tag, "input2：" + input.toString());
            Log.e(tag, "input3：" + inputMsg.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}

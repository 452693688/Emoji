package activity.ui.com.emoji.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import activity.ui.com.emoji.R;

/**
 * Created by Administrator on 2016/7/4.
 */
public class EmojiIconAdapter extends BaseAdapter {
    private ArrayList<String> emojis = new ArrayList<String>();
    private Drawable drawable;
    private Context context;
    private Resources resources;
    private String pckName;

    public EmojiIconAdapter(Context context) {
        this.context = context;
        resources = context.getResources();
        pckName = context.getPackageName();
    }

    public void setData(ArrayList<String> emoji) {
        emojis = emoji;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return emojis.size();
    }

    @Override
    public Object getItem(int position) {
        return emojis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            holder.emoticon = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_emoji, null);
            convertView = holder.emoticon;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String emojiName = "emoji_" + emojis.get(position);
        //emoji_0030
        emojiName=emojiName.replace("_20e3", "");
        Log.e("emojiName:名称", emojiName);

        holder.emoticon.setImageResource(resources.getIdentifier(emojiName, "mipmap", pckName));
        return convertView;
    }

    class ViewHolder {
        ImageView emoticon;
    }
}
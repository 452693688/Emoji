package activity.ui.com.emoji.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/6/30.
 */
public class MainApplication extends Application{
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
}

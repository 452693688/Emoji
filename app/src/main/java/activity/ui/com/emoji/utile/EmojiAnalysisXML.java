package activity.ui.com.emoji.utile;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
public class EmojiAnalysisXML {
    private final String tag = "EmojiClient";
    private HashMap<List<Integer>, String> convertMap = new HashMap<List<Integer>, String>();
    private HashMap<String, ArrayList<String>> emoMap = new HashMap<String, ArrayList<String>>();
    private static EmojiAnalysisXML mParser;

    private EmojiAnalysisXML(Context mContext) {
         readMap(mContext);
    }


    public static EmojiAnalysisXML getInstance(Context mContext) {
        if (mParser == null||mParser.convertMap.size()==0||mParser.emoMap.size()==0) {
            mParser = new EmojiAnalysisXML(mContext);
        }
        return mParser;
    }

    private void readMap(Context context) {
        convertMap = new HashMap<List<Integer>, String>();
        XmlPullParser xmlpull = null;
        String fromAttr = null;
        String key = null;
        ArrayList<String> emos = null;
        try {
            XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
            xmlpull = xppf.newPullParser();
            InputStream stream = context.getAssets().open("emoji.xml");
            xmlpull.setInput(stream, "UTF-8");
            int eventCode = xmlpull.getEventType();
            while (eventCode != XmlPullParser.END_DOCUMENT) {
                switch (eventCode) {
                    case XmlPullParser.START_DOCUMENT: {
                        break;
                    }
                    case XmlPullParser.START_TAG: {
                        if (xmlpull.getName().equals("key")) {
                            emos = new ArrayList<String>();
                            key = xmlpull.nextText();
                        }
                        if (xmlpull.getName().equals("e")) {
                            fromAttr = xmlpull.nextText();
                            emos.add(fromAttr);
                            List<Integer> fromCodePoints = new ArrayList<Integer>();
                            if (fromAttr.length() > 6) {
                                String[] froms = fromAttr.split("\\_");
                                for (String part : froms) {
                                    fromCodePoints.add(Integer.parseInt(part, 16));
                                }
                            } else {
                                fromCodePoints.add(Integer.parseInt(fromAttr, 16));
                            }
                            convertMap.put(fromCodePoints, fromAttr);
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if (xmlpull.getName().equals("dict")) {
                            emoMap.put(key, emos);
                        }
                        break;
                    }
                    case XmlPullParser.END_DOCUMENT: {
                        Log.e(tag, "parse emoji complete");
                        break;
                    }
                }
                eventCode = xmlpull.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public HashMap<List<Integer>, String> getConvertMap() {
        return convertMap;
    }
    public HashMap<String, ArrayList<String>> getEmoMap() {
        return emoMap;
    }
}

package mastersunny.unitedclub.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by ASUS on 2/1/2018.
 */

public class TextViewRegularFont extends android.support.v7.widget.AppCompatTextView {

    public TextViewRegularFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewRegularFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewRegularFont(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "AVENIRLTSTD-REGULAR.OTF");
        setTypeface(tf, 1);

    }
}

package mastersunny.unitedclub.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ASUS on 2/1/2018.
 */

public class TextViewMediumFont extends android.support.v7.widget.AppCompatTextView {

    public TextViewMediumFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewMediumFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewMediumFont(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "AVENIRLTSTD-MEDIUM.otf");
        setTypeface(tf, 1);

    }
}

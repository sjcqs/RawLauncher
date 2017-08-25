package com.sjcqs.rawlauncher.items.apps;

import android.graphics.drawable.Drawable;

/**
 * Created by satyan on 8/24/17.
 */

abstract class Item {

    protected CharSequence label;
    protected Drawable icon;

    public String getLabel(){
        return label.toString();
    }

    public Drawable getIcon(){
        return icon;
    }
}

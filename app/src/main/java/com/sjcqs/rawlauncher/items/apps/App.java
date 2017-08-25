package com.sjcqs.rawlauncher.items.apps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by satyan on 8/24/17.
 */

class App extends Item{
    static final Comparator<? super App> ALPHA_COMPARATOR = new Comparator<App>() {
        private final Collator COLLATOR = Collator.getInstance(Locale.getDefault());

        @Override
        public int compare(App app1, App app2) {
            return (COLLATOR.compare(app1.getLabel(), app2.getLabel()));
        }
    };
    private final Context context;
    private final ApplicationInfo info;

    private boolean mounted = false;
    private  final File apkFile;

    App(final Context context, ApplicationInfo info){
        this.info = info;
        this.context = context;

        this.apkFile = new File(info.sourceDir);
    }

    @Override
    public String getLabel() {
        loadLabel();
        return label.toString();
    }

    private String getPackageName(){
        return info.packageName;
    }

    @Override
    public Drawable getIcon(){
        if (icon == null){
            if (apkFile.exists()){
                icon = info.loadIcon(context.getPackageManager());
                return icon;
            } else {
                mounted = false;
            }
        } else if(!mounted){
            if (apkFile.exists()){
                mounted = true;
                icon = info.loadIcon(context.getPackageManager());
                return icon;
            }
        } else {
            return icon;
        }

        return context.getResources().getDrawable(android.R.drawable.sym_def_app_icon,null);
    }


    void loadLabel() {
        if (label == null || !mounted){
            if (apkFile.exists()){
                mounted = true;
                CharSequence seq = info.loadLabel(context.getPackageManager());
                label = seq  != null ? seq.toString() : info.packageName;
            } else {
                mounted = false;
                label = info.packageName;
            }
        }
    }

    public Intent getLaunchIntentForPackage(){
        return context.getPackageManager().getLaunchIntentForPackage(getPackageName());
    }
}

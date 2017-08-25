package com.sjcqs.rawlauncher.items.apps;


import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sjcqs.rawlauncher.utils.StringUtil;
import com.sjcqs.rawlauncher.utils.interfaces.Manager;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by satyan on 8/24/17.
 * Manage
 */

public class AppManager extends Manager implements LoaderManager.LoaderCallbacks<List<App>> {
    private static final String TAG = AppManager.class.getName();
    private List<App> apps;

    public AppManager(AppCompatActivity activity) {
        super(activity);
        activity.getSupportLoaderManager().initLoader(0,null,this);
    }

    @Override
    public boolean isLoaded() {
        return apps != null;
    }

    @Override
    public Loader<List<App>> onCreateLoader(int id, Bundle args) {
        return new AppsLoader(activity);
    }

    @Override
    public void onLoadFinished(Loader<List<App>> loader, List<App> data) {
        apps = data;
        for (App app : apps) {
            Log.d(TAG, "app: "+app.getLabel());
        }
    }

    @Override
    public void onLoaderReset(Loader<List<App>> loader) {
        apps = null;
    }

    public List<App> suggestApps(String str1){
        List<App> suggestions = new ArrayList<>();
        if (isLoaded()) {
            Log.d(TAG, "===============");
            for (App app : apps) {
                String str2 = app.getLabel();
                if (StringUtil.canBeSuggested(str1,str2)){
                    suggestions.add(app);
                    Log.d(TAG, "suggestApps: "+str2);
                }
            }
            Log.d(TAG, "===============");
        }
        return suggestions;
    }

}

package com.sjcqs.rawlauncher;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sjcqs.rawlauncher.utils.interfaces.Reloadable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satyan on 9/6/17.
 */

public class HistoryManager implements Reloadable {
    private static final String TAG = HistoryManager.class.getName();
    private static final int HISTORY_SIZE = 20;

    private final Context context;
    private int index = 0;
    private List<String> history = new ArrayList<>();

    public HistoryManager(Context context) {
        this.context = context;
    }


    @Override
    public void reload() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int size = pref.getInt(context.getString(R.string.history_size_shared_preferences), 0);
        index = 0;
        for (int i = 0; i < size && i < HISTORY_SIZE; i++) {
            String item = pref
                    .getString(context.getString(R.string.history_item_shared_preferences, i), "");
            history.add(item);
        }
    }

    public String previous() {
        String str = "";
        if (history.size() > index && index >= 0) {
            str = history.get(index);
        }
        index--;
        if (index < 0) {
            index = 0;
        }
        return str;
    }

    public String next() {
        String str = "";
        if (history.size() > index && index >= 0) {
            str = history.get(index);
        }
        index++;
        if (index >= history.size()) {
            index = history.size() - 1;
        }
        return str;
    }

    public void push(String str) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        if (history.size() == HISTORY_SIZE) {
            history = history.subList(1, HISTORY_SIZE);
            history.add(str);
            for (int i = 0; i < history.size(); i++) {
                editor.putString(
                        context.getString(R.string.history_item_shared_preferences, i),
                        history.get(i)
                );
            }
        } else {
            history.add(str);
            editor.putString(
                    context.getString(R.string.history_item_shared_preferences, history.size() - 1),
                    str
            );
        }
        editor.putInt(context.getString(R.string.history_size_shared_preferences), history.size());
        editor.apply();

    }
}

package com.sjcqs.rawlauncher;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sjcqs.rawlauncher.items.apps.AppManager;
import com.sjcqs.rawlauncher.items.suggestions.SuggestionManager;
import com.sjcqs.rawlauncher.views.UserInputView;

/**
 * The main launcher activity
 */
public class RawLauncher extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private static final String TAG = RawLauncher.class.getName();
    private UserInputView inputView;
    private View rootView;
    private AppManager appManager;
    private SuggestionManager suggestionManager;
    private RecyclerView suggestionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = getLayoutInflater().inflate(R.layout.activity_raw_launcher,null);
        setContentView(rootView);
        appManager = new AppManager(this);
        inputView = (UserInputView) findViewById(R.id.user_input_view);
        suggestionRecyclerView = (RecyclerView) findViewById(R.id.suggestions);
        suggestionManager = new SuggestionManager(appManager, this);
        suggestionRecyclerView.setAdapter(suggestionManager);
        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 0){
                    final String str = charSequence.toString();
                    inputView.post(new Runnable() {
                        @Override
                        public void run() {
                            suggestionManager.suggest(str);
                        }
                    });
                } else {
                    suggestionManager.clearSuggestions();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();

        rootView.post(new Runnable() {

            @Override
            public void run() {
                rootView.setBackground(wallpaperDrawable);
            }
        });
    }
}

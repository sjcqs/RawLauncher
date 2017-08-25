package com.sjcqs.rawlauncher.items.suggestions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjcqs.rawlauncher.R;
import com.sjcqs.rawlauncher.items.Item;
import com.sjcqs.rawlauncher.items.apps.App;
import com.sjcqs.rawlauncher.items.apps.AppManager;
import com.sjcqs.rawlauncher.utils.interfaces.Launchable;
import com.sjcqs.rawlauncher.utils.interfaces.SuggestionUpdator;
import com.sjcqs.rawlauncher.utils.interfaces.Suggestor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by satyan on 8/25/17.
 */

public class SuggestionManager extends  RecyclerView.Adapter<SuggestionManager.ItemHolder> implements Suggestor, Launchable {
    private static final String TAG = SuggestionManager.class.getName();
    private final AppManager appManager;
    private final Context context;

    private List<Item> suggestions;
    private Map<Class<? extends Item>, List<Item>> suggestionsMap;

    public SuggestionManager(AppManager appManager, Context context) {
        this.appManager = appManager;
        this.context = context;
        suggestions = new ArrayList<>();
        suggestionsMap = new ArrayMap<>();
    }


    @Override
    public List<? extends Item> suggest(String input) {
        List<Item> apps = suggestionsMap.get(App.class);
        if (apps == null){
            apps = new ArrayList<>();
        }
        updateSuggestions(App.class,appManager.updateSuggestions(input,apps));

        return suggestions;
    }

    private void updateSuggestions(Class<? extends Item> itemClass, SuggestionUpdator.SuggestionUpdate update) {
        List<Item> items = suggestionsMap.get(itemClass);
        if (items == null){
            items = new ArrayList<>();
        }

        items.addAll(update.getToAdd());
        suggestions.addAll(update.getToAdd());

        Collections.sort(suggestions,Item.ALPHA_COMPARATOR);
        items.removeAll(update.getToRemove());
        suggestions.removeAll(update.getToRemove());

        suggestionsMap.put(App.class,items);

        notifyDataSetChanged();
    }

    @Override
    public void clearSuggestions() {
        int size = suggestions.size();
        suggestions.clear();
        suggestionsMap.clear();
        notifyItemRangeRemoved(0,size);
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final Item item = suggestions.get(position);
        if (item != null) {
            holder.setIcon(item.getIcon());
            holder.setLabel(item.getLabel());
            holder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    context.startActivity(item.getIntent());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView labelView;
        private ImageView iconView;

        ItemHolder(View itemView) {
            super(itemView);
            labelView = itemView.findViewById(R.id.label);
            iconView = itemView.findViewById(R.id.item_icon);
        }

        void setIcon(Drawable drawable){
            iconView.setImageDrawable(drawable);
        }

        void setLabel(String text){
            labelView.setText(text);
        }

        void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}

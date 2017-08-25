package com.sjcqs.rawlauncher.items.suggestions;

import android.content.Intent;

import com.sjcqs.rawlauncher.items.Item;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by satyan on 8/25/17.
 */

public class Suggestion extends Item{
    public static final Comparator<? super Suggestion> SUGGESTION_COMPARATOR =
            new Comparator<Suggestion>() {
                private final Collator COLLATOR = Collator.getInstance(Locale.getDefault());

                @Override
                public int compare(Suggestion item1, Suggestion item2) {
                    int rateCmp = Double.compare(item1.getRate(),item2.getRate());
                    return rateCmp == 0 ?
                            rateCmp : (COLLATOR.compare(item1.getLabel(), item2.getLabel()));
                }
            };

    private final Item item;
    private final double rate;


    public Suggestion(Item item, double rate) {
        label = item.getLabel();
        icon = item.getIcon();
        this.item = item;
        this.rate = rate;
    }

    @Override
    public Intent getIntent() {
        return item.getIntent();
    }

    public double getRate() {
        return rate;
    }

    public Item getItem() {
        return item;
    }
}

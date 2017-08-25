package com.sjcqs.rawlauncher.utils.interfaces;

import com.sjcqs.rawlauncher.items.Item;

import java.util.List;

/**
 * Created by satyan on 8/25/17.
 */

public interface Suggestor {
    List<? extends Item> suggest(String input);
    void clearSuggestions();
}

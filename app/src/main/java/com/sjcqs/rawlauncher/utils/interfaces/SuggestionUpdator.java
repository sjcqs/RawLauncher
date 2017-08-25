package com.sjcqs.rawlauncher.utils.interfaces;

import com.sjcqs.rawlauncher.items.Item;

import java.util.List;

/**
 * Created by satyan on 8/25/17.
 */

public interface SuggestionUpdator {

    SuggestionUpdate updateSuggestions(String input, List<Item> current);

    public static class SuggestionUpdate{
        private List<Item> toRemove;
        private List<Item> toAdd;

        public SuggestionUpdate(List<Item> toRemove, List<Item> toAdd) {
            this.toRemove = toRemove;
            this.toAdd = toAdd;
        }

        public List<Item> getToRemove() {
            return toRemove;
        }

        public List<Item> getToAdd() {
            return toAdd;
        }
    }

}

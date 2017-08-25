package com.sjcqs.rawlauncher.utils;

import java.text.Normalizer;

/**
 * Created by satyan on 8/24/17.
 */

public final class StringUtil {
    private static final String TAG = StringUtil.class.getName();

    public static boolean canBeSuggested(String input, String str){
        input = normalize(input).toUpperCase();
        str = normalize(str).toUpperCase();

        return input.contains(str) || str.contains(input);
    }

    public static String normalize(String str){
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }
}

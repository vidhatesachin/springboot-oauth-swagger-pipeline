package com.springboot.sample.util;

import java.util.Locale;
import java.util.ResourceBundle;
    
public class Localizer {
    private final static String RESOURCE_BUNDLE = "messages";
    
    
    public static String getLocalizedText(String key,String language)
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE,
                    (language.equals("nl")?Locale.GERMAN:Locale.ENGLISH), Localizer.class.getClassLoader());
    
            if (bundle.keySet().contains(key)) {
                return bundle.getString(key);
            }
            else {
                return key + "(No localization entry found)";
            }
        }
        catch (Exception e)
        {
            return "LOCALIZATION FAILED: " + e.toString();
        }
    }
}
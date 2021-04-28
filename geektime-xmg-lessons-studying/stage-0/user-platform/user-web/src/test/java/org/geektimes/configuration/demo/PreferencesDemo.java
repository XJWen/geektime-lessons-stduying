package org.geektimes.configuration.demo;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PreferencesDemo {

    public static void main(String[] args) throws BackingStoreException {
        Preferences userPreference = Preferences.userRoot();
        userPreference.put("my-key","Hello World");
        userPreference.flush();
        System.out.println(userPreference.get("my-key",""));
        Preferences.systemRoot();
    }
}

package com.yougen.anticafemanager;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.app.ActionBar;
import android.preference.SwitchPreference;
import android.view.MenuItem;


/**
 * Created by Konstantin on 23.10.2017.
 */

public class Settings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

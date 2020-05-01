package com.hoverhackathon.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.hoverhackathon.R;
import com.hoverhackathon.ui.fragment.UtilityFragment;

public class ActivityUtility extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Utility");


        UtilityFragment utilityFragment = new UtilityFragment();
        startFragment(utilityFragment);

    }
    public void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.utility_fragment_container, fragment)
                .commit();
    }
}

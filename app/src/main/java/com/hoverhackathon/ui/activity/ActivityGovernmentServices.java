package com.hoverhackathon.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.hoverhackathon.R;
import com.hoverhackathon.ui.fragment.BanksSaccosFragment;
import com.hoverhackathon.ui.fragment.GovernmentServicesFragment;

public class ActivityGovernmentServices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_services);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GovernmentServicesFragment governmentServicesFragment = new GovernmentServicesFragment();
        startFragment(governmentServicesFragment);
    }

    public void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.gvt_fragment_container, fragment)
                .commit();
    }
}

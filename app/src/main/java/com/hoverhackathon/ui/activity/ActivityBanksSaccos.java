package com.hoverhackathon.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.hoverhackathon.R;
import com.hoverhackathon.ui.fragment.BanksSaccosFragment;

public class ActivityBanksSaccos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks_saccos);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BanksSaccosFragment banksSaccosFragment = new BanksSaccosFragment();
        startFragment(banksSaccosFragment);

    }
    public void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.banks_fragment_container, fragment)
                .commit();
    }
}

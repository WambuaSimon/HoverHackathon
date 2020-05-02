package com.hoverhackathon.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hoverhackathon.R;

public class DashboardActivity extends AppCompatActivity {
CardView utility,banks_sacco,county,gvt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        gvt = findViewById(R.id.gvt);
        county = findViewById(R.id.county);
        utility = findViewById(R.id.utility);
        banks_sacco = findViewById(R.id.banks_sacco);
        utility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityUtility.class);
                startActivity(intent);
            }
        });
        banks_sacco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityBanksSaccos.class);
                startActivity(intent);
            }
        });
        county.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityCountyServices.class);
                startActivity(intent);
            }
        });
        gvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityGovernmentServices.class);
                startActivity(intent);
            }
        });
    }
}

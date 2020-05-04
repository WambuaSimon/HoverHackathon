package com.hoverhackathon.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hoverhackathon.R;
import com.hoverhackathon.adapter.PromotionalMessagesAdapter;
import com.hoverhackathon.model.PromotionalMessagesModel;
import com.hoverhackathon.ui.fragment.BanksSaccosFragment;
import com.hoverhackathon.ui.fragment.PromotionalMessagesFragment;

import java.util.ArrayList;
import java.util.List;

public class ActivityPromotionalMessages extends AppCompatActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotional_messages);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PromotionalMessagesFragment promotionalMessagesFragment = new PromotionalMessagesFragment();
        startFragment(promotionalMessagesFragment);
    }
    public void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.messages_fragment_container, fragment)
                .commit();
    }

}

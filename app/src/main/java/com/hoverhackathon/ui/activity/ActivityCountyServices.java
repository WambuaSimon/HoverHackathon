package com.hoverhackathon.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hoverhackathon.R;
import com.hoverhackathon.adapter.CountyServicesAdapter;
import com.hoverhackathon.model.CountyModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityCountyServices extends AppCompatActivity implements CountyServicesAdapter.CountyServicesAdapterListener, AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    List<CountyModel> countyModelList;
    CountyServicesAdapter adapter;
    SearchView searchView;
      Spinner spinner;
      EditText plate_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county_services);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        countyModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);

        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);

        adapter = new CountyServicesAdapter(this, countyModelList, this);
        DisplayCounties();
        AddCounty();

    }


    void DisplayCounties() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.scheduleLayoutAnimation();
    }

    void AddCounty() {
        CountyModel nrb = new CountyModel("Nairobi County");
        countyModelList.add(nrb);
        CountyModel kiambu = new CountyModel("Kiambu County");
        countyModelList.add(kiambu);
        CountyModel nyeri = new CountyModel("Nyeri County");
        countyModelList.add(nyeri);
        CountyModel msa = new CountyModel("Mombasa County");
        countyModelList.add(msa);
        CountyModel nakuru = new CountyModel("Nakuru County");
        countyModelList.add(nakuru);
        CountyModel kakamega = new CountyModel("Kakamega County");
        countyModelList.add(kakamega);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onCountyModelSelected(CountyModel county) {

        payDialog(county.getCountyName());
    }

    void payDialog(String county) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActivityCountyServices.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_country_layout, null);
        dialogBuilder.setView(dialogView);

      final  EditText amount;;
        Button pay, cancel;

        TextView bill_name = dialogView.findViewById(R.id.bill_name);
        bill_name.setText(county);

        spinner = dialogView.findViewById(R.id.spinner);
        plate_no = dialogView.findViewById(R.id.plate_no);
        amount = dialogView.findViewById(R.id.amount);
        pay = dialogView.findViewById(R.id.pay);
        cancel = dialogView.findViewById(R.id.cancel);

        plate_no.setVisibility(View.GONE);
        spinner.setOnItemSelectedListener(this);
        List<String> services = new ArrayList<>();
        services.add("Select Service");
        services.add("Parking");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, services);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        final AlertDialog alertDialog = dialogBuilder.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().isEmpty()) {
                    amount.setError("Enter Amount");
                } else {
                    amount.setError(null);
                }

                /*TODO: HOVER STUFF GOES HERE*/

//                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(item.equalsIgnoreCase("Parking")){
            plate_no.setVisibility(View.VISIBLE);
        }
        else{
            plate_no.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

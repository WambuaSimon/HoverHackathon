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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hoverhackathon.R;
import com.hoverhackathon.adapter.CountyServicesAdapter;
import com.hoverhackathon.model.CountyModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityCountyServices extends AppCompatActivity implements CountyServicesAdapter.CountyServicesAdapterListener {
    RecyclerView recyclerView;
    List<CountyModel> countyModelList;
    CountyServicesAdapter adapter;
    SearchView searchView;

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
        CountyModel nrb = new CountyModel("Nairobi");
        countyModelList.add(nrb);
        CountyModel kiambu = new CountyModel("Kiambu");
        countyModelList.add(kiambu);
        CountyModel nyeri = new CountyModel("Nyeri");
        countyModelList.add(nyeri);
        CountyModel msa = new CountyModel("Mombasa");
        countyModelList.add(msa);
        CountyModel embu = new CountyModel("Embu");
        countyModelList.add(embu);
        CountyModel meru = new CountyModel("Meru");
        countyModelList.add(meru);
        CountyModel tharaka = new CountyModel("Tharaka-Nithi");
        countyModelList.add(tharaka);
        CountyModel kajiado = new CountyModel("Kajiado");
        countyModelList.add(kajiado);
        CountyModel kericho = new CountyModel("Kericho");
        countyModelList.add(kericho);
        CountyModel laikipia = new CountyModel("Laikipia");
        countyModelList.add(laikipia);
        CountyModel nakuru = new CountyModel("Nakuru");
        countyModelList.add(nakuru);
        CountyModel trans = new CountyModel("Trans-Nzoia");
        countyModelList.add(trans);
        CountyModel kakamega = new CountyModel("Kakamega");
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
        View dialogView = inflater.inflate(R.layout.payment_dialog, null);
        dialogBuilder.setView(dialogView);


        final EditText paybill, account_no, number;
        Button pay, cancel;

        TextView bill_name = dialogView.findViewById(R.id.bill_name);
        bill_name.setText(county);
        paybill = dialogView.findViewById(R.id.paybill);
        account_no = dialogView.findViewById(R.id.account_no);
        number = dialogView.findViewById(R.id.number);
        pay = dialogView.findViewById(R.id.pay);
        cancel = dialogView.findViewById(R.id.cancel);


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

                if (paybill.getText().toString().isEmpty()) {
                    paybill.setError("Enter Paybill");
                } else {
                    paybill.setError(null);
                }
                if (account_no.getText().toString().isEmpty()) {
                    account_no.setError("Enter Paybill");
                } else {
                    account_no.setError(null);
                }
                if (number.getText().toString().isEmpty()) {
                    number.setError("Enter Paybill");
                } else {
                    number.setError(null);
                }

                /*TODO: HOVER STUFF GOES HERE*/

//                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }
}

package com.hoverhackathon.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hoverhackathon.DB.AppDatabase;
import com.hoverhackathon.R;
import com.hoverhackathon.adapter.TransactionAdapter;
import com.hoverhackathon.model.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityTransactionSummary extends AppCompatActivity {
    List<TransactionModel> transactionModelList;
    RecyclerView recyclerView;
    TransactionAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_summary);
        init();
    }

    void init() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        transactionModelList = new ArrayList<>();

        getTransactionList();
    }

    private void getTransactionList() {

        class GetTransactionList extends AsyncTask<Void, Void, List<TransactionModel>> {

            @Override
            protected List<TransactionModel> doInBackground(Void... voids) {
                List<TransactionModel> childModels = AppDatabase.getCfctDatabase(getApplicationContext()).transactionDAO().getTransactions();
                Log.d("TransactionModel", String.valueOf(childModels.size()));
                return childModels;

            }

            @Override
            protected void onPostExecute(List<TransactionModel> transactionModels) {
                super.onPostExecute(transactionModels);
                adapter = new TransactionAdapter(transactionModels, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
        GetTransactionList transactionList = new GetTransactionList();
        transactionList.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);
        SearchManager searchManager = (SearchManager) ActivityTransactionSummary.this.getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

//                Toast.makeText(ctx, "Stuff changed", Toast.LENGTH_SHORT).show();
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

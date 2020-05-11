package com.hoverhackathon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hoverhackathon.DB.AppDatabase;
import com.hoverhackathon.model.TransactionModel;

import java.util.HashMap;

import static com.android.volley.VolleyLog.TAG;

public class TransactionReceiver extends BroadcastReceiver {
    public TransactionReceiver(/*Context cont,String msisdn,*/) {

        //this.context=cont;
    }
    String actionName,convtimeStamp;
    long timeStamp;
    String error;
    @Override
    public void onReceive(final Context context, Intent intent) {
        String uuid = intent.getStringExtra("uuid");
         actionName=intent.getStringExtra("action_name");
       //  timeStamp=intent.getLongExtra("request_timestamp");
         timeStamp=intent.getLongExtra("request_timestamp", 0);
          convtimeStamp="" +timeStamp;
         error=intent.getStringExtra("error");
        String confirmationCode, balance;
        if (intent.hasExtra("parsed_variables")) {
            HashMap<String, String> parsed_variables = (HashMap<String, String>) intent.getSerializableExtra("parsed_variables");
            if (parsed_variables.containsKey("confirmCode"))
                confirmationCode = parsed_variables.get("confirmCode");
            if (parsed_variables.containsKey("balance"))
                balance = parsed_variables.get("balance");
        }


    }

    void saveUtility(final String name, final String status, final String timestamp){
        class Saveutility extends AsyncTask<Void,Void,Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                TransactionModel transactionModel = new TransactionModel();
                transactionModel.setName(actionName);
                transactionModel.setName(error);
                transactionModel.setName(convtimeStamp);

                AppDatabase.getCfctDatabase(getActivity()).transactionDAO().insertTransaction(transactionModel);
                return null;
            }

            private Context getActivity() {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d(TAG,"Success");
            }
        }
        Saveutility saveutility = new Saveutility();
        saveutility.execute();

    }
}






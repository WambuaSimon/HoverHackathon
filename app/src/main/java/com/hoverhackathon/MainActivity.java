package com.hoverhackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;
import com.hoverhackathon.DB.AppDatabase;
import com.hoverhackathon.ui.activity.DashboardActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_SMS;

public class MainActivity extends AppCompatActivity {
    private static final int TYPE_INCOMING_MESSAGE = 1;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private ListView messageList;
    private MessageListAdapter messageListAdapter;
    private List<Message> recordsStored;
    String number;
    Button proceed;
    boolean isFirstRun;
    SharedPreferences wmbPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize hover
        DownloadListener xv = new DownloadListener();
        Hover.initialize(Objects.requireNonNull(getApplicationContext()));
        Hover.updateActionConfigs(xv, Objects.requireNonNull(getApplicationContext()));
        if (!checkPermission()) {  // this line checks permission everytime you access this activity
            Toast.makeText(getApplicationContext(), "Grant Permission to View Messages", Toast.LENGTH_SHORT).show();
            requestPermission();
        } else {
            wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
            isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
            if (isFirstRun) {
                fetchInboxSms();
                // Code to run once
                SharedPreferences.Editor editor = wmbPreference.edit();
                editor.putBoolean("FIRSTRUN", false);
                editor.commit();
            }


            setContentView(R.layout.activity_main);

            initViews();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
//        populateMessageList();
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recordsStored = new ArrayList<>();

        messageList = findViewById(R.id.messageList);
        proceed = findViewById(R.id.proceed);
        populateMessageList();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d("recordsStored", String.valueOf(recordsStored.size()));
                List<String> messages = new ArrayList<>();
                Handler handler1 = new Handler();
                for (int m = 0; m < recordsStored.size(); m++) {
                    Message msg = recordsStored.get(m);
                    if (msg.isChecked) {
                        Message msgList = recordsStored.get(m);

//                        messages=recordsStored;
                        number = recordsStored.get(m).messageNumber.toLowerCase().trim();
                        messages.add(msgList.getMessageNumber());
                        for (int n = 0; n < messages.size(); n++) {

                            Log.d("recordsStored", messages.get(n));
                            int times = messages.size();
                            for (int i = 0; i < times; ++i) {
                                Intent unsubscribe = new HoverParameters.Builder(getApplicationContext())
                                        //.extra("", "")
                                        .request("1a5963da")
                                        .extra("SenderName", messages.get(n))
                                        //.setEnvironment(HoverParameters.DEBUG_ENV)
                                        .buildIntent();
                                startActivityForResult(unsubscribe, 0);
                            }

                        }

                        Log.d("recordsStored", number);
                        updateTask(msgList);
                        recordsStored.remove(m--);
                        messageListAdapter.notifyDataSetChanged();


                        /*TODO: after unsubscribing, update ROOM DB with status ==1, then remove item from list view */


                    }
                }

            }

        });

    }

    private void updateTask(final Message task) {

        class UpdateMessage extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
//                task.setMessageNumber(task.messageNumber);
                AppDatabase.getCfctDatabase(getApplicationContext()).messageDAO().updateMessage(task.messageNumber);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                Log.d("Update Message", "Success");

            }
        }
        UpdateMessage ut = new UpdateMessage();
        ut.execute();

    }

    void GetMessages() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Messages...");

        class getMessages extends AsyncTask<Void, Void, List<Message>> {
            @Override
            protected List<Message> doInBackground(Void... voids) {

                List<Message> messages = AppDatabase.getCfctDatabase(getApplicationContext()).messageDAO().getMessages();
                recordsStored = messages;
                return messages;

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(List<Message> messages) {
                super.onPostExecute(messages);
                progressDialog.dismiss();
//                Log.d("MessagesList", String.valueOf(messages.size()));
                for (int i = 0; i < messages.size(); i++) {
                    Message message = messages.get(i);
                    int item = message.getStatus();
                    String address = message.getMessageNumber();
//                    Log.d("MessageItemValue", address + " " + item);

                    messageListAdapter = new MessageListAdapter(MainActivity.this,
                            R.layout.message_list_item, messages);
                    messageList.setAdapter(messageListAdapter);

                }
            }
        }
        getMessages msg = new getMessages();
        msg.execute();
    }

    public void populateMessageList() {
        GetMessages();

    }

    public void fetchInboxSms() {
        final List<Message> smsInbox = new ArrayList<>();
        Uri uriSms = Uri.parse("content://sms");
        final Cursor cursor = this.getContentResolver()
                .query(uriSms,
                        new String[]{"_id", "address", "date", "body",
                                "type", "read"}, "address IS NOT NULL) GROUP BY (address", //GROUP BY
                        null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {

                do {

                    final Message message = new Message();
                    message.messageNumber = cursor.getString(cursor
                            .getColumnIndex("address"));

                    if (!message.messageNumber.equalsIgnoreCase("Safaricom") &&
                            !message.messageNumber.equalsIgnoreCase("Mshwari") &&
                            !message.messageNumber.equalsIgnoreCase("KPLC") &&
                            !message.messageNumber.equalsIgnoreCase("Verify") &&
                            !message.messageNumber.equalsIgnoreCase("Mpesa") &&
                            !message.messageNumber.equalsIgnoreCase("iandmbank") &&
                            !message.messageNumber.equalsIgnoreCase("safhome") &&
                            !message.messageNumber.equalsIgnoreCase("authmsg") &&
                            !message.messageNumber.equalsIgnoreCase("google") &&
                            message.messageNumber.length() < 10) {

//                        address = cursor.getString(cursor
//                                .getColumnIndex("address"));
//                        Log.d("AddressValue", address);
//                        smsInbox.add(message);

                        class SaveMessage extends AsyncTask<Void, Void, Void> {

                            @Override
                            protected Void doInBackground(Void... voids) {

                                AppDatabase.getCfctDatabase(getApplicationContext()).messageDAO().insertMessage(message);

                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
//                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        }
                        SaveMessage saveMessage = new SaveMessage();
                        saveMessage.execute();


                    }

                } while (cursor.moveToNext());

            }

        }

    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, READ_SMS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(getApplicationContext(), "Please allow access to continue!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
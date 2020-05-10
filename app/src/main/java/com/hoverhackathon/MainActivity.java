package com.hoverhackathon;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hoverhackathon.DB.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int TYPE_INCOMING_MESSAGE = 1;
    private ListView messageList;
    private MessageListAdapter messageListAdapter;
    private List<Message> recordsStored;
    Button proceed;
    boolean isFirstRun;
    SharedPreferences wmbPreference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
             isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
            if (isFirstRun)
            {
                fetchInboxSms();
                // Code to run once
                SharedPreferences.Editor editor = wmbPreference.edit();
                editor.putBoolean("FIRSTRUN", false);
                editor.commit();
            }
        setContentView(R.layout.activity_main);

        initViews();
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
                Log.d("recordsStored", String.valueOf(recordsStored.size()));

                for (int m = 0; m < recordsStored.size(); m++) {
                    Message msg = recordsStored.get(m);
                    if (msg.isChecked) {
                        Message msgList = recordsStored.get(m);
                        String number = recordsStored.get(m).messageNumber.toLowerCase().trim();
//                        Log.d("recordsStored", number);
                        updateTask(msgList);
                        recordsStored.remove(m--);
                        messageListAdapter.notifyDataSetChanged();

                    }
                    /*TODO: after unsubscribing, update ROOM DB with status ==1, then remove item from list view */
//
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
        class getMessages extends AsyncTask<Void, Void, List<Message>> {

            @Override
            protected List<Message> doInBackground(Void... voids) {
                List<Message> messages = AppDatabase.getCfctDatabase(getApplicationContext()).messageDAO().getMessages();
                recordsStored = messages;
                return messages;

            }

            @Override
            protected void onPostExecute(List<Message> messages) {
                super.onPostExecute(messages);
                Log.d("MessagesList", String.valueOf(messages.size()));
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
}
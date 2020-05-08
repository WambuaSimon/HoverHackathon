package com.hoverhackathon;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

import android.app.Activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hoverhackathon.DB.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private static final int TYPE_INCOMING_MESSAGE = 1;
    private ListView messageList;
    private MessageListAdapter messageListAdapter;
    private List<Message> recordsStored;
    private List<Message> listInboxMessages;

    private CustomHandler customHandler;
    int REQUEST_CODE_ASK_PERMISSIONS = 100;
    Button proceed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            initViews();

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        populateMessageList();
    }

    private void initViews() {
        customHandler = new CustomHandler(this);

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
                for (int m = 0; m < recordsStored.size(); m++) {
                    if (recordsStored.get(m).isChecked) {
                        String number = recordsStored.get(m).messageNumber;
                        Log.d("recordsStored", number);
                    }

                }
                /*TODO: after unsubscribing, update ROOM DB with status ==1, then remove item from list view */

            }
        });

    }




    public void populateMessageList() {
        fetchInboxMessages();

        /*TODO: only show addresses saved in ROOM DB*/
        /*TODO: store numbers/address and status to ROOM*/
        /*TODO: check if such numbers have been unsubscribed by checking status flag in ROOM*/
        /*TODO: if status==1, it's been here before so don't store, else, store with status==0*/
        /*TODO: only fetch with status==0, put in array list and pass to list adapter*/

        messageListAdapter = new MessageListAdapter(this,
                R.layout.message_list_item, recordsStored);
        messageList.setAdapter(messageListAdapter);

    }


    private void fetchInboxMessages() {
        if (listInboxMessages == null) {

            startThread();
        } else {
            // messageType = TYPE_INCOMING_MESSAGE;
            recordsStored = listInboxMessages;
            messageListAdapter.setArrayList(recordsStored);
        }
    }

    public class FetchMessageThread extends Thread {
        public int tag = -1;
        public FetchMessageThread(int tag) {
            this.tag = tag;
        }

        @Override
        public void run() {

            recordsStored = fetchInboxSms(TYPE_INCOMING_MESSAGE);
            listInboxMessages = recordsStored;
            customHandler.sendEmptyMessage(0);

        }

    }

    public List<Message> fetchInboxSms(int type) {
        List<Message> smsInbox = new ArrayList<>();

        Uri uriSms = Uri.parse("content://sms");

        Cursor cursor = this.getContentResolver()
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
                            !message.messageNumber.equalsIgnoreCase("google")&&
                            message.messageNumber.length() < 10) {

                        smsInbox.add(message);

                    }

                } while (cursor.moveToNext());
            }
        }

        return smsInbox;

    }

    private FetchMessageThread fetchMessageThread;

    private int currentCount = 0;

    public synchronized void startThread() {

        if (fetchMessageThread == null) {
            fetchMessageThread = new FetchMessageThread(currentCount);
            fetchMessageThread.start();
        }
    }

    public synchronized void stopThread() {
        if (fetchMessageThread != null) {
            Log.i("Cancel thread", "stop thread");
            FetchMessageThread moribund = fetchMessageThread;
            currentCount = fetchMessageThread.tag == 0 ? 1 : 0;
            fetchMessageThread = null;
            moribund.interrupt();
        }
    }

    static class CustomHandler extends Handler {
        private final WeakReference<MainActivity> activityHolder;

        CustomHandler(MainActivity inboxListActivity) {
            activityHolder = new WeakReference<MainActivity>(inboxListActivity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {

            MainActivity inboxListActivity = activityHolder.get();
            if (inboxListActivity.fetchMessageThread != null
                    && inboxListActivity.currentCount == inboxListActivity.fetchMessageThread.tag) {
                Log.i("received result", "received result");
                inboxListActivity.fetchMessageThread = null;

                inboxListActivity.messageListAdapter
                        .setArrayList(inboxListActivity.recordsStored);

            }
        }
    }

    private OnCancelListener dialogCancelListener = new OnCancelListener() {

        @Override
        public void onCancel(DialogInterface dialog) {
            stopThread();
        }

    };

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
package com.hoverhackathon.ui.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoverhackathon.MainActivity;
import com.hoverhackathon.R;
import com.hoverhackathon.adapter.PromotionalMessagesAdapter;
import com.hoverhackathon.model.PromotionalMessagesModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ActivityPromotionalMessages extends AppCompatActivity   implements PromotionalMessagesAdapter.PromotionalMessagesAdapterListener {
    private RecyclerView recyclerView;
    private List<PromotionalMessagesModel> messagesModelList ;
    private List<PromotionalMessagesModel> listInboxMessages ;
    private PromotionalMessagesAdapter adapter;
    private SearchView searchView;
    private CustomHandler customHandler;
    int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private static final int TYPE_INCOMING_MESSAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotional_messages);

        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            initViews();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

    }
    void initViews(){
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customHandler = new CustomHandler(this);
        recyclerView = findViewById(R.id.recyclerview);
        messagesModelList = new ArrayList<>();
        listInboxMessages = new ArrayList<>();

        populateMessageList();
    }

    void populateMessageList(){
        fetchInboxMessages();

        adapter = new PromotionalMessagesAdapter(this, messagesModelList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onMessageModelSelected(PromotionalMessagesModel message) {
        messagesModelList.add(message);
    }

    @Override
    public void onMessageModelUnSelected(PromotionalMessagesModel message) {
        messagesModelList.remove(message);
    }

  private void fetchInboxMessages() {
        if (listInboxMessages == null) {
//            showProgressDialog("Fetching Inbox Messages...");
            startThread();
        } else {
            // messageType = TYPE_INCOMING_MESSAGE;
            messagesModelList= listInboxMessages;
            adapter = new PromotionalMessagesAdapter(this, messagesModelList, this);
        }
    }

    public class FetchMessageThread extends Thread {

        public int tag = -1;

        public FetchMessageThread(int tag) {
            this.tag = tag;
        }

        @Override
        public void run() {

            messagesModelList = fetchInboxSms(TYPE_INCOMING_MESSAGE);
            messagesModelList = listInboxMessages;
            customHandler.sendEmptyMessage(0);

        }

    }
    @Override
    public void onResume() {
        super.onResume();
        populateMessageList();
    }

    public ArrayList<PromotionalMessagesModel> fetchInboxSms(int type) {
        ArrayList<PromotionalMessagesModel> smsInbox = new ArrayList<>();

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

                    PromotionalMessagesModel message = new PromotionalMessagesModel();
                    String messageItem;
                    messageItem  = cursor.getString(cursor.getColumnIndex("address"));
                    message.setMessageAddress(messageItem);
                    Log.d("messageItem","messageItem");
                    Toast.makeText(this, ""+messageItem, Toast.LENGTH_SHORT).show();

                    if (!messageItem.equalsIgnoreCase("Safaricom") &&
                            !messageItem.equalsIgnoreCase("Mshwari") &&
                            !messageItem.equalsIgnoreCase("KPLC") &&
                            !messageItem.equalsIgnoreCase("Verify") &&
                            !messageItem.equalsIgnoreCase("Mpesa") &&
                            !messageItem.equalsIgnoreCase("iandmbank") &&
                            !messageItem.equalsIgnoreCase("safhome") &&
                            !messageItem.equalsIgnoreCase("authmsg") &&
                            !messageItem.equalsIgnoreCase("google") &&
                            messageItem.length() < 10) {

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
            ActivityPromotionalMessages.FetchMessageThread moribund = fetchMessageThread;
            currentCount = fetchMessageThread.tag == 0 ? 1 : 0;
            fetchMessageThread = null;
            moribund.interrupt();
        }
    }

    static class CustomHandler extends Handler {
        private final WeakReference<ActivityPromotionalMessages> activityHolder;

        CustomHandler(ActivityPromotionalMessages inboxListActivity) {
            activityHolder = new WeakReference<>(inboxListActivity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {

            ActivityPromotionalMessages inboxListActivity = activityHolder.get();
            if (inboxListActivity.fetchMessageThread != null
                    && inboxListActivity.currentCount == inboxListActivity.fetchMessageThread.tag) {
                Log.i("received result", "received result");
                inboxListActivity.fetchMessageThread = null;

                inboxListActivity.adapter
                        .setArrayList(inboxListActivity.messagesModelList);

            }
        }
    }

    private DialogInterface.OnCancelListener dialogCancelListener = new DialogInterface.OnCancelListener() {

        @Override
        public void onCancel(DialogInterface dialog) {
            stopThread();
        }

    };

}

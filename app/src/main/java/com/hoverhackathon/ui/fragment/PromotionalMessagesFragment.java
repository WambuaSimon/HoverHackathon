package com.hoverhackathon.ui.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoverhackathon.MainActivity;
import com.hoverhackathon.R;
import com.hoverhackathon.adapter.PromotionalMessagesAdapter;
import com.hoverhackathon.model.PromotionalMessagesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromotionalMessagesFragment extends Fragment implements PromotionalMessagesAdapter.PromotionalMessagesAdapterListener {
    private RecyclerView recyclerView;
    private List<PromotionalMessagesModel> messagesModelList = new ArrayList<>();
    private List<PromotionalMessagesModel> messagesModelListStored = new ArrayList<>();
    private PromotionalMessagesAdapter adapter;
    private SearchView searchView;
    View view;
    int REQUEST_CODE_ASK_PERMISSIONS = 100;
    public PromotionalMessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_promotional_messages, container, false);

        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            initViews();

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }


        addData();

        return view;
    }

    void initViews(){
        recyclerView = view.findViewById(R.id.recyclerview);
        messagesModelList = new ArrayList<>();
        messagesModelListStored = new ArrayList<>();
        adapter = new PromotionalMessagesAdapter(getActivity(), messagesModelList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
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

    void addData() {
        PromotionalMessagesModel model = new PromotionalMessagesModel("21021");
        messagesModelList.add(model);
        PromotionalMessagesModel model2 = new PromotionalMessagesModel("98765");
        messagesModelList.add(model2);
        PromotionalMessagesModel model3 = new PromotionalMessagesModel("MinOfHealth");
        messagesModelList.add(model3);
        PromotionalMessagesModel model4 = new PromotionalMessagesModel("ODI Bets");
        messagesModelList.add(model4);

        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
    }
}

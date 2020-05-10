package com.hoverhackathon.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;
import com.hoverhackathon.DownloadListener;
import com.hoverhackathon.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class GovernmentServicesFragment extends Fragment {
    CardView ecitizen, nhif, nssf, helb;
    View view;
    String accountnumber, amount;

    public GovernmentServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_government_services, container, false);
        init();

        return view;
    }

    void init() {
        ecitizen = view.findViewById(R.id.ecitizen);
        nhif = view.findViewById(R.id.nhif);
        nssf = view.findViewById(R.id.nssf);
        helb = view.findViewById(R.id.helb);
        //initialize hover
        DownloadListener xv = new DownloadListener();
        Hover.initialize(Objects.requireNonNull(getContext()));
        Hover.updateActionConfigs(xv, Objects.requireNonNull(getContext()));

        ecitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("eCitizen", "Enter Account Number");
            }
        });
        helb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("Helb", "Enter ID Number");
            }
        });
        nhif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("NHIF", "Enter ID/Member Number");
            }
        });
        nssf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("NSSF", "Enter NSSF Number");
            }
        });
    }

    void payDialog(final String billName, String hint) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.payment_dialog, null);
        dialogBuilder.setView(dialogView);


        final EditText account_no, number;
        Button pay, cancel;

        TextView bill_name = dialogView.findViewById(R.id.bill_name);
        bill_name.setText(billName);
        account_no = dialogView.findViewById(R.id.account_no);
        account_no.setHint(hint);
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
                accountnumber = account_no.getText().toString();
                amount = number.getText().toString();
                /*TODO: HOVER STUFF GOES HERE*/
                //ecitizen 205d8fea

                // nhif e07cc54a
                //nssf 7751435f
                //helb 49a6b45c
                switch (billName) {
                    case "eCitizen":
                        Intent kplcprepaid = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("205d8fea")
                                .extra("paybill","206206")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(kplcprepaid, 0);
                        break;
                    case "Helb":
                        Intent dstvpayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("49a6b45c")
                                .extra("paybill","200800")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(dstvpayment, 0);
                        break;
                    case "NHIF":
                        Intent zukupayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("e07cc54a")
                                .extra("paybill","200222")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(zukupayment, 0);
                        break;
                    case "NSSF":
                        Intent gotvpayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("7751435f")
                                .extra("paybill","333300")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(gotvpayment, 0);
                        break;


                }
//                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }
}

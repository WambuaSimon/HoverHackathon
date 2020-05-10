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
public class BanksSaccosFragment extends Fragment {
    View view;
    CardView im, kcb, equity, stan, sheria, stima;
    String accountnumber, amount;
    public BanksSaccosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_banks_saccos, container, false);
        init();
        return view;
    }

    void init() {
        im = view.findViewById(R.id.im);
        kcb = view.findViewById(R.id.kcb);
        equity = view.findViewById(R.id.equity);
        stan = view.findViewById(R.id.stan);
        sheria = view.findViewById(R.id.sheria);
        stima = view.findViewById(R.id.stima);

        //initialize hover
        DownloadListener xv = new DownloadListener();
        Hover.initialize(Objects.requireNonNull(getContext()));
        Hover.updateActionConfigs(xv, Objects.requireNonNull(getContext()));

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("I&M Bank");
            }
        });
        kcb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("KCB Bank");
            }
        });
        equity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("Equity Bank");
            }
        });
        stan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("Standard Chartered Bank");
            }
        });
        sheria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("Sheria Sacco");
            }
        });
        stima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("Stima Sacco");
            }
        });
    }

    void payDialog(final String billName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.payment_dialog, null);
        dialogBuilder.setView(dialogView);


        final EditText account_no, number;
        Button pay, cancel;

        TextView bill_name = dialogView.findViewById(R.id.bill_name);
        bill_name.setText(billName);

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

                if (account_no.getText().toString().isEmpty()) {
                    account_no.setError("Enter Account Number");
                } else {
                    account_no.setError(null);
                }
                if (number.getText().toString().isEmpty()) {
                    number.setError("Enter Amount");
                } else {
                    number.setError(null);
                }


                accountnumber = account_no.getText().toString();
                amount = number.getText().toString();
                /*TODO: HOVER STUFF GOES HERE*/
                switch (billName) {
                    case "I&M Bank":
                        Intent iandmbank = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("741e5e8f")
                                .extra("paybill", "542542")
                                .extra("accountnumber", accountnumber)
                                .extra("amount", amount)
                                .buildIntent();
                        startActivityForResult(iandmbank, 0);
                        break;
                    case "KCB Bank":
                        Intent kcbbank = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("bbe7034f")
                                .extra("paybill", "522522")
                                .extra("accountnumber", accountnumber)
                                .extra("amount", amount)
                                .buildIntent();
                        startActivityForResult(kcbbank, 0);
                        break;
                    case "Equity Bank":
                        Intent equitybank = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("86238c25")
                                .extra("paybill", "247247")
                                .extra("accountnumber", accountnumber)
                                .extra("amount", amount)
                                .buildIntent();
                        startActivityForResult(equitybank, 0);
                        break;
                    case "Standard Chartered Bank":
                        Intent gotvpayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("21cd7bf8")
                                .extra("paybill", "329329")
                                .extra("accountnumber", accountnumber)
                                .extra("amount", amount)
                                .buildIntent();
                        startActivityForResult(gotvpayment, 0);
                        break;
                    case "Sheria Sacco":
                        Intent starttimespayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("def448ba")
                                .extra("paybill", "964700")
                                .extra("accountnumber", accountnumber)
                                .extra("amount", amount)
                                .buildIntent();
                        startActivityForResult(starttimespayment, 0);
                        break;
                    case "Stima Sacco":
                        Intent rentpayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("6198c375")
                                .extra("paybill", "523500")
                                .extra("accountnumber", accountnumber)
                                .extra("amount", amount)
                                .buildIntent();
                        startActivityForResult(rentpayment, 0);
                        break;

                }
//                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }
}

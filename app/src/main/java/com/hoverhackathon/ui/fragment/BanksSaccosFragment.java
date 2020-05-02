package com.hoverhackathon.ui.fragment;

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

import com.hoverhackathon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BanksSaccosFragment extends Fragment {
    View view;
    CardView im, kcb, equity, stan;

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
    }

    void payDialog(String billName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.payment_dialog, null);
        dialogBuilder.setView(dialogView);


        final EditText paybill, account_no, number;
        Button pay, cancel;

        TextView bill_name = dialogView.findViewById(R.id.bill_name);
        bill_name.setText(billName);
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

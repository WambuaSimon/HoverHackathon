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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;
import com.hoverhackathon.DownloadListener;
import com.hoverhackathon.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UtilityFragment extends Fragment {
    CardView kplc, dstv, zuku, gotv, star, rent, bonga;
    LinearLayout sgr, buupass;
    View view;
    String accountnumber, amount;
    public UtilityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_utility, container, false);
        init();
        return view;
    }

    void init() {
        kplc = view.findViewById(R.id.kplc);
        kplc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("KPLC Prepaid");
            }
        });
        //initialize hover
        DownloadListener xv = new DownloadListener();
        Hover.initialize(Objects.requireNonNull(getContext()));
        Hover.updateActionConfigs(xv, Objects.requireNonNull(getContext()));

        dstv = view.findViewById(R.id.dstv);
        dstv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("DSTV");
            }
        });

        zuku = view.findViewById(R.id.zuku);
        zuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("Zuku");
            }
        });

        gotv = view.findViewById(R.id.gotv);
        gotv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("GOTV");
            }
        });

        star = view.findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("Star Times");
            }
        });

        rent = view.findViewById(R.id.rent);
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("Rent");
            }
        });

        sgr = view.findViewById(R.id.sgr);
        sgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("Madaraka Express");
            }
        });

        buupass = view.findViewById(R.id.buupass);
        buupass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog("BuuPass");
            }
        });

        bonga = view.findViewById(R.id.bonga);
        bonga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Proceed with pay with bonga", Toast.LENGTH_SHORT).show();
                Intent payWithBonga = new HoverParameters.Builder(getContext())
                        //.extra("", "")
                        .request("def448ba")
                        .buildIntent();
                startActivityForResult(payWithBonga, 0);
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
                    account_no.setError("Enter account no.");
                } else {
                    account_no.setError(null);
                }
                if (number.getText().toString().isEmpty()) {
                    number.setError("Enter amount.");
                } else {
                    number.setError(null);
                }
                accountnumber = account_no.getText().toString();
                amount = number.getText().toString();
                /*TODO: HOVER STUFF GOES HERE*/
                switch (billName) {
                    case "KPLC Prepaid":
                        Intent kplcprepaid = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("afdccceb")
                                .extra("paybill","888880")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(kplcprepaid, 0);
                        alertDialog.dismiss();
                        break;
                    case "DSTV":
                        Intent dstvpayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("9a41e022")
                                .extra("paybill","444900")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(dstvpayment, 0);
                        alertDialog.dismiss();
                        break;
                    case "Zuku":
                        Intent zukupayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("a725648a")
                                .extra("paybill","320323")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(zukupayment, 0);
                        alertDialog.dismiss();
                        break;
                    case "GOTV":
                        Intent gotvpayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("52cf4887")
                                .extra("paybill","423655")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(gotvpayment, 0);
                        alertDialog.dismiss();
                        break;
                    case "Star Times":
                        Intent starttimespayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("def448ba")
                                .extra("paybill","585858")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(starttimespayment, 0);
                        alertDialog.dismiss();
                        break;
                    case "Rent":
                        Intent rentpayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("4310a43b")
                                .extra("paybill","247247")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(rentpayment, 0);
                        alertDialog.dismiss();
                        break;
                    case "Madaraka Express":
                        Intent madarakaexpresspayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("4c65b52c")
                                .extra("paybill","809888 ")
                                .extra("accountnumber",accountnumber)
                                .extra("amount",amount)
                                .buildIntent();
                        startActivityForResult(madarakaexpresspayment, 0);
                        alertDialog.dismiss();
                       break;
                    case"BuuPass":
                        Intent buupasspayment = new HoverParameters.Builder(getContext())
                                //.extra("", "")
                                .request("112c155a")
                                .buildIntent();
                        startActivityForResult(buupasspayment, 0);
                        alertDialog.dismiss();
                        break;

                }
//                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }
}

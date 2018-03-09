package com.yougen.anticafemanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

/**
 * Created by thymomenosgata on 03.11.17.
 */

public class DialogFragment extends android.support.v4.app.DialogFragment{


    public interface onClickButtonOk{
        void onClickOk(ListV listV);
    }

    onClickButtonOk listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (onClickButtonOk) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnClickButtonOk");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainFragment mainFragment = new MainFragment();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view, null);
        builder.setView(view);
        builder.setTitle(R.string.addNewPaymants);


        final TextInputLayout numTable = (TextInputLayout)view.findViewById(R.id.inputNumberTable);
        final TextInputLayout countPeopls = (TextInputLayout)view.findViewById(R.id.inputCountPeoples);
        final EditText eText = numTable.getEditText();
        final EditText cText = countPeopls.getEditText();
        final Switch switchH = (Switch)view.findViewById(R.id.switchHard);
        final Switch switchN = (Switch)view.findViewById(R.id.switchNormal);
        final Switch switchL = (Switch)view.findViewById(R.id.switchLigth);
        final Switch switchV = (Switch)view.findViewById(R.id.switchVIP);
        final Switch switchNL = (Switch)view.findViewById(R.id.switchNonLimited);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean hhookah, vvip, nnon;

        hhookah = settings.getBoolean("Hookah", false);
        vvip = settings.getBoolean("VIP", false);
        nnon = settings.getBoolean("Stop", false);



        numTable.setHint(getResources().getString(R.string.hintTable));
        countPeopls.setHint(getResources().getString(R.string.hintPeoples));

        final Switch switchDop = (Switch)view.findViewById(R.id.switchDOP);
        final Switch switchVIP = (Switch)view.findViewById(R.id.switchVIP);
        final Switch switchNONL = (Switch)view.findViewById(R.id.switchNonLimited);

        switchDop.setText(R.string.hookah);
        if(hhookah == false){
            switchDop.setVisibility(View.INVISIBLE);
        }
        else{
            switchDop.setVisibility(View.VISIBLE);
        }

        if(vvip == false){
            switchVIP.setVisibility(View.INVISIBLE);
        }
        else{
            switchVIP.setVisibility(View.VISIBLE);
        }

        if(nnon == false){
            switchNONL.setVisibility(View.INVISIBLE);
        }
        else{
            switchNONL.setVisibility(View.VISIBLE);
        }

        final LinearLayout linlay = (LinearLayout)view.findViewById(R.id.switchGroup);
        if(switchDop.isChecked())
            linlay.setVisibility(View.VISIBLE);
        else
            linlay.setVisibility(View.INVISIBLE);

        switchDop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchDop.isChecked())
                    linlay.setVisibility(View.VISIBLE);
                else
                    linlay.setVisibility(View.INVISIBLE);
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                listener.onClickOk(new ListV(Integer.valueOf(eText.getText().toString()),
                        Integer.valueOf(cText.getText().toString()),
                        switchDop.isChecked(), switchH.isChecked(),
                        switchN.isChecked(),switchL.isChecked(),
                        switchV.isChecked(),switchNL.isChecked()));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                positive.setEnabled(false);
                if(eText.length() == 0){
                    positive.setEnabled(false);
                    numTable.setError(getResources().getString(R.string.errorTable));
                }
                if (cText.length() == 0) {
                        positive.setEnabled(false);
                        countPeopls.setError(getResources().getString(R.string.errorPeople));
                }

                eText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        if(s.length() == 0){
                            positive.setEnabled(false);
                            numTable.setError(getResources().getString(R.string.errorTable));
                        }
                        else {
                            if(cText.length() != 0) {
                                positive.setEnabled(true);
                            }
                            numTable.setErrorEnabled(false);
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                cText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        if(s.length() == 0){
                            positive.setEnabled(false);
                            countPeopls.setError(getResources().getString(R.string.errorPeople));
                        }
                        else {
                            if(eText.length() != 0) {
                                positive.setEnabled(true);
                            }
                            countPeopls.setErrorEnabled(false);

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

            }
        });


        return alertDialog;
    }
}

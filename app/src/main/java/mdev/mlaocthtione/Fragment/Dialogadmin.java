package mdev.mlaocthtione.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.ModelBus.OnCheck;
import mdev.mlaocthtione.ModelBus.OnclickPrinter;
import mdev.mlaocthtione.ModelBus.Onreset;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.bus.BusProvider;
import mdev.mlaocthtione.bus.ModelBus;
import mdev.mlaocthtione.utils.Utils;

/**
 * Created by Lenovo on 26-12-2017.
 */

public class Dialogadmin extends Fragment {

    public static Dialogadmin newInstance(){
        Dialogadmin dialogadmin = new Dialogadmin();
        return dialogadmin;
    }

    private TextView btn_enter_admin;
    private EditText edit_admin;
    private AllCommand allCommand;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        allCommand = new AllCommand();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_admin,container,false);

        btn_enter_admin = view.findViewById(R.id.btn_enter_admin);
        edit_admin = view.findViewById(R.id.edit_admin);
        edit_admin.setKeyListener(null);
        edit_admin.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});



        btn_enter_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (allCommand.GetStringShare(getContext(),allCommand.moSaveAdmin,"").equals(edit_admin.getText().toString())){
                    final ModelBus modelBus = new ModelBus();

                    if (allCommand.GetStringShare(getContext(),allCommand.moStatuspage,"").equals("p1")){
                        modelBus.setPage(Utils.KEY_ADD_PAGE_PRINTER);
                        modelBus.setMsg(Utils.NAME_ADD_PAGE_PRINTER);
                        BusProvider.getInstance().post(modelBus);
                        edit_admin.setText("");
                    }else if (allCommand.GetStringShare(getContext(),allCommand.moStatuspage,"").equals("p2")){

                        loginOut();
                    }

                }else {

                    allCommand.ShowAertDialog_OK("รหัสไม่ถูกต้อง",getContext());
                    edit_admin.setText("");
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edit_admin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                OnCheck onCheck = new OnCheck();
                if (edit_admin.getText().length()>0){
                    onCheck.setTAG_KEY("0");
                    BusProvider.getInstance().post(onCheck);
                }else {
                    onCheck.setTAG_KEY("1");
                    BusProvider.getInstance().post(onCheck);
                }

            }
        });
    }

    @Subscribe
    public void onClickBus(OnclickPrinter onclickPrinter){

        if (!onclickPrinter.getTAG_KEY().equals("")){

            switch (onclickPrinter.getTAG_KEY()) {
                case "edit":

                    int length = edit_admin.getText().length();
                    if (length > 0) {
                        edit_admin.getText().delete(length - 1, length);
                    }

                    break;
                case "back":

                    ModelBus modelBus = new ModelBus();
                    modelBus.setPage(Utils.KEY_ADD_PAGE_TANG_LOT_FAST);
                    modelBus.setMsg(Utils.NAME_ADD_PAGE_TANG_LOT_FAST);
                    BusProvider.getInstance().post(modelBus);

                    break;
                default:
                    setNumber(onclickPrinter.getTAG_KEY());
                    break;
            }

        }

    }
    private void setNumber(String number){
        if (getActivity().getCurrentFocus().getId() == edit_admin.getId()) {
            edit_admin.setText(edit_admin.getText() + number);
        }
    }

    public void loginOut(){
        int sizeMsgDialog = Integer.parseInt(getResources().getString(R.string.size_dialog_ok));
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TextView myMsg = new TextView(getActivity());
        myMsg.setText("\n" + getResources().getString(R.string.title_logout) + "\n");
        myMsg.setTextSize(sizeMsgDialog);
        myMsg.setGravity(Gravity.CENTER);
        myMsg.setTypeface(null, Typeface.BOLD);
        builder.setView(myMsg);
        builder.setCancelable(true);
        builder.setPositiveButton(getResources().getString(R.string.title_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                allCommand.SaveStringShare(getContext(),allCommand.moSavePass,"");

                ModelBus modelBus = new ModelBus();
                modelBus.setPage(Utils.KEY_ADD_PAGE_LOGIN);
                modelBus.setMsg(Utils.Name_ADD_PAGE_LOGIN);
                BusProvider.getInstance().post(modelBus);

                Onreset onreset = new Onreset();
                onreset.setTAG_KEY(true);
                BusProvider.getInstance().post(onreset);
                edit_admin.setText("");
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.title_no),null);
        final AlertDialog alertdialog = builder.create();
        alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                int iPadBT = Integer.parseInt(getResources().getString(R.string.size_padding_tb_dialog));
                int iSizeButton = Integer.parseInt(getResources().getString(R.string.size_dialog_button));
                Button btnOk = alertdialog.getButton(Dialog.BUTTON_POSITIVE);
                Button btnCancle= alertdialog.getButton(Dialog.BUTTON_NEGATIVE);
                btnOk.setTextSize(iSizeButton);
                btnOk.setPadding(1, iPadBT, 1, iPadBT);
                btnOk.setTypeface(null, Typeface.BOLD);
                btnCancle.setTextSize(iSizeButton);
                btnCancle.setPadding(1, iPadBT, 1, iPadBT);
                btnCancle.setTypeface(null, Typeface.BOLD);

            }
        });
        alertdialog.show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }
}

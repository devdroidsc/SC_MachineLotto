package mdev.mlaocthtione.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.wang.avi.AVLoadingIndicatorView;

import mdev.mlaocthtione.BuildConfig;
import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.Model.ModelStatusConnectPrinter;
import mdev.mlaocthtione.ModelBus.OnCheck;
import mdev.mlaocthtione.ModelBus.OnclickPrinter;
import mdev.mlaocthtione.ModelBus.Onclickmain;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.bus.BusProvider;
import mdev.mlaocthtione.utils.Utils;

/**
 * Created by Lenovo on 11-12-2017.
 */

public class Keyboardprint extends Fragment implements View.OnClickListener {
    private TextView bt_zero, bt_nine, bt_eight,
            bt_seven, bt_six, bt_file, bt_four, bt_three, bt_two, bt_one;
    private TextView btn_edit, btn_cancel, btn_enter, btn_print,bt_twozero;
    private LinearLayout lnContentPrinter,lnPrinterLot;
    private ImageView imgStatusPrinterLot;
    private AVLoadingIndicatorView avloadingPrinterLot;
    private boolean isCheckPrinter = false;
    private TextView bt_number_full;
    private boolean Checkpage = true;
    private TextView bt_language;
    private AllCommand allCommand;
    public Keyboardprint() {
    }

    public static Keyboardprint newInstance(){
        Keyboardprint keyboardmain = new Keyboardprint();
        return keyboardmain;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        allCommand = new AllCommand();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_keyboad_printer,container,false);
        itemView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int statusConBt = allCommand.getIntShare(getActivity(), Utils.SHARE_STATUS_CON_BT,0);
        if (statusConBt == 1){
            avloadingPrinterLot.setVisibility(View.INVISIBLE);
            imgStatusPrinterLot.setImageResource(R.drawable.ic_status_printer_conted);
        }else if (statusConBt == 0){
            avloadingPrinterLot.setVisibility(View.VISIBLE);
            imgStatusPrinterLot.setImageResource(R.drawable.ic_status_printer_conting);
        }else {
            avloadingPrinterLot.setVisibility(View.INVISIBLE);
            imgStatusPrinterLot.setImageResource(R.drawable.ic_status_printer_nocon);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isCheckPrinter = true;
        btn_edit.setText(allCommand.GetStringShare(getContext(),allCommand.text_returns,"Back"));
        bt_number_full.setText(allCommand.GetStringShare(getContext(),allCommand.text_numberFull,"Limit number"));
        btn_cancel.setText(allCommand.GetStringShare(getContext(),allCommand.text_cancel,"Cancel"));
        btn_print.setText(allCommand.GetStringShare(getContext(),allCommand.text_confirm,"Print"));
    }

    @Override
    public void onPause() {
        super.onPause();
        isCheckPrinter = false;
    }

    private void itemView(View view){
        bt_zero = view.findViewById(R.id.bt_zero);
        bt_nine = view.findViewById(R.id.bt_nine);
        bt_eight = view.findViewById(R.id.bt_eight);
        bt_seven = view.findViewById(R.id.bt_seven);
        bt_six = view.findViewById(R.id.bt_six);
        bt_file = view.findViewById(R.id.bt_file);
        bt_four = view.findViewById(R.id.bt_four);
        bt_three = view.findViewById(R.id.bt_three);
        bt_two = view.findViewById(R.id.bt_two);
        bt_one = view.findViewById(R.id.bt_one);
        btn_edit = view.findViewById(R.id.btn_edit_printer);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_print = view.findViewById(R.id.btn_print);
        bt_twozero = view.findViewById(R.id.bt_twozero);
        lnContentPrinter = view.findViewById(R.id.lnContentPrinter);
        avloadingPrinterLot = view.findViewById(R.id.avloadingPrinterLot);
        lnPrinterLot = view.findViewById(R.id.lnPrinterLot);
        imgStatusPrinterLot = view.findViewById(R.id.imgStatusPrinterLot);
        bt_number_full = view.findViewById(R.id.bt_number_full);
        bt_language = view.findViewById(R.id.bt_language);


        bt_zero.setOnClickListener(this);
        bt_nine.setOnClickListener(this);
        bt_eight.setOnClickListener(this);
        bt_seven.setOnClickListener(this);
        bt_six.setOnClickListener(this);
        bt_file.setOnClickListener(this);
        bt_four.setOnClickListener(this);
        bt_three.setOnClickListener(this);
        bt_two.setOnClickListener(this);
        bt_one.setOnClickListener(this);
        bt_twozero.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        lnPrinterLot.setOnClickListener(this);
        lnContentPrinter.setOnClickListener(this);
        bt_number_full.setOnClickListener(this);
        bt_language.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        Onclickmain onclickmain = new Onclickmain();
        OnclickPrinter onclickPrinter = new OnclickPrinter();
        switch (view.getId()){

           /* case R.id.lnContentPrinter:
                onclickmain.setTAG_KEY("Tang");
                BusProvider.getInstance().post(onclickmain);
                break;*/
            case R.id.bt_number_full:
                onclickmain.setTAG_KEY("NumberFull");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_language:
                onclickmain.setTAG_KEY("Language");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_one:
                onclickPrinter.setTAG_KEY("1");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.bt_two:
                onclickPrinter.setTAG_KEY("2");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.bt_three:
                onclickPrinter.setTAG_KEY("3");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.bt_four:
                onclickPrinter.setTAG_KEY("4");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.bt_file:
                onclickPrinter.setTAG_KEY("5");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.bt_six:
                onclickPrinter.setTAG_KEY("6");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.bt_seven:
                onclickPrinter.setTAG_KEY("7");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.bt_eight:
                onclickPrinter.setTAG_KEY("8");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.bt_nine:
                onclickPrinter.setTAG_KEY("9");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.bt_zero:
                onclickPrinter.setTAG_KEY("0");
                BusProvider.getInstance().post(onclickPrinter);
                break;
            case R.id.btn_edit_printer:
                if (btn_edit.getText().equals(allCommand.GetStringShare(getContext(),allCommand.text_edit,"Delete"))){
                    onclickPrinter.setTAG_KEY("edit");
                    BusProvider.getInstance().post(onclickPrinter);
                }else {
                    onclickmain.setTAG_KEY("back");
                    BusProvider.getInstance().post(onclickmain);
                }

                break;
        }

    }

    @Subscribe
    public void onCheckEdit(OnCheck state){
       if (!state.getTAG_KEY().equals("")){

           if (state.getTAG_KEY().equals("1")){
               btn_edit.setText(allCommand.GetStringShare(getContext(),allCommand.text_returns,"Back"));
               btn_edit.setBackgroundResource(R.drawable.bg_number_return);
           }else {
               btn_edit.setText(allCommand.GetStringShare(getContext(),allCommand.text_edit,"Delete"));
               btn_edit.setBackgroundResource(R.drawable.bg_number_edit);
           }
       }


    }
    @Subscribe
    public void onBusStatusPrinter(ModelStatusConnectPrinter conPrinter){
        onShowLogCat("Event Bus","Connect Printer " + conPrinter.getTextStatus());
        if (isCheckPrinter && conPrinter != null){
            if (conPrinter.getStatus() == 1){
                avloadingPrinterLot.setVisibility(View.INVISIBLE);
                imgStatusPrinterLot.setImageResource(R.drawable.ic_status_printer_conted);
            }else if (conPrinter.getStatus() == 0){
                avloadingPrinterLot.setVisibility(View.VISIBLE);
                imgStatusPrinterLot.setImageResource(R.drawable.ic_status_printer_conting);
            }else {
                avloadingPrinterLot.setVisibility(View.INVISIBLE);
                imgStatusPrinterLot.setImageResource(R.drawable.ic_status_printer_nocon);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }
    public void onShowLogCat(String tag, String msg){
        if (BuildConfig.DEBUG) {
            Log.e("***KeyboardPrint***",tag +" ==> "+ msg);
        }
    }
}

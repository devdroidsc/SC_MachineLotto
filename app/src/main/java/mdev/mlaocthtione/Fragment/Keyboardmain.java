package mdev.mlaocthtione.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import mdev.mlaocthtione.ModelBus.Onclicklogin;
import mdev.mlaocthtione.ModelBus.Onclickmain;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.bus.BusProvider;

/**
 * Created by Lenovo on 11-12-2017.
 */

public class Keyboardmain extends Fragment implements View.OnClickListener {
    private TextView bt_zero, bt_nine, bt_eight,
            bt_seven, bt_six, bt_file, bt_four, bt_three, bt_two, bt_one;
    private TextView btn_edit, btn_cancel, btn_enter, btn_print,bt_twozero;
    private LinearLayout lnContentPrinter;

    private boolean Checkpage = true;
    public Keyboardmain() {
    }

    public static Keyboardmain newInstance(){
        Keyboardmain keyboardmain = new Keyboardmain();
        return keyboardmain;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_keyboad,container,false);
        itemView(view);
        return view;
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
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_print = view.findViewById(R.id.btn_print);
        bt_twozero = view.findViewById(R.id.bt_twozero);
        lnContentPrinter = view.findViewById(R.id.lnContentPrinter);


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
        btn_print.setOnClickListener(this);
        lnContentPrinter.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        Onclickmain onclickmain = new Onclickmain();
        switch (view.getId()){

            case R.id.btn_edit:
                onclickmain.setTAG_KEY("edit");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.btn_cancel:
                onclickmain.setTAG_KEY("Clear");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.btn_print:
                onclickmain.setTAG_KEY("Savelot");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.lnContentPrinter:
                if (Checkpage){
                    Checkpage = false;
                    onclickmain.setTAG_KEY("SettingPrinter");
                    BusProvider.getInstance().post(onclickmain);
                }else {
                    Checkpage = true;
                    onclickmain.setTAG_KEY("Tang");
                    BusProvider.getInstance().post(onclickmain);
                }

                break;
            case R.id.bt_one:
                onclickmain.setTAG_KEY("1");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_two:
                onclickmain.setTAG_KEY("2");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_three:
                onclickmain.setTAG_KEY("3");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_four:
                onclickmain.setTAG_KEY("4");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_file:
                onclickmain.setTAG_KEY("5");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_six:
                onclickmain.setTAG_KEY("6");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_seven:
                onclickmain.setTAG_KEY("7");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_eight:
                onclickmain.setTAG_KEY("8");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_nine:
                onclickmain.setTAG_KEY("9");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_zero:
                onclickmain.setTAG_KEY("0");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.bt_twozero:
                onclickmain.setTAG_KEY("00");
                BusProvider.getInstance().post(onclickmain);
                break;

        }

    }
}

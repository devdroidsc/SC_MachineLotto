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

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.ModelBus.OnCloseSavelot;
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
    private LinearLayout lnContentPrinter,bt_logout;
    private ImageView img_priterandtang;
    private TextView bt_number_full;
    private String strCloseBig, strCloseSmall, strPhon;
    private AllCommand allCommand;
    private Date D_CloseBig, D_Phon;
    private LinearLayout liner_keyboad_main,close_tang_keyboad;
    private TextView bt_language;

    private boolean Checkpage = true;
    public Keyboardmain() {
    }

    public static Keyboardmain newInstance(){
        Keyboardmain keyboardmain = new Keyboardmain();
        return keyboardmain;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCommand = new AllCommand();
        BusProvider.getInstance().register(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Date currentTime = Calendar.getInstance().getTime();
        strCloseBig = allCommand.SetDatestamp(allCommand.GetStringShare(getContext(), allCommand.moCloseBig, ""));
        strPhon = allCommand.SetDateFoment(currentTime);

        SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");

        try {
            D_CloseBig = dates.parse(strCloseBig);
            D_Phon = dates.parse(strPhon);

            if (D_CloseBig.getTime() > D_Phon.getTime()) {
                close_tang_keyboad.setVisibility(View.GONE);
            } else {
                close_tang_keyboad.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_keyboad,container,false);
        itemView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        btn_edit.setText(allCommand.GetStringShare(getContext(),allCommand.text_edit,"Delete"));
        bt_number_full.setText(allCommand.GetStringShare(getContext(),allCommand.text_numberFull,"Limit number"));
        btn_cancel.setText(allCommand.GetStringShare(getContext(),allCommand.text_cancel,"Cancel"));
        btn_print.setText(allCommand.GetStringShare(getContext(),allCommand.text_confirm,"Print"));


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
        //img_priterandtang = view.findViewById(R.id.img_priterandtang);
        //bt_logout = view.findViewById(R.id.bt_logout);
        bt_number_full = view.findViewById(R.id.bt_number_full);
        liner_keyboad_main = view.findViewById(R.id.liner_keyboad_main);
        close_tang_keyboad = view.findViewById(R.id.close_tang_keyboad);
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
        btn_print.setOnClickListener(this);
        lnContentPrinter.setOnClickListener(this);
        bt_number_full.setOnClickListener(this);
        close_tang_keyboad.setOnClickListener(this);
        bt_language.setOnClickListener(this);
//        bt_logout.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        Onclickmain onclickmain = new Onclickmain();

        switch (view.getId()){

            case R.id.btn_edit:
                if (btn_edit.getText().equals(allCommand.GetStringShare(getContext(),allCommand.text_edit,"Delete"))){
                    onclickmain.setTAG_KEY("edit");
                    BusProvider.getInstance().post(onclickmain);
                }else {
                    onclickmain.setTAG_KEY("Close");
                    BusProvider.getInstance().post(onclickmain);
                }


                break;
            case R.id.btn_cancel:
                onclickmain.setTAG_KEY("Clear");
                BusProvider.getInstance().post(onclickmain);
                break;
            case R.id.btn_print:
                onclickmain.setTAG_KEY("Savelot");
                BusProvider.getInstance().post(onclickmain);

                break;
            /*case R.id.lnContentPrinter:
                onclickmain.setTAG_KEY("SettingPrinter");
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
           /* case R.id.bt_logout:
                onclickmain.setTAG_KEY("Loginout");
                BusProvider.getInstance().post(onclickmain);
                break;*/
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void OnCloseSave_lot(OnCloseSavelot onCloseSavelot){

        if (!onCloseSavelot.getTAG_KEY().equals("")){
            if (onCloseSavelot.getTAG_KEY().equals("1")){
                btn_edit.setText(allCommand.GetStringShare(getContext(),allCommand.text_returns,"Back"));
                btn_edit.setBackgroundResource(R.drawable.bg_number_return);
            }else {
                btn_edit.setText(allCommand.GetStringShare(getContext(),allCommand.text_edit,"Delete"));
                btn_edit.setBackgroundResource(R.drawable.bg_number_edit);
            }
        }

    }
}

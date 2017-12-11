package mdev.mlaocthtione.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mdev.mlaocthtione.ModelBus.Onclicklogin;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.bus.BusProvider;

/**
 * Created by Lenovo on 11-12-2017.
 */

public class Keyboardlogin extends Fragment implements View.OnClickListener {

    private TextView bt_zero, bt_nine, bt_eight,
            bt_seven, bt_six, bt_file, bt_four, bt_three, bt_two, bt_one;
    private TextView btn_edit,bt_Left,bg_Login;

    public Keyboardlogin() {
    }

    public static Keyboardlogin newInstance(){
        Keyboardlogin keyboardlogin = new Keyboardlogin();
        return  keyboardlogin;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_keyboad_login,container,false);
        itemView(view);
        return view;
    }

    private void itemView(View itemview){
        bt_zero = itemview.findViewById(R.id.bt_zero);
        bt_nine = itemview.findViewById(R.id.bt_nine);
        bt_eight = itemview.findViewById(R.id.bt_eight);
        bt_seven = itemview.findViewById(R.id.bt_seven);
        bt_six = itemview.findViewById(R.id.bt_six);
        bt_file = itemview.findViewById(R.id.bt_file);
        bt_four = itemview.findViewById(R.id.bt_four);
        bt_three = itemview.findViewById(R.id.bt_three);
        bt_two = itemview.findViewById(R.id.bt_two);
        bt_one = itemview.findViewById(R.id.bt_one);
        btn_edit = itemview.findViewById(R.id.btn_edit);
        bt_Left = itemview.findViewById(R.id.bt_Left);
        bg_Login = itemview.findViewById(R.id.btn_login);

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
        btn_edit.setOnClickListener(this);
        bt_Left.setOnClickListener(this);
        bg_Login.setOnClickListener(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onClick(View view) {

        Onclicklogin onclicklogin = new Onclicklogin();

        switch (view.getId()){

            case R.id.btn_edit:
                onclicklogin.setTAG_KEY("edit");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_Left:
                onclicklogin.setTAG_KEY("nextto");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.btn_login:
                onclicklogin.setTAG_KEY("enter");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_one:
                onclicklogin.setTAG_KEY("1");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_two:
                onclicklogin.setTAG_KEY("2");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_three:
                onclicklogin.setTAG_KEY("3");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_four:
                onclicklogin.setTAG_KEY("4");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_file:
                onclicklogin.setTAG_KEY("5");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_six:
                onclicklogin.setTAG_KEY("6");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_seven:
                onclicklogin.setTAG_KEY("7");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_eight:
                onclicklogin.setTAG_KEY("8");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_nine:
                onclicklogin.setTAG_KEY("9");
                BusProvider.getInstance().post(onclicklogin);
                break;
            case R.id.bt_zero:
                onclicklogin.setTAG_KEY("0");
                BusProvider.getInstance().post(onclicklogin);
                break;
        }
    }
}

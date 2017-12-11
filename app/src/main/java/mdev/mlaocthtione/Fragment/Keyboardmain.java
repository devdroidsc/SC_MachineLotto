package mdev.mlaocthtione.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mdev.mlaocthtione.R;

/**
 * Created by Lenovo on 11-12-2017.
 */

public class Keyboardmain extends Fragment implements View.OnClickListener {
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
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}

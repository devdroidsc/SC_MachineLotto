package mdev.mlaocthtione.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import mdev.mlaocthtione.Adapter.CustomAdapterDetail;
import mdev.mlaocthtione.Adapter.CustomAdapterMain;
import mdev.mlaocthtione.BuildConfig;
import mdev.mlaocthtione.FormatHttpPostOkHttp.BasicNameValusPostOkHttp;
import mdev.mlaocthtione.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.Model.Modeldetail;
import mdev.mlaocthtione.Model.Modelitemlot;
import mdev.mlaocthtione.ModelBus.Onclickmain;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.bus.BusProvider;
import mdev.mlaocthtione.bus.ModelBus;
import mdev.mlaocthtione.con_bt.InstanceVariable;
import mdev.mlaocthtione.utils.Utils;

/**
 * Created by Lenovo on 06-12-2017.
 */

public class PageMain extends Fragment implements View.OnClickListener {

    private ArrayList<Modeldetail> list;
    private ArrayList<Modelitemlot> list_lot;
    private RecyclerView redetail;
    private RecyclerView re_savelot;
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager gridLayoutManager_savelot;
    private CustomAdapterMain adapter;
    private CustomAdapterDetail adapter_savelot;
    private LinearLayout laout_number,laout_savelot;

    private TextView btn_enter;

    private EditText edit_number;
    private TextView text_tital;
    private TextView btn_close_lot;


    private boolean Check_number = true, Check_numberTop = true,
            Check_numberlower = true, Check_numberToad = true;
    private AllCommand allCommand;
    private TelephonyManager tManager;
    private String uuid;
    private String StateTang;


    private MediaPlayer mpEffect;
    //Thread
    private HandlerThread backgroundHandlerThread;
    private Handler backgroundHandler;
    private Handler mainHandler;


    public PageMain(){}
    public static PageMain newInstance(){
        PageMain pageMain = new PageMain();
        return pageMain;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_number_main,container,false);
        itemView(view);
        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        onThreadPrintBill();
        //TODO : M Error 1
        //uuid = tManager.getDeviceId();
        uuid = "358918050979765";
        edit_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                edit_number.removeTextChangedListener(this);

                if (Check_number){
                    edit_number.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
                }else {
                    edit_number.setFilters(new InputFilter[] {new InputFilter.LengthFilter(13)});
                }

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    edit_number.setText(formattedString);
                    edit_number.setSelection(edit_number.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edit_number.addTextChangedListener(this);

            }
        });
    }
    private void itemView(View view){
        list = new ArrayList<>();
        list_lot = new ArrayList<>();
        allCommand = new AllCommand();


        laout_number = view.findViewById(R.id.laout_number);
        laout_savelot = view.findViewById(R.id.laout_savelot);

        btn_close_lot = view.findViewById(R.id.btn_close_lot);

        edit_number = view.findViewById(R.id.edit_number);
        edit_number.setKeyListener(null);
        redetail = view.findViewById(R.id.recy_detail);
        re_savelot = view.findViewById(R.id.re_savelot);

        btn_enter = view.findViewById(R.id.btn_enter);

        text_tital = view.findViewById(R.id.text_tital);
        text_tital.setText("เลข");

        btn_enter.setOnClickListener(this);


        btn_close_lot.setOnClickListener(this);


        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        adapter = new CustomAdapterMain(list,getActivity());

        gridLayoutManager_savelot = new GridLayoutManager(getActivity(),1);
        adapter_savelot = new CustomAdapterDetail(list_lot,getActivity());

        redetail.setAdapter(adapter);
        redetail.setLayoutManager(gridLayoutManager);
        redetail.setHasFixedSize(true);

        re_savelot.setAdapter(adapter_savelot);
        re_savelot.setLayoutManager(gridLayoutManager_savelot);
        re_savelot.setHasFixedSize(true);

        edit_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                int count = edit_number.length();
                if (count<=0){
                    btn_enter.setBackgroundResource(R.drawable.bg_number_nextto);
                    btn_enter.setText(R.string.text_next);
                }else {
                    btn_enter.setBackgroundResource(R.drawable.bg_number_enter);
                    btn_enter.setText(R.string.text_enter);
                }
                if (text_tital.getText().equals("เลข")){
                    btn_enter.setBackgroundResource(R.drawable.bg_number_enter);
                    btn_enter.setText(R.string.text_enter);
                }
            }
        });
        //setDataFist();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_enter:
                if (btn_enter.getText().equals("ตกลง")){
                    if (edit_number.length()>0){
                        setData();
                        redetail.smoothScrollToPosition(list.size());
                    }
                }else if (btn_enter.getText().equals("ข้าม")){
                    setNexto();
                }

                break;
            case R.id.btn_close_lot:

                laout_number.setVisibility(View.VISIBLE);
                laout_savelot.setVisibility(View.GONE);
                if (list_lot.size()>0){
                    list_lot.clear();
                    adapter_savelot.notifyDataSetChanged();
                }

                break;

        }
    }

    @Subscribe
    public void onClickMain(Onclickmain onclickmain){
        ModelBus modelBus = new ModelBus();
        if (!onclickmain.getTAG_KEY().equals("")){

            switch (onclickmain.getTAG_KEY()){

                case "edit":
                    int length = edit_number.getText().length();
                    if (length > 0) {
                        edit_number.getText().delete(length - 1, length);
                    }
                    break;
                case "Clear":
                    Clear_Dataset();
                    break;
                case "Savelot":
                    if(list.size()>0){
                        if (list.get(list.size()-1).getNumber().length()>0&&
                                list.get(list.size()-1).getTop().length()>0||list.get(list.size()-1).getButton().length()>0
                                ||list.get(list.size()-1).getToad().length()>0){
                            saveLot();
                        }

                    }
                    break;
                case "SettingPrinter":
                    modelBus.setPage(Utils.KEY_ADD_PAGE_PRINTER);
                    modelBus.setMsg(Utils.NAME_ADD_PAGE_PRINTER);
                    BusProvider.getInstance().post(modelBus);
                    break;
                case "Tang":
                    modelBus.setPage(Utils.KEY_ADD_PAGE_TANG_LOT_FAST);
                    modelBus.setMsg(Utils.NAME_ADD_PAGE_TANG_LOT_FAST);
                    BusProvider.getInstance().post(modelBus);
                    break;
                default:
                    setNumber(onclickmain.getTAG_KEY());
                    break;
            }
        }


    }

    private void Clear_Editext(){

        edit_number.setText(null);
        edit_number.requestFocus();
        edit_number.setFocusable(true);

    }
    private void setNumber(String number){
        edit_number.setText(edit_number.getText()+number);
    }
    private void setData(){

        if (Check_number){

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(edit_number.getText().toString());
            modeldetail.setTop("");
            modeldetail.setButton("");
            modeldetail.setToad("");

            modeldetail.setFocus_number("0");
            modeldetail.setFocus_top("1");
            modeldetail.setFocus_button("0");
            modeldetail.setFocus_toad("0");

            modeldetail.setNo_focus_top("0");
            modeldetail.setNo_focus_button("0");
            modeldetail.setNo_focus_toad("0");

            if (edit_number.getText().length()>2){
                modeldetail.setNo_focus_button("1");

            }else if (edit_number.getText().length()<=2){
                modeldetail.setNo_focus_toad("1");
            }

            list.add(modeldetail);
            adapter.notifyDataSetChanged();

            text_tital.setText("บน");
            Check_number = false;
            Check_numberTop = true;
            Check_numberlower = true;
            Check_numberToad = true;

            if (edit_number.getText().length()>2){
                Check_numberlower = false;

            }else if (edit_number.getText().length()<=2){
                Check_numberToad = false;
            }

            Clear_Editext();

        }else if (Check_numberTop){
            String number = edit_number.getText().toString();
            if (!number.equals("0")){

                Modeldetail modeldetail = new Modeldetail();
                modeldetail.setNumber(list.get(list.size()-1).getNumber());
                modeldetail.setTop(edit_number.getText().toString());
                modeldetail.setButton("");
                modeldetail.setToad("");

                modeldetail.setFocus_number("0");
                modeldetail.setFocus_top("0");
                modeldetail.setFocus_button("0");
                modeldetail.setFocus_toad("0");

                modeldetail.setNo_focus_top(list.get(list.size()-1).getNo_focus_top());
                modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
                modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

                if (Check_numberlower){
                    text_tital.setText("ล่าง");
                    modeldetail.setFocus_button("1");
                }else {
                    text_tital.setText("โต้ด");
                    modeldetail.setFocus_toad("1");
                }

                list.set(list.size()-1,modeldetail);
                adapter.notifyDataSetChanged();

                Check_numberTop = false;
                Clear_Editext();

            }


        }else if (Check_numberlower){
            String number = edit_number.getText().toString();
            if (!number.equals("0")){
                Modeldetail modeldetail = new Modeldetail();
                modeldetail.setNumber(list.get(list.size()-1).getNumber());
                modeldetail.setTop(list.get(list.size()-1).getTop());
                modeldetail.setButton(edit_number.getText().toString());
                modeldetail.setToad("");

                modeldetail.setFocus_number("0");
                modeldetail.setFocus_top("0");
                modeldetail.setFocus_button("0");
                modeldetail.setFocus_toad("0");

                modeldetail.setNo_focus_top(list.get(list.size()-1).getNo_focus_top());
                modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
                modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

                if (!Check_numberToad){
                    Log.e("MainActivity", "END");
                    text_tital.setText("เลข");
                    Check_number = true;
                    modeldetail.setFocus_number("1");

                }else {

                    text_tital.setText("โต้ด");
                    modeldetail.setFocus_toad("1");
                }

                list.set(list.size()-1,modeldetail);
                adapter.notifyDataSetChanged();

                Check_numberlower = false;
                Clear_Editext();
            }


        }else if (Check_numberToad){

            String number = edit_number.getText().toString();
            if (!number.equals("0")){
                Modeldetail modeldetail = new Modeldetail();
                modeldetail.setNumber(list.get(list.size()-1).getNumber());
                modeldetail.setTop(list.get(list.size()-1).getTop());
                modeldetail.setButton(list.get(list.size()-1).getButton());
                modeldetail.setToad(edit_number.getText().toString());

                modeldetail.setFocus_number("0");
                modeldetail.setFocus_top("0");
                modeldetail.setFocus_button("0");
                modeldetail.setFocus_toad("0");


                modeldetail.setNo_focus_top(list.get(list.size()-1).getNo_focus_top());
                modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
                modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

                if (!Check_numberlower){
                    Log.e("MainActivity", "END");
                    text_tital.setText("เลข");
                    Check_number = true;
                    modeldetail.setFocus_number("1");
                }

                list.set(list.size()-1,modeldetail);
                adapter.notifyDataSetChanged();

                Check_numberToad = false;
                Clear_Editext();
                //setDataFist();
            }
        }else {
            Log.e("MainActivity", "out");
        }
    }
    private void Clear_Dataset(){
        if (list.size()>0){
            list.clear();
            adapter.notifyDataSetChanged();
        }
        Clear_Editext();
        text_tital.setText("เลข");
        Check_number = true;
        //setDataFist();
    }
    private void setNexto(){
        if (!Check_number&&Check_numberlower){
            Clear_Editext();
            Check_numberTop = false;
            text_tital.setText("ล่าง");

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(list.get(list.size()-1).getNumber());
            modeldetail.setTop(list.get(list.size()-1).getTop());
            modeldetail.setButton(list.get(list.size()-1).getButton());
            modeldetail.setToad(list.get(list.size()-1).getToad());

            modeldetail.setFocus_number("0");
            modeldetail.setFocus_top("0");
            modeldetail.setFocus_button("1");
            modeldetail.setFocus_toad("0");

            modeldetail.setNo_focus_top("1");
            modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
            modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

            if (!Check_numberToad&&list.get(list.size()-1).getTop().length()>0){
                modeldetail.setFocus_button("0");
                modeldetail.setNo_focus_top("0");
                modeldetail.setNo_focus_button("1");
            }


            list.set(list.size()-1,modeldetail);
            adapter.notifyDataSetChanged();

            if (!Check_numberToad&&list.get(list.size()-1).getTop().length()>0){
                text_tital.setText("เลข");
                Check_number = true;
                btn_enter.setBackgroundResource(R.drawable.bg_number_enter);
                btn_enter.setText(R.string.text_enter);
            }

        }else if (!Check_number&&Check_numberToad){
            Clear_Editext();
            Check_numberTop = false;
            text_tital.setText("โต้ด");

            Modeldetail modeldetail = new Modeldetail();
            modeldetail.setNumber(list.get(list.size()-1).getNumber());
            modeldetail.setTop(list.get(list.size()-1).getTop());
            modeldetail.setButton(list.get(list.size()-1).getButton());
            modeldetail.setToad(list.get(list.size()-1).getToad());

            modeldetail.setFocus_number("0");
            modeldetail.setFocus_top("0");
            modeldetail.setFocus_button("0");
            modeldetail.setFocus_toad("1");

            modeldetail.setNo_focus_top("1");
            modeldetail.setNo_focus_button(list.get(list.size()-1).getNo_focus_button());
            modeldetail.setNo_focus_toad(list.get(list.size()-1).getNo_focus_toad());

            if (!Check_numberlower&&list.get(list.size()-1).getTop().length()>0){
                modeldetail.setFocus_toad("0");
                modeldetail.setNo_focus_toad("1");
                modeldetail.setNo_focus_top("0");
            }

            list.set(list.size()-1,modeldetail);
            adapter.notifyDataSetChanged();

            if (!Check_numberlower&&list.get(list.size()-1).getTop().length()>0){
                text_tital.setText("เลข");
                Check_number = true;
                btn_enter.setBackgroundResource(R.drawable.bg_number_enter);
                btn_enter.setText(R.string.text_enter);
                //setDataFist();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void saveLot(){

        if (allCommand.isConnectingToInternet(getActivity())){

            final String urlServer = allCommand.GetStringShare(getActivity(),allCommand.moURL,"");
            final String moMid = allCommand.GetStringShare(getActivity(),allCommand.moMemberID,"");
            /*Log.e("MainActivity", "URL SET "+urlServer);*/
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    ArrayList<FromHttpPostOkHttp> param = new ArrayList<>();
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("mid",moMid));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("txt",setFomatTxtSavelot(list)));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("lat","0.0"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("lon","0.0"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("zone","1"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("bf","a1"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("lot_page","1"));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("uuid",uuid));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("pname",""));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("pid",""));
                    return allCommand.POST_OK_HTTP_SendData(urlServer+"save_lot.php",param);
                    ///return allCommand.GET_OK_HTTP_SendData("http://192.168.1.45/testMomo/save_lot.php");
                }

                @Override
                protected void onPostExecute(String s) {
                    //s = "{\"Status\":\"1\",\"Licen\":\"SC\",\"BillID\":\"175292\",\"LastLot\":\"16-11-2017\",\"CloseBig\":\"1512116400\",\"CloseSmall\":\"1512116400\",\"data\":[{\"txt\":\"3บน[456]= 10 สำเร็จ\",\"status\":1},{\"txt\":\"3ล่างหลัง[456]= เลขเต็ม คงเหลือ : 10\",\"status\":2}],\"Msg\":\"สำเร็จ\"}";
                    allCommand.ShowLogCat("MainActivity ",s);
                    boolean isSound = false;
                    boolean isPrintBill = false;
                    try {
                        JSONObject jsonTang = new JSONObject(s);
                        if (jsonTang.getString("Status").equals("1")){
                            String billId = jsonTang.getString("BillID");
                            laout_number.setVisibility(View.GONE);
                            laout_savelot.setVisibility(View.VISIBLE);
                            JSONArray jArray = new JSONArray(jsonTang.getString("data"));
                            for (int i = 0;i<jArray.length();i++){
                                JSONObject jObject = jArray.getJSONObject(i);
                                Modelitemlot modelitemlot = new Modelitemlot();
                                modelitemlot.setNumberlot(jObject.getString("txt"));
                                modelitemlot.setStatuslot(jObject.getString("status"));
                                list_lot.add(modelitemlot);
                                adapter_savelot.notifyDataSetChanged();
                                if (!(jObject.getString("status").toString().trim().equals("1"))){
                                    isSound = true;
                                }else {
                                    isPrintBill = true;
                                }
                            }
                            if (isPrintBill){
                                Message msgBack = new Message();
                                msgBack.obj = billId.toString().trim();//Bill ID
                                backgroundHandler.sendMessageDelayed(msgBack,1000);
                            }
                        }else {
                            laout_number.setVisibility(View.GONE);
                            laout_savelot.setVisibility(View.VISIBLE);
                            Modelitemlot modelitemlot = new Modelitemlot();
                            modelitemlot.setNumberlot(jsonTang.getString("Msg"));
                            modelitemlot.setStatuslot(jsonTang.getString("Status"));
                            list_lot.add(modelitemlot);
                            adapter_savelot.notifyDataSetChanged();
                            isSound = true;
                        }
                        if (isSound){
                            playSoundEffect(getActivity(),R.raw.soundtang_no);
                        }

                        Clear_Dataset();
                        Clear_Editext();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
    private String setFomatTxtSavelot(ArrayList<Modeldetail> listsave){

        String save_Lot = "";

        for (int i=0;i<listsave.size();i++){

            save_Lot += allCommand.DeleteFormatNumber(listsave.get(i).getNumber())+"-"+allCommand.DeleteFormatNumber(listsave.get(i).getTop())+"-"+
                    allCommand.DeleteFormatNumber(listsave.get(i).getToad())+"-"+allCommand.DeleteFormatNumber(listsave.get(i).getButton());

            if (i+1<listsave.size()){
                save_Lot += ",";
            }
        }

        return "("+save_Lot+")";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
        if (backgroundHandlerThread != null) {
            backgroundHandlerThread.quit();
        }
        stopSoundEffect();
    }

    private void onThreadPrintBill(){
        backgroundHandlerThread = new HandlerThread("BackgroundHandlerThread");
        backgroundHandlerThread.start();
        backgroundHandler = new Handler(backgroundHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //Run with background
                try {
                    String idBill = (String) msg.obj;
                    if (InstanceVariable.mConnector.isConnected()) {
                        //Show Status Printing
                        Message msgMain2 = new Message();
                        msgMain2.arg1 = 2;
                        msgMain2.obj = msg.obj;
                        mainHandler.sendMessage(msgMain2);
                        //Load Bill
                        onLoadBillFromServer(idBill);
                        //Print Bill
                        InstanceVariable.mConnector.PrintImage(
                                Utils.PATH_BILL,
                                idBill,
                                allCommand.GetStringShare(getActivity(),Utils.SHARE_BARCODE,"").toString().trim());

                        //Print Successful
                        Message msgMain = new Message();
                        msgMain.arg1 = 1;
                        msgMain.obj = msg.obj;
                        mainHandler.sendMessage(msgMain);
                    }else {
                        //Load Bill
                        onLoadBillFromServer(idBill);
                        //No Print
                        Message msgMain = new Message();
                        msgMain.arg1 = 0;
                        msgMain.obj = msg.obj;
                        mainHandler.sendMessage(msgMain);
                    }
                }catch (Exception e){
                    onShowLogCat("***Err***","Err Print Bill " + e.getMessage());
                }
            }
        };
        mainHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //Run with Main Thread
                if (msg.arg1 == 1){
                    onShowLogCat("Status Print","Successful");
                    deleteFilePrint((String) msg.obj);
                    //lnStatusPrinting.startAnimation(animHide);
                }if (msg.arg1 == 2){
                    onShowLogCat("Status Print","Show Printing...");
                    /*lnStatusPrinting.setVisibility(View.VISIBLE);
                    lnStatusPrinting.startAnimation(animShow);*/
                }else {
                    onShowLogCat("Status Print","No Print");
                    addFileBill((String) msg.obj);
                }
            }
        };
    }
    private void onLoadBillFromServer(String idBill){
        final String urlServer = allCommand.GetStringShare(getActivity(), allCommand.moURL, "");
        final int paper = allCommand.getIntShare(getActivity(), Utils.SHARE_SETTING_PAPER, 2);
        final String barcode = allCommand.GetStringShare(getActivity(),Utils.SHARE_BARCODE, "");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File SDCardRoot = null;
        URL url = null;
        try {
            url = new URL(urlServer + "print_lotto_new.php?id=" + idBill
                    + "&barcode=" + barcode
                    + "&paper=" + paper);
            onShowLogCat("Url Img Lotto:  ", url + "");
            inputStream = url.openStream();
            SDCardRoot = new File(Environment.getExternalStorageDirectory(), Utils.PATH_BILL);
            SDCardRoot.mkdirs();
            File file = new File(SDCardRoot, "bill" + idBill + ".png");
            outputStream = new FileOutputStream(file);
            try {
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) >= 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } finally {
                outputStream.close();
            }
        } catch (Exception e) {
            onShowLogCat("Err", "Download bill " + e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void addFileBill(String billId){
        try {
            File SDCardRoot = new File(Environment.getExternalStorageDirectory(), Utils.PATH_BILL);
            File file = new File(SDCardRoot, "bill" + billId + ".png");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                //File f = new File("file://"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
                onShowLogCat("Path Save Bill ",file.toString() + "\n" + file.getPath());
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                getActivity().sendBroadcast(mediaScanIntent);
            }else{
                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(file.getPath())));
            }
        }catch (Exception e){
            onShowLogCat("Err","addFileBill " + e.getMessage());
        }

    }
    public void deleteFilePrint(String billId){
        try {
            File SDCardRoot = new File(Environment.getExternalStorageDirectory(), Utils.PATH_BILL);
            File file = new File(SDCardRoot, "bill" + billId + ".png");
            if (file.isFile()){
                file.delete();
            }
        }catch (Exception e){
            onShowLogCat("Err","deleteFilePrint " + e.getMessage());
        }
    }
    // เสียยง?
    public void playSoundEffect(Context context, int resId) {
        stopSoundEffect();
        mpEffect = MediaPlayer.create(context, resId);
        mpEffect.start();
        mpEffect.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }
    public void stopSoundEffect() {
        try {
            if (mpEffect != null && mpEffect.isPlaying()) {
                mpEffect.stop();
                mpEffect.release();
            }
        } catch (Exception e) {
        }
    }

    public void onShowLogCat(String tag, String msg){
        if (BuildConfig.DEBUG) {
            Log.e("***PageMain ***", tag + " ==> " + msg);
        }
    }

}

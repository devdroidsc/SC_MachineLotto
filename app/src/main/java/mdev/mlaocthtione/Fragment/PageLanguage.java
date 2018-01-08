package mdev.mlaocthtione.Fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mdev.mlaocthtione.Adapter.CustomAdapterLangguane;
import mdev.mlaocthtione.Adapter.CustomAdapterNamberFull;
import mdev.mlaocthtione.FormatHttpPostOkHttp.BasicNameValusPostOkHttp;
import mdev.mlaocthtione.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.Model.ModelitemLanguane;
import mdev.mlaocthtione.Model.ModelitemNumberFull;
import mdev.mlaocthtione.ModelBus.OnclickPrinter;
import mdev.mlaocthtione.ModelBus.Onclicklogin;
import mdev.mlaocthtione.ModelBus.Onclickmain;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.bus.BusProvider;

/**
 * Created by Lenovo on 03-01-2018.
 */

public class PageLanguage extends Fragment{

    public PageLanguage() {
    }

    public static PageLanguage newInstance(){
        PageLanguage pageLanguage = new PageLanguage();
        return pageLanguage;
    }

    private AllCommand allCommand;
    private ArrayList<ModelitemLanguane> listLanguane;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView re_language;
    private CustomAdapterLangguane adapter;
    private TextView titel_language;
    private AVLoadingIndicatorView avloadLanguane;
    private RelativeLayout avi_loadlanguane;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCommand = new AllCommand();
        listLanguane = new ArrayList<>();

        if (savedInstanceState != null){

            listLanguane = savedInstanceState.getParcelableArrayList("listLanguane");
            Log.e("PageLanguage", "in savedInstanceState");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.page_language,container,false);
        itemView(view);
        return view;
    }


    private void itemView(View view){

        avloadLanguane = view.findViewById(R.id.avloadLanguane);
        avi_loadlanguane = view.findViewById(R.id.avi_loadlanguane);

        re_language = view.findViewById(R.id.re_language);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        adapter = new CustomAdapterLangguane(listLanguane,getActivity());

        re_language.setAdapter(adapter);
        re_language.setLayoutManager(gridLayoutManager);
        re_language.setHasFixedSize(true);
        titel_language = view.findViewById(R.id.titel_language);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("listLanguane",listLanguane);
    }

    @Override
    public void onResume() {
        super.onResume();
        titel_language.setText(allCommand.GetStringShare(getContext(),allCommand.text_Languane,"Language"));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (listLanguane.size()<=0){
            setData();
        }

        adapter.setOnItemClickNo(new CustomAdapterLangguane.onItemClickNo() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onItemClickNo(int position, String check, final String url_file,String key_langane) {

                setListLanguane(position,check);

                //Log.e("PageLanguage", url_file.substring(0,url_file.indexOf(".")));
                allCommand.SaveStringShare(getContext(),allCommand.Check_Languane,key_langane);

                if (allCommand.isConnectingToInternet(getContext())){
                    final String urlServer = allCommand.GetStringShare(getActivity(),allCommand.moURL,"");
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            avi_loadlanguane.setVisibility(View.VISIBLE);
                            avloadLanguane.setVisibility(View.VISIBLE);
                        }

                        @Override
                        protected String doInBackground(String... strings) {

                            return allCommand.GET_OK_HTTP_SendData(urlServer+url_file);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            allCommand.ShowLogCat("Languane_List ",s);
                            avi_loadlanguane.setVisibility(View.GONE);
                            avloadLanguane.setVisibility(View.GONE);
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(s);
                                allCommand.SaveStringShare(getContext(),allCommand.text_ok,jsonObject.getString("ok"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_logout,jsonObject.getString("exit_app"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_returns,jsonObject.getString("back"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_inputNumber,jsonObject.getString("alert_input_data"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_Top,jsonObject.getString("on"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_lower,jsonObject.getString("down"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_Toad,jsonObject.getString("tod"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_printer,jsonObject.getString("menu_printer"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_edit,jsonObject.getString("delete"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_cancel,jsonObject.getString("cancel"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_confirm,jsonObject.getString("Confirm"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_numberFull,jsonObject.getString("menu_lotfull"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_admin,jsonObject.getString("enter_code"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_yes,jsonObject.getString("yes"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_no,jsonObject.getString("no"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_user,jsonObject.getString("username"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_pass,jsonObject.getString("password"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_login,jsonObject.getString("login"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_Number,jsonObject.getString("num"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_Next,jsonObject.getString("nextto"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_Languane,jsonObject.getString("menu_language"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_close_bet,jsonObject.getString("close_bet"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_save_success,jsonObject.getString("save_success"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_connected_printer,jsonObject.getString("connecting_printer"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_connecting_printer,jsonObject.getString("connected_printer"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_no_connect_printer,jsonObject.getString("no_connect_printer"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_search,jsonObject.getString("search"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_paper,jsonObject.getString("paper"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_paper_3,jsonObject.getString("paper_3"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_paper_2,jsonObject.getString("paper_2"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_searching,jsonObject.getString("searching"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_list_device,jsonObject.getString("list_device"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_device_connected,jsonObject.getString("device_connected"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_close_printer,jsonObject.getString("close_printer"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_status_printing,jsonObject.getString("status_printing"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_alert_input_data,jsonObject.getString("alert_input_data"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_no_internet,jsonObject.getString("no_internet"));
                                allCommand.SaveStringShare(getContext(),allCommand.text_userincorrect,jsonObject.getString("user_incorrect"));

                                Onclickmain onclickmain = new Onclickmain();
                                onclickmain.setTAG_KEY("back");
                                BusProvider.getInstance().post(onclickmain);

                            } catch (JSONException e) {
                                allCommand.ShowLogCat("error mexx : ",e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }else {
                    allCommand.ShowAertDialog_OK(allCommand.GetStringShare(getContext(),allCommand.text_no_internet,"Please connect to the internet."),getContext());

                }

            }
        });
    }

    private void setListLanguane(int position,String check){

        for (int i = 0; i < listLanguane.size(); i++) {

            ModelitemLanguane modelitem = new ModelitemLanguane();
            modelitem.setTitle(listLanguane.get(i).getTitle());
            modelitem.setKeys(listLanguane.get(i).getKeys());
            modelitem.setFile(listLanguane.get(i).getFile());
            modelitem.setUrl(listLanguane.get(i).getUrl());
            modelitem.setCheck_use("0");
            listLanguane.set(i,modelitem);
        }

        ModelitemLanguane modelitem = new ModelitemLanguane();
        modelitem.setTitle(listLanguane.get(position).getTitle());
        modelitem.setKeys(listLanguane.get(position).getKeys());
        modelitem.setFile(listLanguane.get(position).getFile());
        modelitem.setUrl(listLanguane.get(position).getUrl());
        modelitem.setCheck_use(check.toString());
        listLanguane.set(position,modelitem);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("StaticFieldLeak")
    private void setData(){

        if (allCommand.isConnectingToInternet(getContext())){
            final String urlServer = allCommand.GetStringShare(getActivity(),allCommand.moURL,"");
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... strings) {

                    return allCommand.GET_OK_HTTP_SendData(urlServer+"listlang.php");
                }

                @Override
                protected void onPostExecute(String s) {
                    allCommand.ShowLogCat("Languane_List ",s);
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject jsonObject;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            ModelitemLanguane modelitem = new ModelitemLanguane();
                            modelitem.setTitle(jsonObject.getString("title"));
                            modelitem.setKeys(jsonObject.getString("keys"));
                            modelitem.setFile(jsonObject.getString("file"));
                            modelitem.setUrl(jsonObject.getString("url"));
                            modelitem.setCheck_use("0");
                            if (allCommand.GetStringShare(getContext(),allCommand.Check_Languane,"").equals(jsonObject.getString("keys"))){
                                modelitem.setCheck_use("1");
                            }
                            listLanguane.add(modelitem);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}

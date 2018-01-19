package mdev.mlaocthtione.Fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mdev.mlaocthtione.Adapter.CustomAdapterMain;
import mdev.mlaocthtione.Adapter.CustomAdapterNamberFull;
import mdev.mlaocthtione.FormatHttpPostOkHttp.BasicNameValusPostOkHttp;
import mdev.mlaocthtione.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.Model.ModelitemNumberFull;
import mdev.mlaocthtione.Model.Modelitemlot;
import mdev.mlaocthtione.R;

/**
 * Created by Lenovo on 25-12-2017.
 */

public class PageNumberfull extends Fragment {

    public PageNumberfull() {
    }
    public static PageNumberfull newInstance(){
        PageNumberfull pageNumberfull = new PageNumberfull();
        return pageNumberfull;
    }

    private AllCommand allCommand;
    private ArrayList<ModelitemNumberFull> listnumber;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView re_number_full;
    private CustomAdapterNamberFull adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_title_number_full;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCommand = new AllCommand();
        listnumber = new ArrayList<>();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_number_full,container,false);
        itemView(view);
        return view;
    }
    private void itemView(View view){

        tv_title_number_full = view.findViewById(R.id.tv_title_number_full);

        re_number_full = view.findViewById(R.id.re_number_full);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        adapter = new CustomAdapterNamberFull(getActivity(),listnumber);

        re_number_full.setAdapter(adapter);
        re_number_full.setLayoutManager(gridLayoutManager);
        re_number_full.setHasFixedSize(true);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (listnumber.size()<=0){
            setData();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listnumber.size()>0){
                    listnumber.clear();
                    adapter.notifyDataSetChanged();
                }
                setData();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        tv_title_number_full.setText(allCommand.GetStringShare(getContext(),allCommand.text_numberFull,"Limit number"));
    }

    @SuppressLint("StaticFieldLeak")
    private void setData(){

        if (allCommand.isConnectingToInternet(getContext())){
            swipeRefreshLayout.setRefreshing(true);
            final String urlServer = allCommand.GetStringShare(getActivity(),allCommand.moURL,"");
            final String moMid = allCommand.GetStringShare(getActivity(),allCommand.moMemberID,"");
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    ArrayList<FromHttpPostOkHttp> param = new ArrayList<>();
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("mid",moMid));
                    param.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("lang", allCommand.GetStringShare(getContext(),allCommand.Check_Languane,"")));
                    return allCommand.POST_OK_HTTP_SendData(urlServer+"getFull.php",param);
                }

                @Override
                protected void onPostExecute(String s) {
                    allCommand.ShowLogCat("Number_Full ",s);
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject jsonObject;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            String lotTxt = jsonObject.getString("lot_txt");
                            if (lotTxt.length() > 0) {
                                ModelitemNumberFull modelitem = new ModelitemNumberFull();
                                modelitem.setHeader(jsonObject.getString("lot_type"));
                                modelitem.setDetail(lotTxt);
                                listnumber.add(modelitem);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    swipeRefreshLayout.setRefreshing(false);
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }else {
            allCommand.ShowAertDialog_OK(allCommand.GetStringShare(getContext(),allCommand.text_no_internet,"Please connect to the internet."),getContext());
        }
    }
}

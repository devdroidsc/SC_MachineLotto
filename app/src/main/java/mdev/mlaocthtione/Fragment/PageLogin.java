package mdev.mlaocthtione.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.squareup.otto.Subscribe;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import mdev.mlaocthtione.FormatHttpPostOkHttp.BasicNameValusPostOkHttp;
import mdev.mlaocthtione.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.ModelBus.Onclicklogin;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.bus.BusProvider;
import mdev.mlaocthtione.bus.ModelBus;
import mdev.mlaocthtione.utils.Utils;

/**
 * Created by Lenovo on 11-12-2017.
 */

public class PageLogin extends Fragment{
    private AllCommand allCommand;
    private String strStatus = "",strURLmo = "",strUsername = " ",strPassword = " ";
    private EditText edUsername,edPassword;
    private TextInputLayout tilEdPassword,tilEdUsername;
    private ImageView im_logo_login;
    private AVLoadingIndicatorView avloadLogin,avi_loadLogo;
    private boolean Checkimage = false;

    public PageLogin() {
    }

    public static PageLogin newInstance(){
        PageLogin pageLogin = new PageLogin();
        return  pageLogin;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCommand = new AllCommand();
        BusProvider.getInstance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_login,container,false);
        itemView(view);
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*Uri data = getActivity().getIntent().getData();
        if (data != null) {
            String uri = getActivity().getIntent().getDataString();
            allCommand.ShowLogCat("uri",uri);
            String dataUP = new String(uri);
            String[] sUserPass = dataUP.split("/");
            try{

                String dataUserPass = allCommand.getEncodeBase64(sUserPass[sUserPass.length - 1].toString());
                String[] arrUserPass = dataUserPass.split("##.##");
                for (int i= 0;i<arrUserPass.length;i++){
                    allCommand.ShowLogCat("User Pass" + i, arrUserPass[i].toString());
                }
                if (arrUserPass.length == 2){
                    allCommand.SaveStringShare(getContext(),allCommand.moSaveUser,arrUserPass[0].toString());
                    allCommand.SaveStringShare(getContext(),allCommand.moSavePass,arrUserPass[1].toString());
                }
            }catch (Exception e){
                allCommand.ShowLogCat("Error","get Base 64 User Pass " + e.getMessage());
            }
        }

        edUsername.setText(allCommand.GetStringShare(getContext(),allCommand.moSaveUser,""));
        edPassword.setText(allCommand.GetStringShare(getContext(),allCommand.moSavePass,""));

        if (allCommand.isConnectingToInternet(getContext())) {
            onLogin();
        } else {
            allCommand.ShowAertDialog_OK(getResources().getString(R.string.msg_connect_internet),getContext());
        }*/

        File myDir =new File(android.os.Environment.getExternalStorageDirectory()+ "/Android/data/"
                + getActivity().getPackageName(),"img");
        File imageFile = new File(myDir, "logo.png");

        if(imageFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            im_logo_login.setImageBitmap(myBitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onClickBus(Onclicklogin onclicklogin){

        if (!onclicklogin.getTAG_KEY().equals("")){

            switch (onclicklogin.getTAG_KEY()) {
                case "edit":

                    int lengthuser = edUsername.getText().length();
                    int lengthpass = edPassword.getText().length();

                    if (getActivity().getCurrentFocus().getId() == edUsername.getId()) {
                        edUsername.getText().delete(lengthuser - 1, lengthuser);

                    } else if (getActivity().getCurrentFocus().getId() == edPassword.getId()) {
                        edPassword.getText().delete(lengthpass - 1, lengthpass);
                    }

                    break;
                case "nextto":
                    setbg_edtext();
                    break;
                case "enter":
                    //Log.e("PageLogin", "Wllcome");
                    onLogin();
                    break;
                default:
                    setNumber(onclicklogin.getTAG_KEY());
                    break;
            }

        }

    }
    private void itemView(View itemview){

        edUsername =  itemview.findViewById(R.id.edUsername);
        edPassword =  itemview.findViewById(R.id.edPassword);
        tilEdPassword = itemview.findViewById(R.id.tilEdPassword);
        tilEdUsername = itemview.findViewById(R.id.tilEdUsername);
        im_logo_login = itemview.findViewById(R.id.im_logo_login);
        avloadLogin = itemview.findViewById(R.id.avloadLogin);
        avi_loadLogo = itemview.findViewById(R.id.avi_loadLogo);

        edUsername.setText("aaaa01@zx");
        edPassword.setText("1111");

        edUsername.setKeyListener(null);
        edPassword.setKeyListener(null);

        tilEdUsername.setBackgroundResource(R.drawable.bg_ed_select);
    }

    private void setbg_edtext(){
        if (getActivity().getCurrentFocus().getId() == edUsername.getId()){
            edPassword.requestFocus();
            tilEdPassword.setBackgroundResource(R.drawable.bg_ed_select);
            tilEdUsername.setBackgroundResource(R.drawable.bg_ed_pass);
        }else if (getActivity().getCurrentFocus().getId() == edPassword.getId()){
            edUsername.requestFocus();
            tilEdUsername.setBackgroundResource(R.drawable.bg_ed_select);
            tilEdPassword.setBackgroundResource(R.drawable.bg_ed_pass);
        }
    }

    private void setNumber(String number){
        if (getActivity().getCurrentFocus().getId() == edUsername.getId()){
            edUsername.setText(edUsername.getText()+number);
        }else if (getActivity().getCurrentFocus().getId() == edPassword.getId()){
            edPassword.setText(edPassword.getText()+number);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void onLogin() {
        if (edUsername.length() > 0 && edPassword.length() > 0) {
            new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    avloadLogin.setVisibility(View.VISIBLE);
                }

                @Override
                protected String doInBackground(String... strings) {
                    ArrayList<FromHttpPostOkHttp> params_login = new ArrayList<FromHttpPostOkHttp>();
                    params_login.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("server", getUserFormat(2)));
                    return allCommand.POST_OK_HTTP_SendData("My_URL.php", params_login);
                }

                @Override
                protected void onPostExecute(String s) {
                    try {
                        allCommand.ShowLogCat("*** url ***", s);
                        final JSONObject jObject;
                        jObject = new JSONObject(allCommand.CoverStringFromServer_One(s));
                        strURLmo = jObject.getString("url");
                        allCommand.SaveStringShare(getContext(),allCommand.moURL,strURLmo);
                        setImageLogo();
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected void onPreExecute() {
                                strUsername = getUserFormat(1);
                                strPassword = edPassword.getText().toString().trim();
                            }

                            @Override
                            protected String doInBackground(String... strings) {
                                String str_Url = strURLmo + "checkLogin.php";
                                ArrayList<FromHttpPostOkHttp> params = new ArrayList<FromHttpPostOkHttp>();
                                params.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("sUsername ", strUsername));
                                params.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("sPassword ", strPassword));
                                return allCommand.POST_OK_HTTP_SendData(str_Url, params);
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                allCommand.ShowLogCat("*** Login ***", s);
                                try {
                                    JSONObject jOLogin =	new JSONObject(s);
                                    strStatus = jOLogin.getString("Status");
                                    if (strStatus.toString().equals("1")) {

                                        allCommand.SaveStringShare(getContext(),allCommand.moSaveUser,edUsername.getText().toString());;
                                        allCommand.SaveStringShare(getContext(),allCommand.moSavePass,edPassword.getText().toString());;
                                        allCommand.SaveStringShare(getContext(),allCommand.moCradit,jOLogin.getString("MemberCradit"));
                                        allCommand.SaveStringShare(getContext(),allCommand.moMemberID,jOLogin.getString("MemberID"));
                                        allCommand.SaveStringShare(getContext(),allCommand.moName,jOLogin.getString("Name"));
                                        allCommand.SaveStringShare(getContext(),allCommand.moCloseBig,jOLogin.getString("CloseBig"));
                                        allCommand.SaveStringShare(getContext(),allCommand.moCloseSmall,jOLogin.getString("CloseSmall"));
                                        ;
                                        String max1 = jOLogin.getString("MemberMax").toString().trim();
                                        String min1 = jOLogin.getString("MemberMin").toString().trim();
                                        if (max1.toString().trim().length() <= 0){
                                            max1 = "1";
                                        }
                                        if (min1.toString().trim().length() <= 0){
                                            min1 = "1";
                                        }

                                        allCommand.SaveStringShare(getContext(),allCommand.moTangMax,max1);

                                        String max = jOLogin.getString("MemberMax").toString().trim();
                                        String min = jOLogin.getString("MemberMin").toString().trim();
                                        if (max.toString().trim().length() <= 0){
                                            max = "1";
                                        }
                                        if (min.toString().trim().length() <= 0){
                                            min = "1";
                                        }
                                        allCommand.SaveStringShare(getContext(),allCommand.moTangMax,max);
                                        allCommand.SaveStringShare(getContext(),allCommand.moTangMin,min);

                                        ModelBus modelBus = new ModelBus();
                                        modelBus.setPage(Utils.KEY_ADD_PAGE_TANG_LOT_FAST);
                                        modelBus.setMsg(Utils.NAME_ADD_PAGE_TANG_LOT_FAST);
                                        BusProvider.getInstance().post(modelBus);


                                    }else {
                                        allCommand.ShowAertDialog_OK("ไม่พบชื่อผู้ใช้นี้",getActivity());
                                    }
                                }catch (Exception e){
                                    allCommand.ShowLogCat("*** Err ***", "Err SetDataLogin " + e.getMessage());
                                }
                                avloadLogin.setVisibility(View.INVISIBLE);
                            }

                        }.execute();

                    } catch (Exception e) {
                        avloadLogin.setVisibility(View.INVISIBLE);
                        allCommand.ShowLogCat("*** Err ***", "Err SetDataGetUrl " + e.getMessage());
                    }
                }

            }.execute();
        }
    }
    private void setImageLogo(){
        Glide.with(this)
                .load(strURLmo+"img/logo99.png")
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        im_logo_login.setImageBitmap(resource);
                        saveImage(resource);
                        Log.e("PageLogin", saveImage(resource));

                    }
                });

    }

    private String saveImage(Bitmap image) {
        File myDir =new File(android.os.Environment.getExternalStorageDirectory()+ "/Android/data/"
                + getActivity().getPackageName(),"img");

        String savedImagePath = null;
        String imageFileName = "logo.png";

        boolean success = true;
        if (!myDir.exists()) {
            success = myDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(myDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return savedImagePath;
    }
    private String getUserFormat(int status){
        String userName = edUsername.getText().toString().trim();
        final String[] arrUserName = userName.split("\\@");
        if (status == 2){//server
            if (arrUserName.length == 2){
                return arrUserName[1];
            }else {
                return "";
            }
        }else if (status == 1){//user
            if (arrUserName.length == 2){
                return arrUserName[0];
            }else {
                return userName;
            }
        }else {//null
            return userName;
        }
    }

}

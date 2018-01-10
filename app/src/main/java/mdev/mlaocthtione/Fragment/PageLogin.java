package mdev.mlaocthtione.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.squareup.otto.Subscribe;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import mdev.mlaocthtione.FormatHttpPostOkHttp.BasicNameValusPostOkHttp;
import mdev.mlaocthtione.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.ModelBus.Onclicklogin;
import mdev.mlaocthtione.ModelBus.Onclickmain;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.activity.login;
import mdev.mlaocthtione.bus.BusProvider;
import mdev.mlaocthtione.bus.ModelBus;
import mdev.mlaocthtione.utils.Utils;

/**
 * Created by Lenovo on 11-12-2017.
 */

public class PageLogin extends Fragment {
    private AllCommand allCommand;
    private String strStatus = "", strURLmo = "", strUsername = " ", strPassword = " ";
    private EditText edUsername, edPassword;
    private TextInputLayout tilEdPassword, tilEdUsername;
    private ImageView im_logo_login;
    private AVLoadingIndicatorView avloadLogin, avi_loadLogo;
    private boolean Checkimage = false;
    private TextView check_no_onClick;
    private String img_url;

    private static final String TAG = "PageLogin";
    final private int REQUEST_MUTIPLE = 124;
    private TelephonyManager tManager;
    private String uuid;
    private int STATUS_FOCUS;

    public PageLogin() {
    }

    public static PageLogin newInstance() {
        PageLogin pageLogin = new PageLogin();
        return pageLogin;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCommand = new AllCommand();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_login, container, false);
        itemView(view);
        return view;
    }

    @SuppressLint({"StaticFieldLeak", "ClickableViewAccessibility"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        STATUS_FOCUS = 2;//รอบต่อไปเข้า Pass

        edUsername.setText(allCommand.GetStringShare(getContext(), allCommand.moSaveUser, ""));
        edPassword.setText(allCommand.GetStringShare(getContext(), allCommand.moSavePass, ""));
        edUsername.setText("aaaa02");



        if (allCommand.isConnectingToInternet(getContext())) {
            onPermissionMultiple();
        } else {
            allCommand.ShowAertDialog_OK(allCommand.GetStringShare(getContext(),allCommand.text_no_internet,"Please connect to the internet."),getContext());
        }

        File myDir =new File(android.os.Environment.getExternalStorageDirectory()+ "/Android/data/"
                + getActivity().getPackageName(),"img");
        File imageFile = new File(myDir, "logo.png");

        if(imageFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            im_logo_login.setImageBitmap(myBitmap);
        }

        check_no_onClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

                    if (STATUS_FOCUS == 1){
                        int length = edPassword.getText().length();
                        if (length > 0) {
                            edPassword.getText().delete(length - 1, length);
                        }
                    }else if (STATUS_FOCUS == 2){
                        int length = edUsername.getText().length();
                        if (length > 0) {
                            edUsername.getText().delete(length - 1, length);
                        }
                    }
                    break;
                case "nextto":
                    setbg_edtext();
                    break;
                case "enter":
                    onPermissionMultiple();
                    break;
                case "onClick":
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
        check_no_onClick = itemview.findViewById(R.id.check_no_onClick);

        edUsername.setKeyListener(null);
        edPassword.setKeyListener(null);

        tilEdPassword.setHint(allCommand.GetStringShare(getContext(),allCommand.text_pass,"password"));
        tilEdUsername.setHint(allCommand.GetStringShare(getContext(),allCommand.text_user,"username"));


        tilEdUsername.setBackgroundResource(R.drawable.bg_ed_select);
    }

    private void setbg_edtext(){
        if (STATUS_FOCUS == 1){
            STATUS_FOCUS = 2;
            edUsername.requestFocus();
            tilEdUsername.setBackgroundResource(R.drawable.bg_ed_select);
            tilEdPassword.setBackgroundResource(R.drawable.bg_ed_pass);
        }else if (STATUS_FOCUS == 2){
            STATUS_FOCUS = 1;
            edPassword.requestFocus();
            tilEdPassword.setBackgroundResource(R.drawable.bg_ed_select);
            tilEdUsername.setBackgroundResource(R.drawable.bg_ed_pass);
        }
    }

    private void setNumber(String number){
        if (STATUS_FOCUS == 1){
            edPassword.setText(edPassword.getText().toString()+number);
        }else if (STATUS_FOCUS == 2){
            edUsername.setText(edUsername.getText().toString()+number);
        }
        /*if (getActivity().getCurrentFocus().getId() == edUsername.getId()){
            edUsername.setText(edUsername.getText()+number);
        }else if (getActivity().getCurrentFocus().getId() == edPassword.getId()){
            edPassword.setText(edPassword.getText()+number);
        }*/
    }

    @SuppressLint("StaticFieldLeak")
    private void onLogin() {
        if (edUsername.length() > 0 && edPassword.length() > 0) {
            final Onclicklogin onclicklogin = new Onclicklogin();
            new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    avloadLogin.setVisibility(View.VISIBLE);
                }

                @Override
                protected String doInBackground(String... strings) {
                    ArrayList<FromHttpPostOkHttp> params_login = new ArrayList<FromHttpPostOkHttp>();
                    if (!getUserFormat(2).equals("")){
                        params_login.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("server", getUserFormat(2)));
                    }else {
                        params_login.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("server", "zx"));
                    }
                    return allCommand.POST_OK_HTTP_SendData("MyUrl.php", params_login);
                }

                @SuppressLint("MissingPermission")
                @Override
                protected void onPostExecute(String s) {
                    try {
                        allCommand.ShowLogCat("*** url ***", s);
                        final JSONObject jObject;
                        jObject = new JSONObject(allCommand.CoverStringFromServer_One(s));
                        strURLmo = jObject.getString("url");
                        img_url = jObject.getString("logo55");
                        allCommand.SaveStringShare(getContext(),allCommand.moURL,strURLmo);
                        setImageLogo(img_url);

                        tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                        uuid = tManager.getDeviceId().toString();

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
                                params.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("device ", "android"));
                                params.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("uuid ", uuid+""));
                                params.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("lang ", "th"));
                                return allCommand.POST_OK_HTTP_SendData(str_Url, params);
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                allCommand.ShowLogCat("*** Login ***", s.toString().trim());
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

                                        allCommand.SaveStringShare(getContext(),allCommand.molot_pay_big1,jOLogin.getString("lot_pay_big1"));
                                        allCommand.SaveStringShare(getContext(),allCommand.molot_pay_big2,jOLogin.getString("lot_pay_big2"));
                                        allCommand.SaveStringShare(getContext(),allCommand.molot_pay_big3,jOLogin.getString("lot_pay_big3"));
                                        allCommand.SaveStringShare(getContext(),allCommand.molot_pay_big4,jOLogin.getString("lot_pay_big4"));
                                        allCommand.SaveStringShare(getContext(),allCommand.molot_pay_big5,jOLogin.getString("lot_pay_big5"));

                                        if (allCommand.GetStringShare(getContext(),allCommand.Check_Languane,"").equals("")){

                                            //ภาษา
                                            allCommand.SaveStringShare(getContext(),allCommand.Check_Languane,"en");

                                        }

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
                                        allCommand.SaveStringShare(getContext(),allCommand.moSaveAdmin,"1234");

                                        edPassword.setText("");
                                        ModelBus modelBus = new ModelBus();
                                        modelBus.setPage(Utils.KEY_ADD_PAGE_TANG_LOT_FAST);
                                        modelBus.setMsg(Utils.NAME_ADD_PAGE_TANG_LOT_FAST);
                                        BusProvider.getInstance().post(modelBus);


                                    }else {
                                        allCommand.ShowAertDialog_OK(allCommand.GetStringShare(getContext(),allCommand.text_userincorrect,"Username or password is invalid."),getActivity());
                                    }
                                }catch (Exception e){
                                    allCommand.ShowLogCat("*** Err ***", "Err SetDataLogin " + e.getMessage());

                                }
                                avloadLogin.setVisibility(View.INVISIBLE);
                                onclicklogin.setTAG_KEY("onClick");
                                BusProvider.getInstance().post(onclicklogin);
                            }

                        }.execute();

                    } catch (Exception e) {
                        avloadLogin.setVisibility(View.INVISIBLE);
                        allCommand.ShowLogCat("*** Err ***", "Err SetDataGetUrl " + e.getMessage());
                        allCommand.ShowAertDialog_OK(allCommand.GetStringShare(getContext(),allCommand.text_userincorrect,"Username or password is invalid."),getActivity());
                        onclicklogin.setTAG_KEY("onClick");
                        BusProvider.getInstance().post(onclicklogin);
                    }
                }

            }.execute();
        }
    }
    private void setImageLogo(String img_url){
        Glide.with(this)
                .load(strURLmo+img_url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        im_logo_login.setImageBitmap(resource);
                        saveImage(resource);
                        //Log.e("PageLogin", saveImage(resource));

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



    public void onPermissionMultiple() {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        /*if (!addPermission(permissionsList, Manifest.permission.GET_ACCOUNTS))
            permissionsNeeded.add("Contacts");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Location");*/
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("Phone");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale

                String msg = "";
                for (int i = 0; i < permissionsNeeded.size(); i++){
                    msg += "\n" + permissionsList.get(i);
                }
                String alert1 = allCommand.GetStringShare(getContext(),allCommand.text_alert_premiss_i,"Access is required.");
                String alert2 = allCommand.GetStringShare(getContext(),allCommand.text_alert_premiss_ii,"For complete work");
                String message = alert1 +" " + msg + " " +alert2;
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getActivity(),permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_MUTIPLE);

                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(getActivity(),permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_MUTIPLE);
            return;
        }
        allowMultipleSuccess();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        //ตรวจเช็ค
        if (ActivityCompat.checkSelfPermission(getContext(),permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            //ขอ Permission
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permission))
                return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_MUTIPLE:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                /*perms.put(Manifest.permission.GET_ACCOUNTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);*/
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (/*perms.get(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        &&*/ perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    allowMultipleSuccess();
                } else {
                    // Permission Denied
                    //checkDataUrlPhoneNumber(1);
                    allCommand.ShowLogCat("status","Some Permission is Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener closeListener) {
        String ok = allCommand.GetStringShare(getContext(),allCommand.text_ok,"Ok");
        String exitApp = allCommand.GetStringShare(getContext(),allCommand.text_logout,"Exit the app");
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton(ok, okListener)
                .setNegativeButton(exitApp, closeListener)
                .create()
                .show();
    }
    private void allowMultipleSuccess(){
        allCommand.ShowLogCat("Status","อนุญาตหลายอย่างเสร็จสิ้น");
        //Call Thread Login
        onLogin();

    }
}

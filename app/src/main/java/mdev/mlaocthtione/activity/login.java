package mdev.mlaocthtione.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mdev.mlaocthtione.FormatHttpPostOkHttp.BasicNameValusPostOkHttp;
import mdev.mlaocthtione.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.R;

public class login extends AppCompatActivity implements View.OnClickListener{

    private TextView bg_Login,bt_Left;
    private EditText edUsername,edPassword;
    private AllCommand allCommand;
    private AVLoadingIndicatorView avloadLogin;
    private String strStatus = "",strURLmo = "",strUsername = " ",strPassword = " ";

    private TextView bt_zero, bt_nine, bt_eight,
            bt_seven, bt_six, bt_file, bt_four, bt_three, bt_two, bt_one;
    private TextView btn_edit;
    private TextInputLayout tilEdPassword,tilEdUsername;
    private ImageView im_logo_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        allCommand = new AllCommand();


        tilEdPassword = findViewById(R.id.tilEdPassword);
        tilEdUsername = findViewById(R.id.tilEdUsername);

        bg_Login = (TextView) findViewById(R.id.btn_login);
        bt_Left = findViewById(R.id.bt_Left);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        im_logo_login = findViewById(R.id.im_logo_login);
        edUsername.setText("aaaa01@zx");
        edPassword.setText("1111");
        edUsername.setKeyListener(null);
        edPassword.setKeyListener(null);

        avloadLogin = (AVLoadingIndicatorView) findViewById(R.id.avloadLogin);

        bg_Login.setOnClickListener(this);
        bt_Left.setOnClickListener(this);

        bt_zero = findViewById(R.id.bt_zero);
        bt_nine = findViewById(R.id.bt_nine);
        bt_eight = findViewById(R.id.bt_eight);
        bt_seven = findViewById(R.id.bt_seven);
        bt_six = findViewById(R.id.bt_six);
        bt_file = findViewById(R.id.bt_file);
        bt_four = findViewById(R.id.bt_four);
        bt_three = findViewById(R.id.bt_three);
        bt_two = findViewById(R.id.bt_two);
        bt_one = findViewById(R.id.bt_one);
        btn_edit = findViewById(R.id.btn_edit);

        btn_edit.setOnClickListener(this);

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

        tilEdUsername.setBackgroundResource(R.drawable.bg_ed_select);

        getURL();

    }

    @SuppressLint("StaticFieldLeak")
    private void onGetUrlBall() {
        if (edUsername.length() > 0 && edPassword.length() > 0) {

            try {
                /*final JSONObject jObject;
                jObject = new JSONObject(allCommand.CoverStringFromServer_One(s));
                strURLmo = jObject.getString("url");
                allCommand.SaveStringShare(login.this,allCommand.moURL,strURLmo);*/

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        avloadLogin.setVisibility(View.VISIBLE);
                        bg_Login.setEnabled(false);
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

                                allCommand.SaveStringShare(login.this,allCommand.moCradit,jOLogin.getString("MemberCradit"));
                                allCommand.SaveStringShare(login.this,allCommand.moMemberID,jOLogin.getString("MemberID"));
                                allCommand.SaveStringShare(login.this,allCommand.moName,jOLogin.getString("Name"));
                                ;
                                String max1 = jOLogin.getString("MemberMax").toString().trim();
                                String min1 = jOLogin.getString("MemberMin").toString().trim();
                                if (max1.toString().trim().length() <= 0){
                                    max1 = "1";
                                }
                                if (min1.toString().trim().length() <= 0){
                                    min1 = "1";
                                }

                                allCommand.SaveStringShare(login.this,allCommand.moTangMax,max1);

                                String max = jOLogin.getString("MemberMax").toString().trim();
                                String min = jOLogin.getString("MemberMin").toString().trim();
                                if (max.toString().trim().length() <= 0){
                                    max = "1";
                                }
                                if (min.toString().trim().length() <= 0){
                                    min = "1";
                                }
                                allCommand.SaveStringShare(login.this,allCommand.moTangMax,max);
                                allCommand.SaveStringShare(login.this,allCommand.moTangMin,min);

                                Intent gomain = new Intent(login.this
                                        .getApplicationContext(),MainActivity.class);
                                startActivity(gomain);
                                login.this.finish();
                            }else {
                                allCommand.ShowAertDialog_OK("ไม่พบชื่อผู้ใช้นี้",login.this);
                            }
                        }catch (Exception e){
                            allCommand.ShowLogCat("*** Err ***", "Err SetDataLogin " + e.getMessage());
                        }
                        avloadLogin.setVisibility(View.INVISIBLE);
                        bg_Login.setEnabled(true);
                    }

                }.execute();

            } catch (Exception e) {
                avloadLogin.setVisibility(View.INVISIBLE);
                bg_Login.setEnabled(true);
                allCommand.ShowLogCat("*** Err ***", "Err SetDataGetUrl " + e.getMessage());
            }

            /*new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    avloadLogin.setVisibility(View.VISIBLE);
                    bg_Login.setEnabled(false);
                }

                @Override
                protected String doInBackground(String... strings) {
                    ArrayList<FromHttpPostOkHttp> params_login = new ArrayList<FromHttpPostOkHttp>();
                    params_login.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("server",
                            getUserFormat(2)));
                    return allCommand.POST_OK_HTTP_SendData("MY_URL", params_login);
                }

                @Override
                protected void onPostExecute(String s) {
                    allCommand.ShowLogCat("*** url ***", s);

                    try {
                        final JSONObject jObject;
                        jObject = new JSONObject(allCommand.CoverStringFromServer_One(s));
                        strURLmo = jObject.getString("url");
                        allCommand.SaveStringShare(login.this,allCommand.moURL,strURLmo);

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

                                        allCommand.SaveStringShare(login.this,allCommand.moCradit,jOLogin.getString("MemberCradit"));
                                        allCommand.SaveStringShare(login.this,allCommand.moMemberID,jOLogin.getString("MemberID"));
                                        allCommand.SaveStringShare(login.this,allCommand.moName,jOLogin.getString("Name"));
                                        allCommand.SaveStringShare(login.this, Utils.SHARE_BARCODE,jObject.getString("Barcode"));

                                        String max1 = jOLogin.getString("MemberMax").toString().trim();
                                        String min1 = jOLogin.getString("MemberMin").toString().trim();
                                        if (max1.toString().trim().length() <= 0){
                                            max1 = "1";
                                        }
                                        if (min1.toString().trim().length() <= 0){
                                            min1 = "1";
                                        }

                                        allCommand.SaveStringShare(login.this,allCommand.moTangMax,max1);

                                        String max = jOLogin.getString("MemberMax").toString().trim();
                                        String min = jOLogin.getString("MemberMin").toString().trim();
                                        if (max.toString().trim().length() <= 0){
                                            max = "1";
                                        }
                                        if (min.toString().trim().length() <= 0){
                                            min = "1";
                                        }
                                        allCommand.SaveStringShare(login.this,allCommand.moTangMax,max);
                                        allCommand.SaveStringShare(login.this,allCommand.moTangMin,min);

                                        Intent gomain = new Intent(login.this
                                                .getApplicationContext(),MainActivity.class);
                                        startActivity(gomain);
                                        login.this.finish();
                                    }else {
                                        allCommand.ShowAertDialog_OK("ไม่พบชื่อผู้ใช้นี้",login.this);
                                    }
                                }catch (Exception e){
                                    allCommand.ShowLogCat("*** Err ***", "Err SetDataLogin " + e.getMessage());
                                }
                                avloadLogin.setVisibility(View.INVISIBLE);
                                bg_Login.setEnabled(true);
                            }

                        }.execute();

                    } catch (Exception e) {
                        avloadLogin.setVisibility(View.INVISIBLE);
                        bg_Login.setEnabled(true);
                        allCommand.ShowLogCat("*** Err ***", "Err SetDataGetUrl " + e.getMessage());
                    }
                }

            }.execute();*/
        }
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

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_login:

                if (edUsername.length() > 0 && edUsername.length() > 0){
                    onGetUrlBall();
                }else {
                    allCommand.ShowAertDialog_OK("กรอก" + getResources().getString(R.string.title_hint_user) + "และ" +
                            getResources().getString(R.string.title_hint_pass),login.this);
                }

                break;
            case R.id.bt_Left:

                setbg_edtext();

                break;

            case R.id.btn_edit:

                int lengthuser = edUsername.getText().length();
                int lengthpass = edPassword.getText().length();

                if (this.getCurrentFocus().getId() == edUsername.getId()){
                    edUsername.getText().delete(lengthuser - 1, lengthuser);

                }else if (this.getCurrentFocus().getId() == edPassword.getId()){
                    edPassword.getText().delete(lengthpass - 1, lengthpass);
                }

                break;

            case R.id.bt_one:
                setNumber("1");
                break;
            case R.id.bt_two:
                setNumber("2");
                break;
            case R.id.bt_three:
                setNumber("3");
                break;
            case R.id.bt_four:
                setNumber("4");
                break;
            case R.id.bt_file:
                setNumber("5");
                break;
            case R.id.bt_six:
                setNumber("6");
                break;
            case R.id.bt_seven:
                setNumber("7");
                break;
            case R.id.bt_eight:
                setNumber("8");
                break;
            case R.id.bt_nine:
                setNumber("9");
                break;
            case R.id.bt_zero:
                setNumber("0");
                break;
        }
    }
    private void setNumber(String number){
        if (this.getCurrentFocus().getId() == edUsername.getId()){
            edUsername.setText(edUsername.getText()+number);
        }else if (this.getCurrentFocus().getId() == edPassword.getId()){
            edPassword.setText(edPassword.getText()+number);
        }
    }
    private void setbg_edtext(){
        if (this.getCurrentFocus().getId() == edUsername.getId()){
            edPassword.requestFocus();
            tilEdPassword.setBackgroundResource(R.drawable.bg_ed_select);
            tilEdUsername.setBackgroundResource(R.drawable.bg_ed_pass);
        }else if (this.getCurrentFocus().getId() == edPassword.getId()){
            edUsername.requestFocus();
            tilEdUsername.setBackgroundResource(R.drawable.bg_ed_select);
            tilEdPassword.setBackgroundResource(R.drawable.bg_ed_pass);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void getURL(){

        if (allCommand.isConnectingToInternet(login.this)){

            new AsyncTask<String, Void, String>() {

                @Override
                protected String doInBackground(String... strings) {
                    ArrayList<FromHttpPostOkHttp> params_login = new ArrayList<FromHttpPostOkHttp>();
                    params_login.add(new BasicNameValusPostOkHttp().BasicNameValusPostOkHttp("server",
                            getUserFormat(2)));
                    return allCommand.POST_OK_HTTP_SendData("MY_URL", params_login);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    allCommand.ShowLogCat("*** url ***", s);
                    final JSONObject jObject;
                    try {
                        jObject = new JSONObject(allCommand.CoverStringFromServer_One(s));
                        strURLmo = jObject.getString("url");
                        allCommand.SaveStringShare(login.this,allCommand.moURL,strURLmo);
                        setImageLogo();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }.execute();
        }

    }
    private void setImageLogo(){
        Log.e("login", "Welcome");
        Glide.with(login.this)
                .load(strURLmo+"img/logo99.png")
                .into(im_logo_login);

    }
}

package mdev.mlaocthtione.Manager;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mdev.mlaocthtione.BuildConfig;
import mdev.mlaocthtione.FormatHttpPostOkHttp.FromHttpPostOkHttp;
import mdev.mlaocthtione.R;


public class AllCommand {

	public static String SHARE_NAME = "SAVE_LOGIN";
	public static String moMemberID = "moMemberID";
	public static String moCradit = "moCradit";
	public static String moName = "moName";
	public static String moTangMax = "moTangMax";
	public static String moTangMin = "moTangMin";
	public static String moURL = "moURL";

	public static String moSaveUser = "moSaveUser";
	public static String moSavePass = "moSavePass";
	public static String moCloseBig = "moCloseBig";
	public static String moCloseSmall = "moCloseSmall";
	public static String molot_pay_big1 = "molot_pay_big1";
	public static String molot_pay_big2 = "molot_pay_big2";
	public static String molot_pay_big3 = "molot_pay_big3";
	public static String molot_pay_big4 = "molot_pay_big4";
	public static String molot_pay_big5 = "molot_pay_big5";

	public static String moSaveAdmin = "moSaveAdmin";
	public static String moStatuspage = "moStatuspage";

	public static String moUUID = "moUUID";

	public static String text_ok = "text_ok";//ตกลง
	public static String text_logout = "text_logout";//ออกจากระบบ
	public static String text_returns = "text_returns";//กลับ
	public static String text_inputNumber = "text_inputNumber";//กรุณากรอก
	public static String text_Top = "text_Top";//บน
	public static String text_lower = "text_lower";//ล่าง
	public static String text_Toad = "text_Toad";//โต๊ด
	public static String text_printer = "text_printer";//เครื่องพิมพ์
	public static String text_edit = "text_edit";//แก้ไข
	public static String text_cancel = "text_cancel";//ยกเลิก
	public static String text_confirm = "text_confirm";//ส่ง-พิมพ์
	public static String text_numberFull = "text_numberFull";//เลขเต็ม
	public static String text_admin = "text_admin";//กรอกรหัสยืนยันตัวตน
	public static String text_yes = "text_yes";//ใช่
	public static String text_no = "text_no";//ไม่ใช่
	public static String text_user = "text_user";//ชื่อผู้ใช้
	public static String text_pass = "text_pass";//รหัสผ่าน
	public static String text_login = "text_login";//เข้าสู่ระบบ
	public static String text_Number = "text_Number";//เลข
	public static String text_Next = "text_Next";//ถัดไป
	public static String text_Languane = "text_Languane";//ภาษา
	public static String text_close_bet = "text_close_bet";//ปิดรับแทงแล้ว
	public static String text_save_success = "text_save_success";//บันทึกเสร็จสิ้น
	public static String text_connecting_printer = "text_connecting_printer";//กำลังเชื่อมต่อเครื่องพิมพ์...
	public static String text_connected_printer = "text_connected_printer";//เชื่อมต่อเครื่องพิมพ์แล้ว
	public static String text_no_connect_printer = "text_no_connect_printer";//ไม่ได้เชื่อมต่อเครื่องพิมพ์
	public static String text_search = "text_search";//ค้นหา
	public static String text_paper = "text_paper";//กระดาษ :
	public static String text_paper_3 = "text_paper_3";//3 นิ้ว
	public static String text_paper_2 = "text_paper_2";//2 นิ้ว
	public static String text_searching = "text_searching";//กำลังค้นหา...
	public static String text_list_device = "text_list_device";//รายชื่ออุปกรณ์
	public static String text_device_connected = "text_device_connected";//อุปกรณ์ที่เชื่อมต่อ
	public static String text_close_printer = "text_close_printer";//ปิดเครื่องพิมพ์
	public static String text_status_printing = "text_status_printing";//กำลังพิมพ์...
	public static String text_alert_input_data = "text_alert_input_data";//กรุณากรอกข้อมูล
	public static String text_alert_premiss_i = "text_alert_premiss_i";//ต้องใช้สิทธิ์การเข้าถึง
	public static String text_alert_premiss_ii = "text_alert_premiss_ii";//เพื่อการทำงานที่สมบูรณ์
	public static String text_no_internet = "text_no_internet";//กรุณาเชื่อมต่ออินเตอร์เน็ต
	public static String text_userincorrect = "text_userincorrect";//ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง
	public static String text_loading = "text_loading";//กำลังโหลด...
	public static String text_fail = "text_fail";//ไม่สำเร็จ
	public static String Check_Languane = "Check_Languane";


	public boolean isConnectingToInternet(Context _context) {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
	public String GET_OK_HTTP_SendData(String url) {
		ShowLogCat("GET_OK_HTTP_SendData",url);
		try{
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url(url).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		}catch (Exception e){
			ShowLogCat("Err","GET_OK_HTTP_SendData " + e.getMessage());
			return "";
		}
	}
	public String POST_OK_HTTP_SendData(String url, ArrayList<FromHttpPostOkHttp> params) {
		ShowLogCat("POST_OK_HTTP_SendData",url);
		try{
			OkHttpClient client = new OkHttpClient();
			MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
			for (int i = 0;i<params.size();i++){
				Log.e("Check Data",params.get(i).getKEY_POST().toString()+ " : " +params.get(i).getVALUS_POST().toString());
				multipartBuilder.addFormDataPart(params.get(i).getKEY_POST().toString(),params.get(i).getVALUS_POST().toString());
			}
			RequestBody requestBody = multipartBuilder.build();
			Request request = new Request.Builder()
					.url(url)
					.post(requestBody)
					.build();

			Response response = client.newCall(request).execute();
			if (response.isSuccessful()){
				return response.body().string().toString();
			}
		}catch (Exception e){
			Log.e("*** Err ***","Err POST_OK_HTTP_SendData " + e.getMessage());
			return "";
		}
		return "";
	}


	@TargetApi(Build.VERSION_CODES.KITKAT)
	public String setEncodeBase64(final String input) {
		try {
			byte[] message = input.getBytes(StandardCharsets.UTF_8);
			return  Base64.encodeToString(message, Base64.DEFAULT);
		}catch (Exception e){
			ShowLogCat("Err","setEncodeBase64 " + e.getMessage());
		}
		return "";
	}
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public String getEncodeBase64(final String encodeBase64) {
		try {
			byte[] decoded = Base64.decode(encodeBase64, Base64.DEFAULT);
			return new String(decoded, StandardCharsets.UTF_8);
		}catch (Exception e){
			ShowLogCat("Err","getEncodeBase64 " + e.getMessage());

		}
		return "";
	}
	public String GetStringShare(Context _context, String strKey, String strDe) {
		SharedPreferences shLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		if (shLang != null) {
			String strShare = shLang.getString(strKey, strDe);
			return strShare;
		}
		return "";
	}
	public void SaveStringShare(Context _context, String strKey, String strDe){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
		edShLang.putString(strKey, strDe);
		edShLang.commit();
	}

	public void RemvoeStringShare(Context _context, String strKey, String strDe){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
	}
	public void ShowLogCat(String tag, String msg){
		if (BuildConfig.DEBUG){
			Log.e("***AllCommand***",tag + " : " + msg);
		}
	}
	public String CoverStringFromServer_One(String strData){
		try {
			return  strData.substring(strData.indexOf("{"), strData.lastIndexOf("}") + 1);
		} catch (Exception e) {
			return "";
		}
	}
	public void ShowAertDialog_OK(String strMsg, final Context _context){
		int sizeMsgDialog = Integer.parseInt(_context.getResources().getString(R.string.size_dialog_ok));
		AlertDialog.Builder builder = new AlertDialog.Builder(_context);
		TextView myMsg = new TextView(_context);
		myMsg.setText("\n" + strMsg + "\n");
		myMsg.setTextSize(sizeMsgDialog);
		myMsg.setGravity(Gravity.CENTER);
		myMsg.setTypeface(null, Typeface.BOLD);
		builder.setView(myMsg);
		builder.setCancelable(true);
		builder.setPositiveButton(GetStringShare(_context,text_ok,"Ok"),null);
		final AlertDialog alertdialog = builder.create();
		alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface arg0) {
				int iPadBT = Integer.parseInt(_context.getResources().getString(R.string.size_padding_tb_dialog));
				int iSizeButton = Integer.parseInt(_context.getResources().getString(R.string.size_dialog_button));
				Button btnOk = alertdialog.getButton(Dialog.BUTTON_POSITIVE);
				btnOk.setTextSize(iSizeButton);
				btnOk.setPadding(1, iPadBT, 1, iPadBT);
				btnOk.setTypeface(null, Typeface.BOLD);
			}
		});
		alertdialog.show();
	}
	public int getIntShare(Context _context, String strKey, int strDe) {
		SharedPreferences shLang;
		shLang = _context
				.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		if (shLang != null) {
			int strShare = shLang.getInt(strKey, strDe);
			return strShare;
		}
		return 0;
	}
	public void saveIntShare(Context _context, String strKey, int strDe){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
		edShLang.putInt(strKey, strDe);
		edShLang.commit();
	}
	public void deleteShareData(Context _context, String strKey){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
	}

	public String SetDatestamp(String Stamp){

		long timestamp = Long.parseLong(Stamp) * 1000L;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date netDate = (new Date(timestamp));
			return sdf.format(netDate);
		}
		catch(Exception ex){
			return "xx";
		}
	}
	public String SetDateFoment(Date date){

		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format(date);
		}
		catch(Exception ex){
			return "xx";
		}
	}

	public String DeleteFormatNumber(String strData){
		String strDataNum = new String(strData);
		String strNum = strDataNum.replace(",", "");
		return strNum;
	}


}

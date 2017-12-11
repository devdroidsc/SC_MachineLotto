package mdev.mlaocthtione.con_bt;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.UUID;

/**
 * P25 printer connection class.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *
 */
public class P25Connector {
	private BluetoothSocket mSocket;
	private OutputStream mOutputStream;
	private ConnectTask mConnectTask;
	private P25ConnectionListener mListener;
	private boolean mIsConnecting = false;
	private static final String TAG = "P25";
	private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	private static final byte LF = 0x0a;
	private static final byte CR = 0x0D;
	private static final byte ESC = 0x1b;
	private static final byte[] CMD_INIT_PRT = { ESC, 0x40 }; // Initialize
	public P25Connector(){}
	public P25Connector(P25ConnectionListener listener) {
		mListener = listener;
	}
	public boolean isConnecting() {
		return mIsConnecting;
	}
	public boolean isConnected() {
		return (mSocket == null) ? false : true;
	}

	public void connect(BluetoothDevice device) throws P25ConnectionException {
		if (mIsConnecting && mConnectTask != null) {
			throw new P25ConnectionException("Connection in progress");
		}
		
		if (mSocket != null) {
			throw new P25ConnectionException("Socket already connected");
		}
		
		(mConnectTask = new ConnectTask(device)).execute();
	}
	public void disconnect() throws P25ConnectionException {
		if (mSocket == null) {
			throw new P25ConnectionException("Socket is not connected");
		}
		
		try {
			mSocket.close();
			
			mSocket = null;
			mListener.onDisconnected();
		} catch (IOException e) {
			throw new P25ConnectionException(e.getMessage());
		}
	}
	public void cancel() throws P25ConnectionException {
		if (mIsConnecting && mConnectTask != null) {
			mConnectTask.cancel(true);
		} else {
			throw new P25ConnectionException("No connection is in progress");
		}
	}
	
	/*public void sendData(byte[] msg) throws P25ConnectionException {
		if (mSocket == null) {
			throw new P25ConnectionException("Socket is not connected, try to call connect() first");
		}
			
		try {
			mOutputStream.write(msg);
			mOutputStream.flush();

			Log.i(TAG, StringUtil.byteToString(msg));
		} catch(Exception e) {
			throw new P25ConnectionException(e.getMessage());
		}
	}*/

	private void sendData(byte[] data) {
		try {
			//OutputStream os = mBluetoothSocket.getOutputStream();
			mOutputStream.write(data);
			mOutputStream.flush();
		} catch (Exception e) {
			Log.e("***Err***","Err SendData " + e.getMessage());
		}
	}
	public interface P25ConnectionListener {
		public abstract void onStartConnecting();
		public abstract void onConnectionCancelled();
		public abstract void onConnectionSuccess();
		public abstract void onConnectionFailed(String error);
		public abstract void onDisconnected();
	}
	public class ConnectTask extends AsyncTask<URL, Integer, Long> {
		BluetoothDevice device;
		String error = "";
		
		public ConnectTask(BluetoothDevice device) {
			this.device = device;
		}
		
		protected void onCancelled() {
			mIsConnecting = false;
			mListener.onConnectionCancelled();
		}
		
    	protected void onPreExecute() {
    		mListener.onStartConnecting();
    		
    		mIsConnecting = true;
    	}
    
        protected Long doInBackground(URL... urls) {
            long result = 0;
            
            try {
            	mSocket	= device.createRfcommSocketToServiceRecord(UUID.fromString(SPP_UUID));
            	
            	mSocket.connect(); 
            	
            	mOutputStream	= mSocket.getOutputStream();
            	
            	result = 1;
            } catch (IOException e) {
            	e.printStackTrace();
            	
            	error = e.getMessage();
            }
            
            return result;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        	mIsConnecting = false;
        	if (mSocket != null && result == 1) {
        		mListener.onConnectionSuccess();
        	} else {
        		mListener.onConnectionFailed("Connection failed " + error);
        	}
        }
    }
	public void PrintImage(String STR_FOLDER, String STR_ID) {
		try {
			//OutputStream os = mBluetoothSocket.getOutputStream();

			File Folder = new File(Environment.getExternalStorageDirectory(),STR_FOLDER);
			Folder.mkdirs();
			Log.e("Folder", Folder + "");
			String path = Folder + "/bill"+STR_ID+".png";
			Log.e("path", path + "");

			byte[] sendData = null;
			PrintPic pg = new PrintPic();
			//pg.initCanvas(384,path);
			pg.initCanvas(path);
			pg.initPaint();

			pg.drawImage(0, 0, path);
			sendData = pg.printDraw();
			mOutputStream.write(sendData);
		} catch (Exception e) {
			Log.e("***Err***","Err Print Image " + e.getMessage());
		}
	}
	public void PrintImage(String STR_FOLDER, String STR_ID, String barcode) {
		try {
			//OutputStream os = mBluetoothSocket.getOutputStream();
			File Folder = new File(Environment.getExternalStorageDirectory(),STR_FOLDER);
			Folder.mkdirs();
			Log.e("Folder", Folder + "");
			String path = Folder + "/bill"+STR_ID+".png";
			Log.e("path", path + "");

			byte[] sendData = null;
			PrintPic pg = new PrintPic();
			//pg.initCanvas(384,path);
			pg.initCanvas(path);
			pg.initPaint();

			pg.drawImage(0, 0, path);
			sendData = pg.printDraw();
			mOutputStream.write(sendData);

			if (barcode.toString().trim().equals("1")){
				Print1DBarcode(STR_ID);
			}
			PrintEnter();
		} catch (Exception e) {
			Log.e("***Err***","Err Print Image " + e.getMessage());
		}
	}
	public void PrintEnter(String strID) {// ภาษาไทยออก สระตก
		byte[] cmd_init_prt = { 0x1b, 0x40 };
		byte[] cmd_code_table = { 0x1b, 0x74, 96 }; // Select Thai code
		// table[TIS-620]
		byte[] cmd_code_setleftmargin = { 0x1b, 0x6c,32 }; // Select Thai code
		String strEnter = strID + "\n\n\n";
		ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
		buffer.append(cmd_init_prt, 0, cmd_init_prt.length);
		buffer.append(cmd_code_table, 0, cmd_code_table.length);
		if (strID.length() == 1) {
			for (int i = 0; i < 15; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 2) {
			for (int i = 0; i < 15; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 3) {
			for (int i = 0; i < 14; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 4) {
			for (int i = 0; i < 13; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 5) {
			for (int i = 0; i < 13; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 6) {
			for (int i = 0; i < 12; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 7) {
			for (int i = 0; i < 12; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 8) {
			for (int i = 0; i < 11; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 9) {
			for (int i = 0; i < 11; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 10) {
			for (int i = 0; i < 10; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}
		try {
			buffer.append(strEnter.getBytes("x-iso-8859-11"), 0,
					strEnter.getBytes("x-iso-8859-11").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		buffer.append(LF);
		sendData(buffer.toByteArray());
	}
	public void PrintEnter() {// ภาษาไทยออก สระตก
		byte[] cmd_init_prt = { 0x1b, 0x40 };
		byte[] cmd_code_table = { 0x1b, 0x74, 96 }; // Select Thai code
		// table[TIS-620]
		byte[] cmd_code_setleftmargin = { 0x1b, 0x6c,32 }; // Select Thai code
		String strEnter = "\n\n\n";
		ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
		buffer.append(cmd_init_prt, 0, cmd_init_prt.length);
		buffer.append(cmd_code_table, 0, cmd_code_table.length);
		try {
			buffer.append(strEnter.getBytes("x-iso-8859-11"), 0,
					strEnter.getBytes("x-iso-8859-11").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		buffer.append(LF);
		sendData(buffer.toByteArray());
	}
	public void Print1DBarcode(String strID) {//บาร์โค้ดออก title แล้วตามด้วย 01234567890
		byte[] cmd_code_setbottommargin = { 0x1b, 0x4e,0}; // Select Thai code
		byte[] cmd_code_setleftmargin = { 0x1b, 0x6c,32 }; // Select Thai code
		final byte[] barcode = new byte[strID.length()];
		for (int i = 0; i < barcode.length; i++) {
			String getChar = Character.toString(strID.charAt(i));
			if (getChar.toString().equals("0")) {
				barcode[i] = 0x30;
			}else if (getChar.toString().equals("1")) {
				barcode[i] = 0x31;
			}else if (getChar.toString().equals("2")) {
				barcode[i] = 0x32;
			}else if (getChar.toString().equals("3")) {
				barcode[i] = 0x33;
			}else if (getChar.toString().equals("4")) {
				barcode[i] = 0x34;
			}else if (getChar.toString().equals("5")) {
				barcode[i] = 0x35;
			}else if (getChar.toString().equals("6")) {
				barcode[i] = 0x36;
			}else if (getChar.toString().equals("7")) {
				barcode[i] = 0x37;
			}else if (getChar.toString().equals("8")) {
				barcode[i] = 0x38;
			}else if (getChar.toString().equals("9")) {
				barcode[i] = 0x39;
			}
		}
		ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
		byte[] CODE39 = PrintBarcode.createBarcode(PrintBarcode.CODE39, 2,
				60, true, barcode);
		if (strID.length() == 1) {
			for (int i = 0; i < 12; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 2) {
			for (int i = 0; i < 11; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 3) {
			for (int i = 0; i < 9; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 4) {
			for (int i = 0; i < 8; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 5) {
			for (int i = 0; i < 7; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 6) {
			for (int i = 0; i < 6; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 7) {
			for (int i = 0; i < 5; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 8) {
			for (int i = 0; i < 4; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 9) {
			for (int i = 0; i < 2; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}else if (strID.length() == 10) {
			for (int i = 0; i < 1; i++) {
				buffer.append(cmd_code_setleftmargin, 0, cmd_code_setleftmargin.length);
			}
		}
		//buffer.append(CR);
		buffer.append(CODE39, 0, CODE39.length);
		buffer.append(cmd_code_setbottommargin,0,cmd_code_setbottommargin.length);
		sendData(CMD_INIT_PRT);
		sendData(buffer.toByteArray());
	}
	public void PrintThai1(String strName, String strID) {
		String str1 = "";
		byte[] cmd_init_prt = { 0x1b, 0x40 };
		byte[] cmd_code_table = { 0x1b, 0x74, 96 }; // Select Thai code  table[TIS-620]
		str1 = "\n------------------------------- \n";
		str1 = str1 +  "Printer Name : " + strName + "\n";
		str1 = str1 + "Printer ID   : " + strID + "\n";
		str1 = str1 + "-------------------------------\n\n\n";
		ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
		buffer.append(cmd_init_prt, 0, cmd_init_prt.length);
		buffer.append(cmd_code_table, 0, cmd_code_table.length);
		try {
			buffer.append(str1.getBytes("x-iso-8859-11"), 0,
					str1.getBytes("x-iso-8859-11").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		buffer.append(LF);
		sendData(buffer.toByteArray());
	}
}
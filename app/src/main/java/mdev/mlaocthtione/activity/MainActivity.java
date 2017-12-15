package mdev.mlaocthtione.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.bus.ActivityResultBus;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.bus.ActivityResultEvent;
import com.squareup.otto.Subscribe;

import mdev.mlaocthtione.BuildConfig;
import mdev.mlaocthtione.Fragment.Keyboardlogin;
import mdev.mlaocthtione.Fragment.Keyboardmain;
import mdev.mlaocthtione.Fragment.Keyboardprint;
import mdev.mlaocthtione.Fragment.PageDetail;
import mdev.mlaocthtione.Fragment.PageLogin;
import mdev.mlaocthtione.Fragment.PageMain;
import mdev.mlaocthtione.Fragment.PrinterLotFragment;
import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.Model.ModelStatusConnectPrinter;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.bus.BusProvider;
import mdev.mlaocthtione.bus.ModelBus;
import mdev.mlaocthtione.con_bt.InstanceVariable;
import mdev.mlaocthtione.con_bt.P25Connector;
import mdev.mlaocthtione.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private AllCommand allCommand;
    private String TAG_PAGEMAIN = "TAGPAGEMAIN",TAG_PAGEDETAIL = "TAGPAGEDETAIL",TAG_PAGEPRINTER = "TAGPAGEPRINTER"
                    ,TAG_PAGELOGIN = "TAGPAGELOGIN";
    private String TAG_KEYBOARDLOGIN = "TAGKEYBOARDLOGIN",TAG_KEYBOARDMAIN = "TAGKEYBOARDMAIN",TAG_KEYBOARDPRINTER = "TAGKEYBOARDPRINTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allCommand = new AllCommand();
        BusProvider.getInstance().register(this);
        if (savedInstanceState == null){
            addFragmentFirst();
            addKeyboard();
        }
        AutoConnectPrinter();
    }
    private void addFragmentFirst() {

        PageMain pageMain = PageMain.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutPageMain, pageMain,TAG_PAGEMAIN)
                .detach(pageMain)
                .commit();

        PrinterLotFragment printerLotFragment = PrinterLotFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutPageMain, printerLotFragment,TAG_PAGEPRINTER)
                .detach(printerLotFragment)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutPageMain, PageLogin.newInstance(),TAG_PAGELOGIN)
                .commit();
    }


    private void addKeyboard(){

        Keyboardmain keyboardmain = Keyboardmain.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutKeyboad, keyboardmain,TAG_KEYBOARDMAIN)
                .detach(keyboardmain)
                .commit();

        Keyboardprint keyboardprint = Keyboardprint.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutKeyboad, keyboardprint,TAG_KEYBOARDPRINTER)
                .detach(keyboardprint)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutKeyboad, Keyboardlogin.newInstance(),TAG_KEYBOARDLOGIN)
                .commit();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
        if (InstanceVariable.mConnector != null) {
            try {
                InstanceVariable.mConnector.disconnect();
                InstanceVariable.mConnector = null;
            } catch (Exception e) {
                onShowLogCat("***Err***", "OnDestroy" + e.getMessage());
            }
        }
        try{
            if (InstanceVariable.mBluetoothAdapter != null){
                if (InstanceVariable.mBluetoothAdapter.isDiscovering()){
                    InstanceVariable.mBluetoothAdapter.cancelDiscovery();
                }
            }
        }catch (Exception e){
            onShowLogCat("***Err***", "OnDestroy clear BT " + e.getMessage());
        }
    }

    @Subscribe
    public void onStanByEventBus(ModelBus modelBus){
        if (modelBus != null){
            onShowLogCat("EventBus",modelBus.getMsg() + " : " + modelBus.getPage());

            if (modelBus.getPage() == Utils.KEY_ADD_PAGE_PRINTER){
                Fragment fragmentAttach = (PrinterLotFragment)
                        getSupportFragmentManager().findFragmentByTag(TAG_PAGEPRINTER);

                if (!onFragmentPage().equals(fragmentAttach)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(fragmentAttach)
                            .detach(onFragmentPage())
                            .commit();
                }

                Fragment keyboardmain = (Keyboardprint)
                        getSupportFragmentManager().findFragmentByTag(TAG_KEYBOARDPRINTER);

                if (!onFragmentPage().equals(keyboardmain)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(keyboardmain)
                            .detach(onFragmentKeyBoad())
                            .commit();
                }


            }else if (modelBus.getPage() == Utils.KEY_ADD_PAGE_TANG_LOT_FAST){

                Fragment fragmentAttach = (PageMain)
                        getSupportFragmentManager().findFragmentByTag(TAG_PAGEMAIN);

                if (!onFragmentPage().equals(fragmentAttach)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(fragmentAttach)
                            .detach(onFragmentPage())
                            .commit();
                }

                Fragment keyboardmain = (Keyboardmain)
                        getSupportFragmentManager().findFragmentByTag(TAG_KEYBOARDMAIN);

                if (!onFragmentPage().equals(keyboardmain)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(keyboardmain)
                            .detach(onFragmentKeyBoad())
                            .commit();
                }


            }else if (modelBus.getPage() == Utils.KEY_ADD_PAGE_LOGIN){

                Fragment fragmentAttach = (PageLogin)
                        getSupportFragmentManager().findFragmentByTag(TAG_PAGELOGIN);

                if (!onFragmentPage().equals(fragmentAttach)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(fragmentAttach)
                            .detach(onFragmentPage())
                            .commit();
                }

                Fragment keyboardlogin = (Keyboardlogin)
                        getSupportFragmentManager().findFragmentByTag(TAG_KEYBOARDLOGIN);

                if (!onFragmentPage().equals(keyboardlogin)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(keyboardlogin)
                            .detach(onFragmentKeyBoad())
                            .commit();
                }


            }


        }


    }
    /*private void onManagerFragment(String tag){
        Fragment fragmentAttach = null;//นำเข้า

        if (tag.toString().trim().equals(TAG_PAGEMAIN)){
            fragmentAttach = (PageMain)
                    getSupportFragmentManager().findFragmentByTag(TAG_PAGEMAIN);

        }else if (tag.toString().trim().equals(TAG_PAGEDETAIL)){
            fragmentAttach = (PageDetail) getSupportFragmentManager().findFragmentByTag(TAG_PAGEDETAIL);

        }

        if (!onFragmentPage().equals(fragmentAttach)){
            getSupportFragmentManager().beginTransaction()
                    .attach(fragmentAttach)
                    .detach(onFragmentPage())
                    .commit();
        }

    }*/
    private Fragment onFragmentPage() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.frameLayoutPageMain);
        if (f != null) {
            return f;
        }
        return null;
    }
    private Fragment onFragmentKeyBoad() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.frameLayoutKeyboad);
        if (f != null) {
            return f;
        }
        return null;
    }


    private void AutoConnectPrinter(){
        InstanceVariable.mConnector = new P25Connector(new P25Connector.P25ConnectionListener() {
            @Override
            public void onStartConnecting() {
                allCommand.saveIntShare(MainActivity.this, Utils.SHARE_STATUS_CON_BT,0);//กำลังเชื่อมต่อ
                onSetStatusPrinter(0,getResources().getString(R.string.txt_connecting_printer), Color.BLACK,false);
            }

            @Override
            public void onConnectionCancelled() {
                allCommand.saveIntShare(MainActivity.this,Utils.SHARE_STATUS_CON_BT,-1);//เชื่อมต่อผิดพลาด
                onSetStatusPrinter(-1,getResources().getString(R.string.txt_no_connect_printer) ,Color.RED,false);
            }

            @Override
            public void onConnectionSuccess() {
                allCommand.saveIntShare(MainActivity.this,Utils.SHARE_STATUS_CON_BT,1);//เชื่อมต่อสำเร็จ
                onSetStatusPrinter(1,getResources().getString(R.string.txt_connected_printer),Color.BLUE,true);
            }

            @Override
            public void onConnectionFailed(String error) {
                allCommand.saveIntShare(MainActivity.this,Utils.SHARE_STATUS_CON_BT,-1);//เชื่อมต่อผิดพลาด
                if (InstanceVariable.mConnector != null) {
                    try {
                        InstanceVariable.mConnector.disconnect();
                    } catch (Exception e) {
                        onShowLogCat("***Err***", "Err Stop Connect BT MainActivity " + e.getMessage());
                    }
                }

                onSetStatusPrinter(-1,getResources().getString(R.string.txt_no_connect_printer) ,Color.RED,false);
            }

            @Override
            public void onDisconnected() {
                allCommand.saveIntShare(MainActivity.this,Utils.SHARE_STATUS_CON_BT,-1);//เชื่อมต่อผิดพลาด
                onSetStatusPrinter(-1,getResources().getString(R.string.txt_no_connect_printer) ,Color.RED,false);
            }
        });
        allCommand.saveIntShare(MainActivity.this,Utils.SHARE_STATUS_CON_BT,-1);//เชื่อมต่อผิดพลาด
        onSetStatusPrinter(-1,getResources().getString(R.string.txt_no_connect_printer) ,Color.RED,false);

        String IP_PRINTER = allCommand.GetStringShare(MainActivity.this,Utils.SHARE_IP_PRINTER,"");
        if (IP_PRINTER.trim().length() > 0) {
            InstanceVariable.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (InstanceVariable.mBluetoothAdapter.isEnabled()) {
                try {
                    InstanceVariable.con_dev = InstanceVariable.mBluetoothAdapter.getRemoteDevice(IP_PRINTER.trim());
                    if (!InstanceVariable.mConnector.isConnected()) {
                        InstanceVariable.mConnector.connect(InstanceVariable.con_dev);
                    }
                }catch (Exception e){
                    onShowLogCat("***Err***","Err AutoConnect " +e.getMessage());
                }
            }else {
                Intent enableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, InstanceVariable.REQUEST_ENABLE_BT_MAIN);
            }
        }
    }
    private void onSetStatusPrinter(int status,String txt, int color,boolean conComplete) {
        onShowLogCat("StatusPrinter",status + "");
        ModelStatusConnectPrinter statusPrinter = new ModelStatusConnectPrinter();
        statusPrinter.setStatus(status);
        statusPrinter.setTextStatus(txt);
        statusPrinter.setColorText(color);
        statusPrinter.setConnectComplete(conComplete);
        BusProvider.getInstance().post(statusPrinter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == InstanceVariable.REQUEST_ENABLE_BT_MAIN) {
            if (resultCode == Activity.RESULT_OK) {
                String IP_PRINTER = allCommand.GetStringShare(MainActivity.this, Utils.SHARE_IP_PRINTER, "");
                if (IP_PRINTER.trim().length() > 0) {
                    try {
                        InstanceVariable.con_dev = InstanceVariable.mBluetoothAdapter.getRemoteDevice(IP_PRINTER.trim());
                        if (!InstanceVariable.mConnector.isConnected()) {
                            InstanceVariable.mConnector.connect(InstanceVariable.con_dev);
                        }
                    } catch (Exception e) {
                        onShowLogCat("***Err***", "Err onActivityResult TangActivity " + e.getMessage());
                    }
                }
            }else {
                allCommand.deleteShareData(MainActivity.this, Utils.SHARE_NAME_PRINTER);
                allCommand.deleteShareData(MainActivity.this, Utils.SHARE_IP_PRINTER);
            }
        } else {
            ActivityResultBus.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));
        }
    }
    private void onShowLogCat(String tag, String msg){
        if (BuildConfig.DEBUG){
            Log.e("*** MainActivity ***",tag +" : " + msg);
        }
    }
}

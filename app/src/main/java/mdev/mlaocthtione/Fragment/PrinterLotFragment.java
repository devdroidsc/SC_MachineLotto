package mdev.mlaocthtione.Fragment;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mdev.mlaocthtione.Adapter.CustomAdapterPrinter;
import mdev.mlaocthtione.BuildConfig;
import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.Model.ModelDataPrinter;
import mdev.mlaocthtione.Model.ModelStatusConnectPrinter;
import mdev.mlaocthtione.ModelBus.OnclickPrinter;
import mdev.mlaocthtione.R;
import mdev.mlaocthtione.bus.BusProvider;
import mdev.mlaocthtione.bus.ModelBus;
import mdev.mlaocthtione.con_bt.InstanceVariable;
import mdev.mlaocthtione.con_bt.P25ConnectionException;
import mdev.mlaocthtione.utils.Utils;


public class PrinterLotFragment extends StatedFragment implements View.OnClickListener{
    private AllCommand allCommand;
    private LinearLayout lnPaper2,lnPaper3;
    private ImageView imgPaper2,imgPaper3;
    private TextView tvPaper2,tvPaper3,tvTitleSea,tvSeaPrinter,tvClosePrinter,tvNamePrinterConnected;
    private ListView lvNamePrinter;
    private List<ModelDataPrinter> dataSet;
    private CustomAdapterPrinter customAdapterPrinter;
    private List<String> arrDeviceName;
    private Set<BluetoothDevice> pairedDevices;
    private boolean isFirstLoad;
    private boolean bCheckPrinter = false,bCheckPrintTest = false;
    private TextView txt_paper,text_diver_connect;

    public PrinterLotFragment() {}

    public static PrinterLotFragment newInstance() {
        PrinterLotFragment fragment = new PrinterLotFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstLoad = false;
        bCheckPrinter = false;
        bCheckPrintTest = false;
        dataSet = new ArrayList<>();
        arrDeviceName = new ArrayList<>();
        if (savedInstanceState != null){
            dataSet = savedInstanceState.getParcelableArrayList("dataSet");
            isFirstLoad = savedInstanceState.getBoolean("isFirstLoad");
            arrDeviceName = savedInstanceState.getStringArrayList("arrDeviceName");
        }
        BusProvider.getInstance().register(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFirstLoad",isFirstLoad);
        outState.putParcelableArrayList("dataSet", (ArrayList<? extends Parcelable>) dataSet);
        outState.putStringArrayList("arrDeviceName", (ArrayList<String>) arrDeviceName);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_printer, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onSettingPaper(allCommand.getIntShare(getActivity(), Utils.SHARE_SETTING_PAPER,2));

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mReceiverBT, filter);

        if (savedInstanceState != null){
            customAdapterPrinter = new CustomAdapterPrinter(getActivity(),dataSet);
            lvNamePrinter.setAdapter(customAdapterPrinter);
            customAdapterPrinter.notifyDataSetChanged();
        }else {
            if (!isFirstLoad){
                isFirstLoad = true;
                InstanceVariable.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (onCheckOpenBt()){
                    pairedDevices = InstanceVariable.mBluetoothAdapter.getBondedDevices();
                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            ModelDataPrinter printer = new ModelDataPrinter();
                            printer.setsName(device.getName());
                            printer.setsIp(device.getAddress());
                            dataSet.add(printer);
                            customAdapterPrinter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }

        int statusConBt = allCommand.getIntShare(getActivity(),Utils.SHARE_STATUS_CON_BT,0);
        if (statusConBt == 0){
            tvNamePrinterConnected.setText(allCommand.GetStringShare(getContext(),allCommand.text_connecting_printer,"Connecting printer..."));
            tvNamePrinterConnected.setTextColor(Color.BLACK);
            tvClosePrinter.setEnabled(false);
        }else if (statusConBt == 1){
            tvNamePrinterConnected.setText(allCommand.GetStringShare(getActivity(),Utils.SHARE_IP_PRINTER,""));
            tvNamePrinterConnected.setTextColor(Color.BLUE);
            tvClosePrinter.setEnabled(true);
        }else {
            tvNamePrinterConnected.setText(allCommand.GetStringShare(getContext(),allCommand.text_no_connect_printer,"Printer not connected."));
            tvNamePrinterConnected.setTextColor(Color.RED);
            tvClosePrinter.setEnabled(false);
        }

        if (InstanceVariable.mBluetoothAdapter != null){
            if (InstanceVariable.mBluetoothAdapter.isDiscovering()){
                tvTitleSea.setText(allCommand.GetStringShare(getContext(),allCommand.text_searching,"Searching..."));
                tvSeaPrinter.setEnabled(false);
            }
        }

        lvNamePrinter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bCheckPrintTest = true;
                if (InstanceVariable.mBluetoothAdapter != null){
                    if (InstanceVariable.mBluetoothAdapter.isDiscovering()){
                        InstanceVariable.mBluetoothAdapter.cancelDiscovery();
                    }
                }
                allCommand.SaveStringShare(getActivity(),Utils.SHARE_NAME_PRINTER,dataSet.get(position).getsName());
                allCommand.SaveStringShare(getActivity(),Utils.SHARE_IP_PRINTER,dataSet.get(position).getsIp());
                onShowLogCat("IP Printer", dataSet.get(position).getsName() + " : " + dataSet.get(position).getsIp());
                InstanceVariable.con_dev = InstanceVariable.mBluetoothAdapter.getRemoteDevice(dataSet.get(position).getsIp());
                try {
                    if (!InstanceVariable.mConnector.isConnected()) {
                        InstanceVariable.mConnector.connect(InstanceVariable.con_dev);
                    } else {
                        InstanceVariable.mConnector.disconnect();
                    }
                } catch (P25ConnectionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean onCheckOpenBt() {
        if (InstanceVariable.mBluetoothAdapter != null){
            if (InstanceVariable.mBluetoothAdapter.isEnabled()){
                return true;
            }else {
                Intent enableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                getActivity().startActivityForResult(enableIntent, InstanceVariable.REQUEST_ENABLE_BT_PRINTER);
            }
            return false;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mReceiverBT != null){
                getActivity().unregisterReceiver(mReceiverBT);
            }
        }catch (Exception e){
            onShowLogCat("Err","unregisterReceiver mReceiverBT " + e.getMessage());
        }
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == InstanceVariable.REQUEST_ENABLE_BT_PRINTER)
                && resultCode == Activity.RESULT_OK) {
            pairedDevices = InstanceVariable.mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    ModelDataPrinter printer = new ModelDataPrinter();
                    printer.setsName(device.getName());
                    printer.setsIp(device.getAddress());
                    dataSet.add(printer);
                    customAdapterPrinter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == lnPaper2){
            onSettingPaper(2);
        }else if (v == lnPaper3){
            onSettingPaper(3);
        }else if (v == tvSeaPrinter){
            if (onCheckOpenBt()){
               // tvTitleSea.setText(getActivity().getResources().getString(R.string.txt_searching_name_printer));
                tvTitleSea.setText(allCommand.GetStringShare(getContext(),allCommand.text_searching,"Searching..."));
                tvSeaPrinter.setEnabled(false);
                if (arrDeviceName.size() > 0){
                    arrDeviceName.clear();
                }
                if (dataSet.size() > 0){
                    dataSet.clear();
                    customAdapterPrinter.notifyDataSetChanged();
                }
                if (InstanceVariable.mBluetoothAdapter.isDiscovering()){
                    InstanceVariable.mBluetoothAdapter.cancelDiscovery();
                }
                InstanceVariable.mBluetoothAdapter.startDiscovery();
            }

        }else if (v == tvClosePrinter){
            allCommand.deleteShareData(getActivity(),Utils.SHARE_NAME_PRINTER);
            allCommand.deleteShareData(getActivity(),Utils.SHARE_IP_PRINTER);
            if (InstanceVariable.mConnector.isConnected()) {
                try {
                    InstanceVariable.mConnector.disconnect();
                } catch (Exception e) {
                    onShowLogCat("***Err***", "Err Close Printer " + e.getMessage());
                }
            }
            tvNamePrinterConnected.setText(allCommand.GetStringShare(getContext(),allCommand.text_no_connect_printer,"Printer not connected."));
            tvNamePrinterConnected.setTextColor(Color.RED);
            tvClosePrinter.setEnabled(false);
        }
    }
    @Subscribe
    public void onBusStatusPrinter(ModelStatusConnectPrinter conPrinter){
        onShowLogCat("Event Bus","Connect Printer " + conPrinter.getTextStatus());
        if (conPrinter != null){
            if (bCheckPrinter){
                tvNamePrinterConnected.setText(conPrinter.getTextStatus());
                tvNamePrinterConnected.setTextColor(conPrinter.getColorText());
                tvClosePrinter.setEnabled(false);
                if (conPrinter.isConnectComplete()){
                    tvClosePrinter.setEnabled(true);
                    tvNamePrinterConnected.setText(allCommand.GetStringShare(getActivity(),Utils.SHARE_IP_PRINTER,""));
                    int secondsDelayed = 1;
                    final String namePrinter = allCommand.GetStringShare(getActivity(),Utils.SHARE_NAME_PRINTER,"");
                    final String IPPrinter = allCommand.GetStringShare(getActivity(),Utils.SHARE_IP_PRINTER,"");
                    if (bCheckPrintTest) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                InstanceVariable.mConnector.PrintThai1(namePrinter, IPPrinter);
                            }
                        }, secondsDelayed * 1000);
                    }
                }
            }
        }
    }

    private void initView(View rootView) {
        allCommand = new AllCommand();
        bCheckPrinter = true;
        bCheckPrintTest = false;
        lnPaper2 = (LinearLayout) rootView.findViewById(R.id.lnPaper2);
        lnPaper3 = (LinearLayout) rootView.findViewById(R.id.lnPaper3);
        imgPaper2 = (ImageView) rootView.findViewById(R.id.imgPaper2);
        imgPaper3 = (ImageView) rootView.findViewById(R.id.imgPaper3);
        tvPaper2 = (TextView) rootView.findViewById(R.id.tvPaper2);
        tvPaper3 = (TextView) rootView.findViewById(R.id.tvPaper3);
        tvTitleSea = (TextView) rootView.findViewById(R.id.tvTitleSea);
        tvSeaPrinter = (TextView) rootView.findViewById(R.id.tvSeaPrinter);
        tvClosePrinter = (TextView) rootView.findViewById(R.id.tvClosePrinter);
        tvNamePrinterConnected = (TextView) rootView.findViewById(R.id.tvNamePrinterConnected);
        customAdapterPrinter = new CustomAdapterPrinter(getActivity(),dataSet);
        lvNamePrinter = (ListView) rootView.findViewById(R.id.lvNamePrinter);
        lvNamePrinter.setAdapter(customAdapterPrinter);
        lnPaper2.setOnClickListener(this);
        lnPaper3.setOnClickListener(this);
        tvSeaPrinter.setOnClickListener(this);
        tvClosePrinter.setOnClickListener(this);

        txt_paper = rootView.findViewById(R.id.txt_paper);
        text_diver_connect = rootView.findViewById(R.id.text_diver_connect);
    }

    @Override
    public void onResume() {
        super.onResume();
        txt_paper.setText(allCommand.GetStringShare(getContext(),allCommand.text_paper,"paper : "));
        tvSeaPrinter.setText(allCommand.GetStringShare(getContext(),allCommand.text_search,"Search"));
        text_diver_connect.setText(allCommand.GetStringShare(getContext(),allCommand.text_device_connected,"Connected device"));
        tvClosePrinter.setText(allCommand.GetStringShare(getContext(),allCommand.text_close_printer,"Turn off the printer"));
        tvTitleSea.setText(allCommand.GetStringShare(getContext(),allCommand.text_list_device,"Device list"));
    }

    private final BroadcastReceiver mReceiverBT = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                tvSeaPrinter.setEnabled(true);
                tvTitleSea.setText(allCommand.GetStringShare(getContext(),allCommand.text_list_device,"Device list"));
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!arrDeviceName.contains(device.getAddress())){
                    onShowLogCat("Name Printer",device.getName() + " : " + device.getAddress());
                    arrDeviceName.add(device.getAddress());
                    ModelDataPrinter printer = new ModelDataPrinter();
                    printer.setsName(device.getName());
                    printer.setsIp(device.getAddress());
                    dataSet.add(printer);
                    customAdapterPrinter.notifyDataSetChanged();
                }
            }
        }
    };

    private void onSettingPaper(int paper){
        allCommand.saveIntShare(getActivity(), Utils.SHARE_SETTING_PAPER,paper);
        if (paper == 2){
            imgPaper2.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_s));
            imgPaper2.setColorFilter(ContextCompat.getColor(getActivity(),R.color.color_view_listPlay_s));
            tvPaper2.setTextColor(getResources().getColor(R.color.color_view_listPlay_s));
            tvPaper2.setText(allCommand.GetStringShare(getContext(),allCommand.text_paper_2,"2 inches"));
            imgPaper3.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_d));
            imgPaper3.setColorFilter(ContextCompat.getColor(getActivity(),R.color.color_view_listPlay_d));
            tvPaper3.setTextColor(getResources().getColor(R.color.color_view_listPlay_d));
            tvPaper3.setText(allCommand.GetStringShare(getContext(),allCommand.text_paper_3,"3 inches"));
        }else if (paper == 3){
            imgPaper3.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_s));
            imgPaper3.setColorFilter(ContextCompat.getColor(getActivity(),R.color.color_view_listPlay_s));
            tvPaper3.setTextColor(getResources().getColor(R.color.color_view_listPlay_s));
            tvPaper3.setText(allCommand.GetStringShare(getContext(),allCommand.text_paper_3,"3 inches"));
            imgPaper2.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_d));
            imgPaper2.setColorFilter(ContextCompat.getColor(getActivity(),R.color.color_view_listPlay_d));
            tvPaper2.setTextColor(getResources().getColor(R.color.color_view_listPlay_d));
            tvPaper2.setText(allCommand.GetStringShare(getContext(),allCommand.text_paper_2,"2 inches"));
        }
    }
    public void onShowLogCat(String tag, String msg){
        if (BuildConfig.DEBUG) {
            Log.e("***Printer***",tag +" ==> "+ msg);
        }
    }
}

package mdev.mlaocthtione.con_bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

/**
 * Created by Varayut on 11/9/2558.
 */
public class InstanceVariable {
    //=== เพิ่ม ===
    public static BluetoothAdapter mBluetoothAdapter;
    public static final int REQUEST_ENABLE_BT_MAIN = 100;
    public static final int REQUEST_ENABLE_BT_PRINTER = 200;
    public static P25Connector mConnector;
    public static BluetoothDevice con_dev = null;

}

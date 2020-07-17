package com.example.homemanage;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Button;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private OutputStream mOutStream;
    private BluetoothSocket socket;
    private final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
    private Set<BluetoothDevice> pairedDevices;
    private List<BluetoothDevice> connectedDevices;
    private BluetoothDevice device;
    private ParcelUuid[] uuid;
    private final int start = 1;
    private final int end = 0;
    final Button lamp_on = findViewById(R.id.Lamp_Start);
    final Button lamp_off = findViewById(R.id.Lamp_Stop);
    private final String LAMP = "L";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled() ||
                bluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) != BluetoothHeadset.STATE_CONNECTED) {
            setContentView(R.layout.activity_display_message);
        } else {
            setContentView(R.layout.activity_main);
            this.pairedDevices = bluetoothAdapter.getBondedDevices();
            this.connectedDevices = this.bluetoothManager.getConnectedDevices(2);
            for(BluetoothDevice pairedDevice : pairedDevices) {
                for (BluetoothDevice connectedDevice : connectedDevices) {
                    if (pairedDevice.getName().equals(connectedDevice.getName())) {
                        this.device = pairedDevice;
                        this.uuid = pairedDevice.getUuids();
                    }
                }
            }
            try {
                this.socket = device.createRfcommSocketToServiceRecord(this.uuid[0].getUuid());
                this.mOutStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void write(String str, int order) {
        try {
            mOutStream.write((str + order).getBytes());
        } catch (IOException e) {
            Log.e("Error when sending data", String.valueOf(e));
        }
    }
    public void startLamp() {
        this.write(LAMP, start);
    }
    public void endLamp() {
        this.write(LAMP, end);
    }
}

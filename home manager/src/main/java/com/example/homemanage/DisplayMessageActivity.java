package com.example.homemanage;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisplayMessageActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        setContentView(R.layout.activity_display_message);
        final Button button = findViewById(R.id.button_try_again_connect);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled() ||
                        bluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) != BluetoothHeadset.STATE_CONNECTED) {
                    setContentView(R.layout.activity_display_message);
                } else {
                    setContentView(R.layout.activity_main);
                }
            }
        });
    }
}
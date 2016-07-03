package cl.cygnusmedia.acidobinario.tankcontrol;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class TankControl extends AppCompatActivity {
    private static final String TAG = "TANK";

    Button btnDis, btnTorIz, btnTorDer;
    ImageView imgF, imgB, imgL, imgR, imgBan;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private OutputStream outStream = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        setContentView(R.layout.activity_tank_control);

        btnDis = (Button) findViewById(R.id.disconect);
        btnTorDer = (Button) findViewById(R.id.torder);
        btnTorIz = (Button) findViewById(R.id.toriz);

        imgF = (ImageView) findViewById(R.id.adelante);
        imgB = (ImageView) findViewById(R.id.atras);
        imgL = (ImageView) findViewById(R.id.izquierda);
        imgR = (ImageView) findViewById(R.id.derecha);
        imgBan = (ImageView) findViewById(R.id.ban);

        new ConnectBT().execute(); //Call the class to connect

        imgF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    adelante();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    parar();
                }
                return true;
            }
        });

        imgB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    atras();
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    parar();
                }
                return true;
            }
        });

        imgL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    izquierda();
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    parar();
                }
                return true;
            }
        });

        imgR.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    derecha();
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    parar();
                }
                return true;
            }
        });

        btnTorIz.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    TorretaIzquierda();
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    parar();
                }
                return true;
            }
        });

        btnTorDer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    TorretaDerecha();
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    parar();
                }
                return true;
            }
        });

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
            }
        });

        imgBan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    Banear();
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    parar();
                }
                return true;
            }
        });

    }

    private void derecha() {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("3".toString().getBytes());
                Log.d(TAG, "Derecha");
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void TorretaIzquierda() {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("5".toString().getBytes());
                Log.d(TAG, "Torreta Izquierda");
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void TorretaDerecha() {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("6".toString().getBytes());
                Log.d(TAG, "Torreta Derecha");
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void Banear() {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("7".toString().getBytes());
                Log.d(TAG, "Banea2 xDxdXd");
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void izquierda() {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("2".toString().getBytes());
                Log.d(TAG, "Izquierda");
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void parar() {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("4".toString().getBytes());
                Log.d(TAG, "Parar");
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void atras() {if (btSocket!=null)
    {
        try
        {
            btSocket.getOutputStream().write("1".toString().getBytes());
            Log.d(TAG, "Atras");
        }
        catch (IOException e)
        {
            msg("Error");
        }
    }
    }

    private void adelante() {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("0".toString().getBytes());
                Log.d(TAG, "Adelante");
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            Log.d(TAG, "BT DISCONNECT!");
            msg("conexion terminada e.e :D");
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(TankControl.this, "Connecting...", "es pera!? XdxdxD");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Conecta2 e.e");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
/*
    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...In onResume - Attempting client connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = myBluetooth.getRemoteDevice(address);

        // se necesitan 2 cosas para realizar la coneccion:
        //   una dirección mac del bluetooth
        //   y el ID o UUID del servicio en caso de que usemos UUID para el SPP
        //
        try {
            btSocket = device.createRfcommSocketToServiceRecord(myUUID);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        myBluetooth.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting to Remote...");
        try {
            btSocket.connect();
            Log.d(TAG, "...Connection established and data link opened...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Creating Socket...");

        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
        }
    }*/

    private void errorExit(String title, String message) {
        Toast toastmsg = Toast.makeText(getBaseContext(),
                title + " - " + message, Toast.LENGTH_LONG);
        toastmsg.show();
        finish();
    }


}

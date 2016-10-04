package adb.example.zzl.adb_test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public final static int IP = 0x400 +0;
    public final static int REC = 0x400 +1;
    public final static int SEN = 0x400 +2;

    private Handler mHandler = null;
    private AcceptThread mAcceptThread = null;
    private EditText editText ;
    private AppCompatButton button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("zzl:::","onCreate!!!!!!!!!!!!!!!!!");

        editText = (EditText)findViewById(R.id.edittext);
        button_send = (AppCompatButton)findViewById(R.id.button);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String send_str = editText.getText().toString();
                mHandler.obtainMessage(MainActivity.SEN,send_str).sendToTarget();
            }
        });

        mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                switch(msg.what)
                {
                    case IP:
                    {
                        String strAddress = (msg.obj).toString();
                        Log.d("zzl:::","IP:" +strAddress);
                        break;
                    }
                    case REC:
                    {
                        String strRcv = (msg.obj).toString();
                        Log.v("zzl:::","Rec:" +strRcv);

                        break;
                    }
                    case SEN:
                    {
                        if(mAcceptThread != null){
                            String send = (msg.obj).toString();
                            mAcceptThread.sendMsg(send);
                        }
                        break;
                    }

                }

            }
        };

        mAcceptThread = new AcceptThread(mHandler);
        mAcceptThread.start();
    }


}

package adb.example.zzl.adb_test;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by zzl on 16/10/3.
 */


public class ReceiveThread extends Thread
{
    private InputStream mInputStream = null;
    private byte[] buf = new byte[512];
    private boolean stop = false;
    private String str = null;
    private android.os.Handler Handler = null;

    ReceiveThread(InputStream in, Handler handler)
    {

            // 获得输入流
            mInputStream = in;
            Handler = handler;
    }

    @Override
    public void run()
    {
        while(!stop)
        {

            // 读取输入的数据(阻塞读)
            Arrays.fill(buf,(byte)0);
            try {
                mInputStream.read(buf);
                str = new String(buf, "GB2312").trim();
                if(str.equals(""))
                    stop = true;
                Message msg = new Message();
                msg.what = MainActivity.REC;
                msg.obj = this.str;
                // 发送消息
                Handler.sendMessage(msg);

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                stop = true;
                try {
                    mInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                e1.printStackTrace();
            }

        }
    }

    public void onStop(){
        stop = true;
    }
}
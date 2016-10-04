package adb.example.zzl.adb_test;

import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zzl on 16/10/3.
 */


public class AcceptThread extends Thread
{
    private Socket clientSocket = null;
    private ServerSocket mServerSocket = null;
    private android.os.Handler Handler = null;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    private boolean stop_flag = false;
    ReceiveThread mReceiveThread = null;

    public AcceptThread(android.os.Handler handler) {

        Handler = handler;
    }

    @Override
    public void run()
    {
        try {
            // 实例化ServerSocket对象并设置端口号为 12589
            mServerSocket = new ServerSocket();
            mServerSocket.setReuseAddress(true);
            mServerSocket.bind(new InetSocketAddress(12589));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while(!stop_flag) {
            if(mServerSocket != null) {
                try {
                    // 等待客户端的连接（阻塞）
                    clientSocket = mServerSocket.accept();
                    inputStream = clientSocket.getInputStream();
                    outputStream = clientSocket.getOutputStream();

                    if(clientSocket != null) {
                        mReceiveThread = new ReceiveThread(inputStream, Handler);
                        // 开启接收线程
                        mReceiveThread.start();

                        Message msg = new Message();
                        msg.what = MainActivity.IP;
                        // 获取客户端IP
                        msg.obj = "127.0.0.1";//clientSocket.getInetAddress().getHostAddress();
                        // 发送消息
                        Handler.sendMessage(msg);
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }

            }
        }
    }

    public void sendMsg(String str){

        if(clientSocket != null && outputStream != null){
            try {
                outputStream.write(str.getBytes("GB2312"));

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }


}

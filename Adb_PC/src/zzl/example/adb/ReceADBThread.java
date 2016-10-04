package zzl.example.adb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ReceADBThread extends Thread{
	
	private Socket socket = null;
	InputStream inStream = null;
	byte rece[] = new byte[512];
	String str = null;

	public ReceADBThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			inStream = socket.getInputStream();
			inStream.read(rece);
            str = new String(rece, "GB2312").trim();
            System.out.println("Rece:"+str);
			while(!str.equals("")){
				inStream.read(rece);
	            str = new String(rece, "GB2312").trim();
	            System.out.println("Rece:"+str);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

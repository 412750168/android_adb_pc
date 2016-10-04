package zzl.example.adb;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ADBOperate {
	
	public static void doAction(){
		try {
			Runtime.getRuntime()
					.exec("/Users/zzl/Downloads/android-sdk-macosx/platform-tools/adb forward tcp:12581 tcp:12589");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Socket socket = null;
		try {
			InetAddress serverAddr = null;

			serverAddr = InetAddress.getByName("127.0.0.1");
			System.out.println("TCP 1" + "C: Connecting...");
			socket = new Socket(serverAddr, 12581); // 12581 是 PC 的端口，已重定向到;
													// Device 的 12589 端口

			SendADBThread sendADBThread = new SendADBThread(socket);
			sendADBThread.start();
			
			ReceADBThread receADBThread = new ReceADBThread(socket);
			receADBThread.start();

		} catch (UnknownHostException e1) {
			System.out.println("TCP 2" + "ERROR: " + e1.toString());
		} catch (IOException e2) {
			System.out.println("TCP 3" + "ERROR: " + e2.toString());
		} finally {

		}

	}

}

package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TestSockClient {
	static DataInputStream dis = null;

	public static void main(String[] args) {
		DataOutputStream dos = null;
		String str = null;
		Socket socket = null;
		Scanner input = new Scanner(System.in);

		try {

			socket = new Socket("127.0.0.1", 8888);
			do {
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				// 匿名内部类 另开线程 显示服务器传来的内容
				new Thread() {
					@Override
					public void run() {
						String s = null;
						try {
							if ((s = dis.readUTF()) != null) {
								System.out.println(s);
							}

						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}.start();
				str = input.nextLine();
				dos.writeUTF(str);

			} while (!str.equals("88"));
			System.out.println("客户端结束！");

		} catch (IOException e) {
		} finally {
			// 关闭
			try {
				dos.close();
				dis.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}

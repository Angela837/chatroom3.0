package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestSockServer {
	// 主线程
	static List<Client> clientList = new ArrayList<Client>();// 存线程存通道socket

	public static void main(String[] args) {
		Socket socket = null;
		try {
			// 准备
			@SuppressWarnings("resource")
			ServerSocket s = new ServerSocket(8888);
			System.out.println("服务器启动！");
			// 连接
			// socket = s.accept();
			// 信息交互等待
			while (true) {
				socket = s.accept();
				Client c = new TestSockServer().new Client(socket);// 一个c代表一个客户端对象
				clientList.add(c);
				c.start();
			}
		} catch (IOException e) {
			System.out.println("服务器结束！");
		}

	}

	// 多线程 一人一通道
	class Client extends Thread {
		DataOutputStream dos = null;
		DataInputStream dis = null;
		Socket socket = null;// 通道

		public Client(Socket socket) {
			super();
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				while (true) {
					dos = new DataOutputStream(socket.getOutputStream());
					dis = new DataInputStream(socket.getInputStream());
					String str = null;
					if ((str = dis.readUTF()) != null) {
						System.out.println("客户端：" + str);
						// System.out.println("客户端IP："+socket.getInetAddress().getHostAddress());
						// System.out.println("客户端端口号："+socket.getPort());
					}
					// dos.writeUTF(socket.getPort() + "说：" + str);
					for (int i = 0; i < clientList.size(); i++) {
						// a的话返回给bcd
						new DataOutputStream(clientList.get(i).socket.getOutputStream())
								.writeUTF(socket.getPort() + ":" + str);
						;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// 关闭
				try {
					dis.close();
					dos.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}

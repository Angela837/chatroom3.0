package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestSockServer {
	// ���߳�
	static List<Client> clientList = new ArrayList<Client>();// ���̴߳�ͨ��socket

	public static void main(String[] args) {
		Socket socket = null;
		try {
			// ׼��
			@SuppressWarnings("resource")
			ServerSocket s = new ServerSocket(8888);
			System.out.println("������������");
			// ����
			// socket = s.accept();
			// ��Ϣ�����ȴ�
			while (true) {
				socket = s.accept();
				Client c = new TestSockServer().new Client(socket);// һ��c����һ���ͻ��˶���
				clientList.add(c);
				c.start();
			}
		} catch (IOException e) {
			System.out.println("������������");
		}

	}

	// ���߳� һ��һͨ��
	class Client extends Thread {
		DataOutputStream dos = null;
		DataInputStream dis = null;
		Socket socket = null;// ͨ��

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
						System.out.println("�ͻ��ˣ�" + str);
						// System.out.println("�ͻ���IP��"+socket.getInetAddress().getHostAddress());
						// System.out.println("�ͻ��˶˿ںţ�"+socket.getPort());
					}
					// dos.writeUTF(socket.getPort() + "˵��" + str);
					for (int i = 0; i < clientList.size(); i++) {
						// a�Ļ����ظ�bcd
						new DataOutputStream(clientList.get(i).socket.getOutputStream())
								.writeUTF(socket.getPort() + ":" + str);
						;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// �ر�
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

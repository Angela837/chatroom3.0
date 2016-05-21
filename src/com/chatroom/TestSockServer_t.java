package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestSockServer_t {
	List<Client> clientList = new ArrayList<Client>();// �̹߳�������static

	public void init() {
		ServerSocket server = null;
		Socket socket = null;
		try {
			// �򿪶˿ڣ��ȴ����������ӡ�
			server = new ServerSocket(8888);
			System.out.println("�������ѿ�����");
			// �����ѭ�������������пͻ������ӣ�����˽��ܲ��Ҵ���һ���̣߳�������ͨ��socket������߳��У��������̡߳�
			while (true) {
				//��ͨͨ��socket
				socket = server.accept();
				Client c = new Client(socket);
				clientList.add(c);// �������ӵĿͻ��˴���list�У��Ա��ں������������Ϣ��
				c.start();
				// ����userList
				updateUserList();
			}

		} catch (IOException e) {
			System.out.println("�������ر�!");
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		TestSockServer_t ts = new TestSockServer_t();
		ts.init();
	}

	// ���չ�������õ��ַ��������� ��,aa,bb,cc,dd
	public String getNameStr() {
		String str = ",";
		for (int i = 0; i < clientList.size(); i++) {
			str += clientList.get(i).name + ",";
		}
		return str;
	}

	// �����пͻ��˷��͸��º���û��б�
	public void updateUserList() {
		String userStr=getNameStr();
		for (int i = 0; i < clientList.size(); i++) {
			try {
				new DataOutputStream(clientList.get(i).socket.getOutputStream()).writeUTF(userStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ��Ϊ��Ҫ���ö���߳�����ɿͻ��˵����ӣ���������ʹ���ڲ��ࡣ
	class Client extends Thread {
		Socket socket;
		String name;
		DataOutputStream dos;
		DataInputStream dis;

		// ʹ�ô������Ĺ��췽������ͨ�������߳��С�
		public Client(Socket socket) {
			this.socket = socket;
			name="�û� "+socket.getPort();
		}

		@Override
		public void run() {
			String str = null;

			try {
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
				// ѭ��������������ĳ���ͻ��˴�������Ϣ�����ҽ���Щ��Ϣ�������͸������ͻ��ˡ�
				while (true) {
					if ((str = dis.readUTF()) != null) {
						// �ж����ݸ�ʽ�����#��ʼ�������Լ���name���ԣ�����ѭ�������Ϣ
						if (str.startsWith("#")) {
							name = str.substring(1);
						} else {
							// ѭ����������Ϣ���͸����пͻ��ˡ�
							for (int i = 0; i < clientList.size(); i++) {
								// �ж��Ƿ����ǳ�
//								if (name != null) {
									new DataOutputStream(clientList.get(i).socket.getOutputStream())
											.writeUTF(name + ":" + str);

//								} else {
//									new DataOutputStream(clientList.get(i).socket.getOutputStream())
//											.writeUTF(socket.getPort() + ":" + str);
								}
							}
						}
					
				}
			} catch (IOException e) {
				System.out.println("�ͻ���" + socket.getPort() + "�˳�");
				updateUserList();//�����û��б�
				clientList.remove(this);
				// e.printStackTrace();
			} finally {
				// �رո�����
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
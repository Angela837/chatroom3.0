package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSockServer {
	public static void main(String[] args) {
		DataOutputStream dos=null;
		DataInputStream dis=null;
		Socket socket=null;
	    try {
			//׼��
	    	ServerSocket s=new ServerSocket(8888);
	    	System.out.println("������������");
	    	//����
	    	socket=s.accept();
	    	//��Ϣ����
	    	while(true){
	    		dos=new DataOutputStream(socket.getOutputStream());
	    		dis=new DataInputStream(socket.getInputStream());
	    		String str=null;
	    		if((str=dis.readUTF())!=null){
	    			System.out.println("�ͻ��ˣ�"+str);
	    			//System.out.println("�ͻ���IP��"+socket.getInetAddress().getHostAddress());
	    			//System.out.println("�ͻ��˶˿ںţ�"+socket.getPort());
	    		}
	    		dos.writeUTF("������˵��"+str);
	    	}
	    	
		} catch (IOException e) {
		System.out.println("������������");
		}finally{
			//�ر�
			try {
				dis.close();
				dos.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		}
		
	}

}

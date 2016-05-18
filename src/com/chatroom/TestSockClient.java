package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TestSockClient {
	public static void main(String[] args) {
		DataOutputStream dos=null;
		DataInputStream dis=null;
		String str=null;
		Socket socket=null;
		Scanner input=new Scanner(System.in);
		
	    try {
                socket =new Socket("127.0.0.1",8888);
             do{
            	dis=new DataInputStream(socket.getInputStream());
	    		dos=new DataOutputStream(socket.getOutputStream());
	    		
	    	    str=input.nextLine();
	    	  
	    	   dos.writeUTF(str);
	    		if((str=dis.readUTF())!=null){
		    	  System.out.println(str); 
	    		}
	    		 
	    	}while(!str.equals("88"));
	    	
		} catch (IOException e) {
		System.out.println("客户端结束！");
		}finally{
			//关闭
			try {
				dos.close();
				dis.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		}
		
	}

}

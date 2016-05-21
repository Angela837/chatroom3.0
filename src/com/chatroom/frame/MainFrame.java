package com.chatroom.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField sendText;
	private JTextArea textArea;

	private DataInputStream dis;
	private DataOutputStream dos;
	private Socket socket;
	private JTextField nameText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void init() {
		try {
			// 客户端连接服务器
			socket = new Socket("127.0.0.1", 8888);
			System.out.println("客户端已连接！");
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			// 启动线程来显示信息
			new Thread() {
				@Override
				public void run() {
					String s = null;
					try {
						while (true) {
							if ((s = dis.readUTF()) != null)
								textArea.append(s + "\r\n");

						}
					} catch (IOException e) {
						// e.printStackTrace();
					}
				}
			}.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		// 点×关闭聊天界面
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

			}
		});
		setTitle("PP\u804A\u5929\u5BA4");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton sendButton = new JButton("\u53D1\u9001");
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			// 向服务器发送数据信息(发送)
			public void mouseClicked(MouseEvent arg0) {
				sendMsg();
			}
		});

		textArea = new JTextArea();
		textArea.setColumns(30);
		textArea.setRows(10);
		sendText = new JTextField();
		sendText.addActionListener(new ActionListener() {
			// 向服务器发送数据信息(回车)
			public void actionPerformed(ActionEvent arg0) {
				sendMsg();
			}
		});
		sendText.setColumns(10);

		JLabel nameLabel = new JLabel("\u6635\u79F0\uFF1A");

		nameText = new JTextField();
		nameText.setColumns(10);

		JButton nameButton = new JButton("\u63D0\u4EA4");
		nameButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//向服务器提交昵称：#张三
				try {
					dos.writeUTF("#"+(nameText.getText()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sendText.setText("");
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(sendText, GroupLayout.PREFERRED_SIZE, 165,
												GroupLayout.PREFERRED_SIZE)
										.addGap(37).addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 83,
												GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_contentPane.createSequentialGroup().addComponent(nameLabel).addGap(1)
										.addComponent(nameText, GroupLayout.PREFERRED_SIZE, 148,
												GroupLayout.PREFERRED_SIZE)
										.addGap(44).addComponent(nameButton)))
						.addContainerGap(138, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(19, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(Alignment.TRAILING,
														gl_contentPane.createSequentialGroup().addComponent(nameLabel)
																.addGap(18))
										.addGroup(Alignment.TRAILING,
												gl_contentPane.createSequentialGroup().addComponent(nameButton)
														.addGap(12))))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(19)
								.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(23)
								.addComponent(sendText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
										.addComponent(sendButton).addContainerGap()))));
		contentPane.setLayout(gl_contentPane);
		// 连接服务器的方法的
		init();

	}

	public void sendMsg() {
		try {
			dos.writeUTF(sendText.getText());
			sendText.setText("");
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}

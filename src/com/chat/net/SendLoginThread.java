package com.chat.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.chat.bean.Msg;
import com.chat.util.OnLineUser;

public class SendLoginThread {
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private int sendType = 0;
	private Msg message = null;
	public SendLoginThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void send(Object obj){
		try {
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

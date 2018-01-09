package com.chat.util;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.chat.net.SendLoginThread;

public class OnLineUser {
	private static OnLineUser onLineUser;
	private static Map<String , Socket> map;
	private static int onLineUserNum;
	private OnLineUser(){
		map = new HashMap<String , Socket>();
		onLineUserNum = 0;
	}
	public static OnLineUser getInstance(){
		if(onLineUser == null){
			onLineUser = new OnLineUser();
		}
		return onLineUser;
	}
	
	public void addOnLineUser(String name,Socket client){
		map.put(name, client);
		this.onLineUserNum++;
	}
	
	public boolean isOnline(String name){
		return map.containsKey(name);
	}
	
	public void closeOnlineUser(String name){
		map.remove(name);
		this.onLineUserNum--;
	}
	
	public int getOnLineUserNum(){
		return this.onLineUserNum;
	}
	
	public Socket getSocket(String name){
		return map.get(name);
	}
	
	public void removeOnlineUser(String name){
		map.remove(name);
		--this.onLineUserNum;
	}
	public static Map<String, Socket> getMap() {
		return map;
	}
	
}

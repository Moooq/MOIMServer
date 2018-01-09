package com.chat.bean;

import java.io.Serializable;
import java.util.Date;

public class Msg implements Serializable{
	public static final int STRING = 1;
	public static final int PICTURE = 2;
	public static final int VOICE = 4;
	public static final int OTHER = 3;
	
	private Object messagecontent;
    private int messagetype;
    private String time;
    private String sender;
	private String receiver;
	public Object getMessagecontent() {
		return messagecontent;
	}
	public void setMessagecontent(Object messagecontent) {
		this.messagecontent = messagecontent;
	}
	public int getMessagetype() {
		return messagetype;
	}
	public void setMessagetype(int messagetype) {
		this.messagetype = messagetype;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	
	
}

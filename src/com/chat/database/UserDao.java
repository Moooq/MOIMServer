package com.chat.database;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chat.bean.FriendList;
import com.chat.bean.LoginReturnType;
import com.chat.bean.Msg;
import com.chat.bean.User;
import com.chat.bean.UserInfo;
import com.chat.util.Utils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.ws.util.ByteArrayBuffer;

public class UserDao implements com.chat.util.UserDao {
	@Override
	public List searchList(int name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List userList(int name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMessage(Msg message) {
		// TODO Auto-generated method stub
		Connection conn = DataBaseHelper.getConnection();
		String messageContent = null;
		if(message.getMessagetype() == message.STRING){
			messageContent = (String) message.getMessagecontent();
		}
		String sql = "insert into offlinemessage (messagecontent,messagetype,time,sender,receiver) values(?,?,?,?,?)";
		try {
			PreparedStatement state = (PreparedStatement) conn.prepareStatement(sql);
			state.setString(1, messageContent);
			state.setInt(2, message.getMessagetype());
			state.setString(3,message.getTime());
			state.setString(4, message.getSender());
			state.setString(5, message.getReceiver());
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}

	@Override
	public boolean addUser(UserInfo u, String imagePath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserInfo findInf(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FriendList> getFriends(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdateUser(int isOnline, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateUser(UserInfo u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateFriend(FriendList friend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReceiveMessage(Msg message, UserInfo u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateSendMessage(String senderName, String receiverName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateReceiveMessage(String senderName, String receiverName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LoginReturnType loginUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFriend(FriendList friend) {
		// TODO Auto-generated method stub
		
	}


}

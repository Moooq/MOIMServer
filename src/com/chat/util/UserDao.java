package com.chat.util;

import java.util.List;

import com.chat.bean.FriendList;
import com.chat.bean.LoginReturnType;
import com.chat.bean.Msg;
import com.chat.bean.User;
import com.chat.bean.UserInfo;

public interface UserDao {
	public boolean addUser(UserInfo u,String imagePath);
	public List userList(int name);
	public List searchList(int name);
	public LoginReturnType loginUser(User u);
	public UserInfo findInf(String name);
	public List<FriendList> getFriends(String name);
	public void UpdateUser(int isOnline,String name);
	public void UpdateUser(UserInfo u);
	public void UpdateFriend(FriendList friend);
	public void deleteFriend(FriendList friend);
	public void addMessage(Msg message);
	public void addReceiveMessage(Msg message ,UserInfo u);
	public void UpdateSendMessage(String senderName,String receiverName);
	public void UpdateReceiveMessage(String senderName, String receiverName);
	
}

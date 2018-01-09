package com.chat.net;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import sun.print.resources.serviceui;

import com.chat.bean.FriendList;
import com.chat.bean.IsRead;
import com.chat.bean.LoginReturnType;
import com.chat.bean.Msg;
import com.chat.bean.MyFriendList;
import com.chat.bean.MyString;
import com.chat.bean.OffLine;
import com.chat.bean.OnLineOffLine;
import com.chat.bean.SendType;
import com.chat.bean.User;
import com.chat.database.UserDao;
import com.chat.util.OnLineUser;
import com.sun.xml.internal.stream.Entity;

public class ReceiveThread implements Runnable {
	private ObjectInputStream ois = null;
	private boolean isStart = true;
	private Socket socket = null;
	private User user = null;

	public ReceiveThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		user = new User();
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		isStart = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isStart) {
			SocketAddress s = socket.getRemoteSocketAddress();
			try {
				Object obj = ois.readObject();
				UserDao dao = new UserDao();
				if (obj instanceof User) {
					if (((User) obj).getType() == SendType.ACCOUNT_CONFIRM) {
						LoginReturnType l = (dao.loginUser((User) obj));
						String name = ((User) obj).getName();
						if (l == LoginReturnType.SUCCESS) {
							user = (User) obj;
							if (OnLineUser.getInstance().isOnline(name)) {
								new SendLoginThread(OnLineUser.getMap().get(name)).send(LoginReturnType.FORCE_OFFLINE);
								OnLineUser.getInstance().removeOnlineUser(name);
								OnLineUser.getInstance().addOnLineUser(name, socket);
								Server.socketList.remove(OnLineUser.getMap().get(name));
								new SendLoginThread(OnLineUser.getInstance().getMap().get(name)).send(l);
								MyFriendList friends = new MyFriendList();
								friends.setFriends(dao.getFriends(name));
								new SendLoginThread(OnLineUser.getInstance().getMap().get(name)).send(friends);
							} else {
								OnLineUser.getInstance().addOnLineUser(name, socket);
								new SendLoginThread(socket).send(l);
								MyFriendList friends = new MyFriendList();
								friends.setFriends(dao.getFriends(name));
								List<FriendList> f = friends.getFriends();
								OnLineOffLine o = new OnLineOffLine();
								o.setName(name);
								o.setType(OnLineOffLine.TYPE_ONLINE);
								for (int i = 0; i < f.size(); i++) {
									if (OnLineUser.getInstance().getMap().containsKey(f.get(i).getFriendName())) {
										new SendLoginThread(
												OnLineUser.getInstance().getMap().get(f.get(i).getFriendName()))
														.send(o);
									}
								}
								new SendLoginThread(socket).send(friends);
							}
							dao.UpdateUser(0, name);
						} else {
							new SendLoginThread(socket).send(l);
						}
						showOnlineSocket(Server.socketList);
						showOnlineMap(OnLineUser.getMap());
					}
				}
//				if (obj instanceof Comfirm) {
//					if (dao.check(((Comfirm) obj).getConfirm())) {
//						Comfirm c = (Comfirm) obj;
//						c.setRegistered(true);
//						c.setYanZhengMa("0");
//						new SendLoginThread(socket).send(c);
//						System.out.println(c.isRegistered());
//					} else {
//						Comfirm c = (Comfirm) obj;
//						SendMail mail = new SendMail();
//						mail.send(c.getConfirm());
//						c.setRegistered(false);
//						c.setYanZhengMa(mail.getContent());
//						new SendLoginThread(socket).send(c);
//						System.out.println(c.isRegistered());
//					}
//				}
//				if (obj instanceof UserInformation) {
//					UserInformation u = (UserInformation) obj;
//					if (u.getType() == 0) {
//						System.out.println(u.getPhoto().length);
//						File file = new File("headImage/" + u.getName() + ".jpg");
//						if (file.exists()) {
//							file.delete();
//						}
//						file.createNewFile();
//						System.out.print(u.getPhoto());
//						FileOutputStream fos = new FileOutputStream(file);
//						BufferedOutputStream bos = new BufferedOutputStream(fos);
//						bos.write(u.getPhoto());
//						bos.flush();
//						bos.close();
//						u.setIsOnline(0);
//
//						if (dao.addUser(u, file.getPath())) {
//							OnLineUser.getInstance().addOnLineUser(u.getName(), socket);
//							new SendLoginThread(socket).send(new MyString("InitSuccess"));
//						} else {
//							new SendLoginThread(socket).send(new MyString("InitFail"));
//						}
//					} else if (u.getType() == 1) {// �޸���Ϣ
//						File file = new File("headImage/" + u.getName() + ".jpg");
//						if (file.exists()) {
//							file.delete();
//						}
//						file.createNewFile();
//						System.out.print(u.getPhoto());
//						FileOutputStream fos = new FileOutputStream(file);
//						BufferedOutputStream bos = new BufferedOutputStream(fos);
//						bos.write(u.getPhoto());
//						bos.flush();
//						bos.close();
//						dao.UpdateUser(u);
//					}
//				}
//				if (obj instanceof FindPassword) {
//					FindPassword f = (FindPassword) obj;
//					if (dao.check(f.getEmail())) {
//						f.setPassword(dao.findPassword(f.getEmail()));
//						f.setType(1);
//						SendMail mail = new SendMail();
//						mail.send(f.getEmail());
//						f.setYanzhengma(mail.getContent());
//						System.out.println(f.getYanzhengma());
//					} else {
//						f.setType(2);
//					}
//					new SendLoginThread(socket).send(f);
//				}
//				if (obj instanceof GetPersonPassword) {
//					GetPersonPassword g = (GetPersonPassword) obj;
//					g.setU(dao.findInf(g.getName()));
//					new SendLoginThread(socket).send(g);
//				}
//				if (obj instanceof MyString) {
//					String name = ((MyString) obj).getString();
//					UserInformation u = dao.findInf(name);
//					new SendLoginThread(socket).send(u);
//				}
//				if (obj instanceof FriendList) {
//					FriendList friend = (FriendList) obj;
//					if (friend.getType() == 1) {
//						dao.UpdateFriend(friend);
//					} else if (friend.getType() == 2) {
//						dao.deleteFriend(friend);
//					}
//
//				}
				if (obj instanceof OffLine) {
					OffLine of = (OffLine) obj;
					Server.socketList.remove(OnLineUser.getInstance().getMap().get(of.getName()));
					OnLineUser.getInstance().getMap().remove(of.getName());
					dao.UpdateUser(1, of.getName());
				}
				if (obj instanceof Msg) {
					Msg message = (Msg) obj;
					if(message.getMessagetype()==message.STRING){
						if(OnLineUser.getInstance().getMap().containsKey(message.getReceiver())){
							new SendLoginThread(OnLineUser.getInstance().getMap().get(message.getReceiver())).send(message);
						}else{
							dao.addMessage(message);
						}
					}
					
					
//					if (message.getType() == message.TYPE_SEND) {
//						dao.UpdateSendMessage(message.getSender().getName(), message.getReceiver().getName());
//						dao.UpdateReceiveMessage(message.getReceiver().getName(), message.getSender().getName());
//						dao.addSendMessage(message, message.getSender());
//						message.setType(message.TYPE_RECEIVED);
//						dao.addReceiveMessage(message, message.getReceiver());
//						if (OnLineUser.getInstance().getMap().containsKey(message.getReceiver().getName())) {
//							IsRead i = new IsRead();
//							i.setSenderName(message.getSender().getName());
//							i.setReceiverName(message.getReceiver().getName());
//							new SendLoginThread(OnLineUser.getInstance().getMap().get(message.getReceiver().getName()))
//									.send(message);
//						}
//					}
				} 
				// if(obj instanceof IsRead){
				// IsRead i = (IsRead) obj;
				// dao.UpdateSendMessage(i.getSenderName(),i.getReceiverName());
				// dao.UpdateReceiveMessage(i.getReceiverName(),i.getSenderName());
				// new
				// SendLoginThread(OnLineUser.getInstance().getMap().get(i.getSenderName())).send(i);
				// }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				stop();
			} catch (EOFException e) {
				// TODO: handle exception
				stop();
				UserDao dao = new UserDao();
				MyFriendList friends = new MyFriendList();
				friends.setFriends(dao.getFriends(user.getName()));
				List<FriendList> f = friends.getFriends();
				OnLineOffLine o = new OnLineOffLine();
				o.setName(user.getName());
				o.setType(OnLineOffLine.TYPE_OFFLINE);
				for (int i = 0; i < f.size(); i++) {
					if (OnLineUser.getInstance().getMap().containsKey(f.get(i).getFriendName())) {
						new SendLoginThread(OnLineUser.getInstance().getMap().get(f.get(i).getFriendName())).send(o);
					}
				}
				Server.socketList.remove(OnLineUser.getInstance().getMap().get(user.getName()));
				OnLineUser.getInstance().getMap().remove(user.getName());
				dao.UpdateUser(1, user.getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				stop();
			}
		}
	}

	public void showOnlineSocket(List<Socket> list) {
		System.out.println("#################show online socket list###############");
		for (Socket s : list) {
			System.out.println(s);
		}
	}

	public void showOnlineMap(Map<String, Socket> map) {
		if (map != null) {
			System.out.println("Online num:" + map.size() + "||" + OnLineUser.getInstance().getOnLineUserNum());
			System.out.println("#################show online map###############");
			Set<String> keys = map.keySet();
			for (String key : keys) {
				System.out.println(key + "------>" + map.get(key));
			}
		}

	}

}

package org.heiduc.oauth2.handler;

import java.io.Serializable;

public class Credentials implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String nickName;
	private String avatar;
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Credentials() {
		super();
	}
	
	public Credentials(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public Credentials(String userName, String password, String nickName,
			String avatar,String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
		this.avatar = avatar;
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

package org.heiduc.oauth2.handler;

import java.io.Serializable;

public class Credentials implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String nickname;
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
	
	public Credentials(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public Credentials(String username, String password, String nickname,
			String avatar,String email) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.avatar = avatar;
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

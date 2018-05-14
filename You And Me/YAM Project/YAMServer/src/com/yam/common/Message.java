package com.yam.common;

public class Message implements java.io.Serializable{
	
	private String mesType;
	private String sender;
	private String getter;
	private String con;
	private String con2;
	private String con3;
	public String getCon3() {
		return con3;
	}

	public void setCon3(String con3) {
		this.con3 = con3;
	}

	public String getCon2() {
		return con2;
	}

	public void setCon2(String con2) {
		this.con2 = con2;
	}

	private String sendTime;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getMesType() {
		return mesType;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}

}

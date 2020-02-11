package com.example.demo.models;

public class QRData {
	
	private int action;
	private int branch;
	private int channel;
	
	public QRData() {}

	public QRData(int action, int branch, int channel) {
		super();
		this.action = action;
		this.branch = branch;
		this.channel = channel;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getBranch() {
		return branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}
	
	
	

}

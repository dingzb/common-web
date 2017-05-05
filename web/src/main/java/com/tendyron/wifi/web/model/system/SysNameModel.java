package com.tendyron.wifi.web.model.system;

import com.tendyron.wifi.web.model.BaseModel;

public class SysNameModel extends BaseModel {
	
	private String name;
	private String police;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPolice() {
		return police;
	}
	public void setPolice(String police) {
		this.police = police;
	}
	
}
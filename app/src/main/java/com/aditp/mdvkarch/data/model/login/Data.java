package com.aditp.mdvkarch.data.model.login;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("token")
	private String token;

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}
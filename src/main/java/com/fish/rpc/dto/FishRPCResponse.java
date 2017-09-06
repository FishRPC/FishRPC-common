package com.fish.rpc.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fish.rpc.util.FishRPCConfig;

public class FishRPCResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String requestId;
	private String error;
	private int code;
	private Object result;
	
	
	private long serverReceiveAtTime;
	
	private long serverStartBusinessTime;
	private long serverDoneBusinessTime;
	
	private long serverDoneSendDataTime;
	
	public FishRPCResponse(){}
	
	 
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}  

	public long getServerReceiveAtTime() {
		return serverReceiveAtTime;
	}


	public void setServerReceiveAtTime(long serverReceiveAtTime) {
		this.serverReceiveAtTime = serverReceiveAtTime;
	}


	public long getServerStartBusinessTime() {
		return serverStartBusinessTime;
	}


	public void setServerStartBusinessTime(long serverStartBusinessTime) {
		this.serverStartBusinessTime = serverStartBusinessTime;
	}


	public long getServerDoneBusinessTime() {
		return serverDoneBusinessTime;
	}


	public void setServerDoneBusinessTime(long serverDoneBusinessTime) {
		this.serverDoneBusinessTime = serverDoneBusinessTime;
	}
 
	public long getServerDoneSendDataTime() {
		return serverDoneSendDataTime;
	}


	public void setServerDoneSendDataTime(long serverDoneSendDataTime) {
		this.serverDoneSendDataTime = serverDoneSendDataTime;
	}


	public String toString() { 
        if( FishRPCConfig.getBooleanValue("fish.rpc.debug.mode", false) ){
        	ReflectionToStringBuilder.toString(this);
        }
		return requestId;
    }
}

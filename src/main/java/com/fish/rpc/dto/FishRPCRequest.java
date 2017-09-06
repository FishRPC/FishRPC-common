package com.fish.rpc.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fish.rpc.util.FishRPCConfig;
import com.fish.rpc.util.TimeUtil;

public class FishRPCRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String requestId;
	private String connection;
	private String className;
	private String methodName;
	private Class<?>[] paramsType;
	private Object[] paramsVal;
	
	
	
	private long constructionTime = System.currentTimeMillis();

	private long clientStartSendDataTime;
	private long clientDoneSendDataTime;
	

	
	private long clientAwaitAtTime;
	private long clientSignalAtTime;
	
	
	
	public FishRPCRequest(){}
	
	 
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParamsType() {
		return paramsType;
	}
	public void setParamsType(Class<?>[] paramsType) {
		this.paramsType = paramsType;
	}
	public Object[] getParamsVal() {
		return paramsVal;
	}
	public void setParamsVal(Object[] paramsVal) {
		this.paramsVal = paramsVal;
	} 
	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}
	
	public long getConstructionTime() {
		return constructionTime;
	}


	public void setConstructionTime(long constructionTime) {
		this.constructionTime = constructionTime;
	} 

	public long getClientStartSendDataTime() {
		return clientStartSendDataTime;
	}


	public void setClientStartSendDataTime(long clientStartSendDataTime) {
		this.clientStartSendDataTime = clientStartSendDataTime;
	}


	public long getClientDoneSendDataTime() {
		return clientDoneSendDataTime;
	}


	public void setClientDoneSendDataTime(long clientDoneSendDataTime) {
		this.clientDoneSendDataTime = clientDoneSendDataTime;
	}

 

	public long getClientAwaitAtTime() {
		return clientAwaitAtTime;
	}


	public void setClientAwaitAtTime(long clientAwaitAtTime) {
		this.clientAwaitAtTime = clientAwaitAtTime;
	}


	public long getClientSignalAtTime() {
		return clientSignalAtTime;
	}


	public void setClientSignalAtTime(long clientSignalAtTime) {
		this.clientSignalAtTime = clientSignalAtTime;
	}


	public String toString() {
		if( FishRPCConfig.getBooleanValue("fish.rpc.debug.mode", false) ){
	        return ReflectionToStringBuilder.toStringExclude(this, new String[]{"typeParameters", "parametersVal"});
		}
		return requestId;
    }
	
}

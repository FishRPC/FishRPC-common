package com.fish.rpc.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class FishRPCResponse {

	private String requestId;
	private String error;
	private int code;
	private Object result;
	private final long responseTime = System.currentTimeMillis();
	
	public long getResponseTime() {
		return responseTime;
	}
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
	public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

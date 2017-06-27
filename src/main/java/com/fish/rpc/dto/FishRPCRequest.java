package com.fish.rpc.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class FishRPCRequest {

	private String requestId;
	private String className;
	private String methodName;
	private Class<?>[] paramsType;
	private Object[] paramsVal;
	private final long requestTime = System.currentTimeMillis();
	
	public long getRequestTime() {
		return requestTime;
	}
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
	
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, new String[]{"typeParameters", "parametersVal"});
    }
	
}

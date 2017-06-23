package com.fish.rpc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转换成String.format格式化输出
 * @author fish
 *
 */
public  class FishRPCLog {
	
	private static final Logger logger = LoggerFactory.getLogger("FishRPC");
	
	public static void debug(String format,Object ...o){
		if(logger.isDebugEnabled() && FishRPCConfig.onDebug()){
			logger.debug(String.format(format, o));
		}
	}
	
	public static void info(String format,Object ...o){
		if(logger.isInfoEnabled()){
			logger.info(String.format(format, o));
		}
	}
	
	public static void warn(String format,Object ...o){
		if(logger.isWarnEnabled()){
			logger.warn(String.format(format, o));
		}
	}
	
	public static void error(Throwable t ,String fromat,Object ...o){
		logger.error(String.format(fromat, o), t);
	}
	
	public static void error(String fromat,Object ...o){
		logger.error(String.format(fromat, o));
	}
}

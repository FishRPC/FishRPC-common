package com.fish.rpc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FishRPCConfig {
    public static final String SYSTEM_PROPERTY_THREADPOOL_REJECTED_POLICY_ATTR = "com.fish.rpc.parallel.rejected.policy";
    public static final String SYSTEM_PROPERTY_THREADPOOL_QUEUE_NAME_ATTR = "com.fish.rpc.parallel.queue";
    public static final int PARALLEL = Math.max(2, Runtime.getRuntime().availableProcessors());
	public static Properties prop = new Properties();  
	
	public static void initClient() throws Exception{
		init("fishRPC-client.properties");
	}
	
	public static void initServer() throws Exception{
		init("fishRPC-server.properties");
	}
	
	public static String getStringValue(String key,String defaultValue){
		return prop.getProperty(key, defaultValue);
	}
	public static int getIntValue(String key,int defaultValue){
		return Integer.parseInt(String.valueOf(prop.getProperty(key,""+defaultValue)));
	}
	public static long getLongValue(String key,long defaultValue){
		return Long.parseLong(String.valueOf(prop.getProperty(key, ""+defaultValue)));
	} 
	
	public static void init(final String fileName) throws Exception{
		try {
			URL url =  Thread.currentThread().getContextClassLoader().getResource(""); 
			File file = new File(url.getFile()); 
			File fishRPC = null;
			if(file.isDirectory()){ 
				File[] files = file.listFiles(new FilenameFilter(){
					@Override
					public boolean accept(File dir, String name) { 
						return name.equals(fileName);
					} 
				});
				
				fishRPC = (files!=null&&files.length>0)?files[0]:null;
			}
			
			if(fishRPC==null || !fishRPC.exists()){
				throw new Exception("FishRPC not found "+fileName+" file.");
			}
				
			Map<String,Object> keyMap = new HashMap<String,Object>();
			
			InputStream is = ClassLoader.getSystemResourceAsStream(fishRPC.getName());
			if(null == is) {
				is = new FileInputStream(fishRPC);
			}
			prop.load(is);
			for(Object key : prop.keySet()){
				if(keyMap.containsKey((String)key)){
					throw new Exception("FishRPC配置key重复，文件="+fishRPC.getName()+",key="+key);
				}else{
					keyMap.put((String)key, null);
				}
			} 
		} catch (Exception e) { 
			throw e;
		}
	}
}

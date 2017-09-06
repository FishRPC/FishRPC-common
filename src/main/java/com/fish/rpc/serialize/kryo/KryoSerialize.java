package com.fish.rpc.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.fish.rpc.util.FishRPCLog;

public class KryoSerialize {
	
	private static final KryoPool pool = KryoPoolFactory.getKryoPoolInstance();
 
	/**
	 * 序列化（对象 -> 字节数组）
	 */
	public synchronized static  byte[] serialize(Object obj) {
      	FishRPCLog.debug("[KryoSerialize][serialize][obj:%s]", obj);
		long start = System.currentTimeMillis();
		Kryo kryo = pool.borrow();
		Output output = new Output(1, -1);
		kryo.writeClassAndObject(output, obj);
		output.flush();
		byte[] ret = output.toBytes();
		output.close();
		pool.release(kryo);
      	FishRPCLog.debug("[KryoSerialize][serialize][耗时: %s ms][obj:%s]",(System.currentTimeMillis() - start),obj);
 		return ret;
		 
	}

	/**
	 * 反序列化（字节数组 -> 对象）
	 */
	public synchronized static Object deserialize(byte[] data) {
      	FishRPCLog.debug("[KryoSerialize][deserialize][size:%s]", data.length);
		long start = System.currentTimeMillis();
		Kryo kryo = pool.borrow();	
		Input input = new Input(data);
		Object rtn = kryo.readClassAndObject(input);
		input.close();
		pool.release(kryo);
      	FishRPCLog.debug("[KryoSerialize][deserialize][size:%s][耗时: %s ms]", data.length,(System.currentTimeMillis() - start));
		return rtn;
	} 
}

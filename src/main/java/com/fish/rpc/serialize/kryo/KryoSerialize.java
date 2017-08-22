package com.fish.rpc.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.fish.rpc.util.FishRPCLog;

public class KryoSerialize {
	
	private static final KryoPool pool = KryoPoolFactory.getKryoPoolInstance();
 
	/**
	 * 序列化（对象 -> 字节数组）
	 */
	public synchronized static  byte[] serialize(Object obj) {
		FishRPCLog.debug("serialize obj %s", obj);

		long start = System.currentTimeMillis();
		Kryo kryo = pool.borrow();
		
		Output output = new Output(1, -1);
		kryo.writeClassAndObject(output, obj);
		output.flush();
		byte[] ret = output.toBytes();
		output.close();
		pool.release(kryo);
		
		FishRPCLog.debug("serialized obj %s, spend %s ms", obj,(System.currentTimeMillis() - start));
		return ret;
		 
	}

	/**
	 * 反序列化（字节数组 -> 对象）
	 */
	public synchronized static Object deserialize(byte[] data) {
		FishRPCLog.debug("deserialize data size %s byte", data.length);
		long start = System.currentTimeMillis();
		Kryo kryo = pool.borrow();	
		Input input = new Input(data);
		Object rtn = kryo.readClassAndObject(input);
		input.close();
		pool.release(kryo);
		FishRPCLog.debug("deserialized data size %s byte, spend %s ms", data.length,(System.currentTimeMillis() - start));
		return rtn;
	} 
}

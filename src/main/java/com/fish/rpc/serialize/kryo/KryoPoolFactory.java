package com.fish.rpc.serialize.kryo;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.fish.rpc.dto.FishRPCHeartbeat;
import com.fish.rpc.dto.FishRPCRequest;
import com.fish.rpc.dto.FishRPCResponse;

public class KryoPoolFactory {
	
	private static KryoPoolFactory poolFactory = null;

	private KryoFactory factory = new KryoFactory() {
		public Kryo create() {
			Kryo kryo = new Kryo();
			kryo.setReferences(true);
			// 把已知的结构注册到Kryo注册器里面，提高序列化/反序列化效率
			kryo.register(FishRPCRequest.class);
			kryo.register(FishRPCResponse.class); 
			kryo.register(FishRPCHeartbeat.class);
 			kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
			return kryo;
		}
	};

	private KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

	private KryoPoolFactory() {
	}

	public static KryoPool getKryoPoolInstance() {
		if (poolFactory == null) {
			synchronized (KryoPoolFactory.class) {
				if (poolFactory == null) {
					poolFactory = new KryoPoolFactory();
				}
			}
		}
		return poolFactory.getPool();
	}

	public KryoPool getPool() {
		return pool;
	}
}

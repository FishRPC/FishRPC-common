package com.fish.rpc.serialize.protostuff.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ProtostuffSerializePoolFactory extends BasePooledObjectFactory<ProtostuffSerialize> {

	@Override
	public ProtostuffSerialize create() throws Exception {
		return new ProtostuffSerialize();
	}

	@Override
	public PooledObject<ProtostuffSerialize> wrap(ProtostuffSerialize hessian) {
		return new DefaultPooledObject<ProtostuffSerialize>(hessian);
	}

}

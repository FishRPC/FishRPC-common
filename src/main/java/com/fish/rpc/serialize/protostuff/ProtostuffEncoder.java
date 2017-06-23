package com.fish.rpc.serialize.protostuff;

import java.io.ByteArrayOutputStream;

import com.fish.rpc.serialize.protostuff.pool.ProtostuffSerialize;
import com.fish.rpc.serialize.protostuff.pool.ProtostuffSerializePool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtostuffEncoder extends MessageToByteEncoder<Object> {
	
	private ProtostuffSerializePool pool= ProtostuffSerializePool.getProtostuffPoolInstance();
	public ProtostuffEncoder( ){
	}
	@Override
	protected void encode(ChannelHandlerContext arg0, Object msg, ByteBuf out) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try{
			ProtostuffSerialize protostuffSerialization = pool.borrow();
	        protostuffSerialization.serialize(byteArrayOutputStream, msg);
	        byte[] body = byteArrayOutputStream.toByteArray();
			out.writeInt(body.length);
			out.writeBytes(body);
			pool.restore(protostuffSerialization);
		}finally{
			byteArrayOutputStream.close();
		}
	}
}


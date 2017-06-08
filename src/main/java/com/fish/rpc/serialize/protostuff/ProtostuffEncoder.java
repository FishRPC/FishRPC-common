package com.fish.rpc.serialize.protostuff;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtostuffEncoder extends MessageToByteEncoder<Object> {
	public ProtostuffEncoder( ){
	}
	@Override
	protected void encode(ChannelHandlerContext arg0, Object msg, ByteBuf out) throws Exception {
		byte[] body = ProtostuffUtil.serializer(msg);
		out.writeInt(body.length);
		out.writeBytes(body);
	}
}


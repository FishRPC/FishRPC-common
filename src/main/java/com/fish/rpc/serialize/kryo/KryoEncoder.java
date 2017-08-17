package com.fish.rpc.serialize.kryo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KryoEncoder extends MessageToByteEncoder<Object> {

 	public KryoEncoder( ){
 		
	}
	@Override
	protected void encode(ChannelHandlerContext arg0, Object msg, ByteBuf out) throws Exception {
 		try{
			byte[] body = KryoSerialize.serialize(msg);
			out.writeInt(body.length);
			out.writeBytes(body); 
		}finally{
			 
		}
	} 
}

package com.fish.rpc.serialize.kryo;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class KryoDecoder extends ByteToMessageDecoder {
  
	 
 	public KryoDecoder(){}
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return;
        }

        in.markReaderIndex();
        int messageLength = in.readInt();

        if (messageLength < 0) {
            ctx.close();
        }

        if (in.readableBytes() < messageLength) {
            in.resetReaderIndex();
            return;
        } else {
            byte[] messageBody = new byte[messageLength];
            in.readBytes(messageBody);
            try { 
            	Object obj = KryoSerialize.deserialize(messageBody);
                out.add(obj);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}

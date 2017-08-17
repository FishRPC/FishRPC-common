package com.fish.rpc.serialize.kryo;

import java.util.List;

import com.fish.rpc.dto.FishRPCRequest;
import com.fish.rpc.dto.FishRPCResponse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class KryoDecoder extends ByteToMessageDecoder {
  
	private boolean isResponse;
 	public KryoDecoder(boolean isResponse){
		this.isResponse = isResponse;
	}
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
            	Object obj = KryoSerialize.deserialize(messageBody, isResponse?FishRPCResponse.class:FishRPCRequest.class);
                out.add(obj);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}

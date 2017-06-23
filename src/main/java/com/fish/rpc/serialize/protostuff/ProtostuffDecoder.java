package com.fish.rpc.serialize.protostuff;

import java.util.List;

import com.fish.rpc.dto.FishRPCRequest;
import com.fish.rpc.dto.FishRPCResponse;
import com.fish.rpc.serialize.protostuff.pool.ProtostuffSerialize;
import com.fish.rpc.serialize.protostuff.pool.ProtostuffSerializePool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProtostuffDecoder extends ByteToMessageDecoder {
	private ProtostuffSerializePool pool= ProtostuffSerializePool.getProtostuffPoolInstance();
	private boolean isResponse;
 	public ProtostuffDecoder(boolean isResponse){
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
            	ProtostuffSerialize decoder =  pool.borrow();
            	Object obj = decoder.deserialize(messageBody, isResponse?FishRPCResponse.class:FishRPCRequest.class);
                out.add(obj);
                pool.restore(decoder);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}


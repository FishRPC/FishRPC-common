package com.fish.rpc.serialize.protostuff.pool;

import java.io.OutputStream;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;

public class ProtostuffSerialize {
	
	private static SchemaCache cachedSchema = SchemaCache.getInstance();
    private static Objenesis objenesis = new ObjenesisStd(true);
    
    private static <T> Schema<T> getSchema(Class<T> cls) {
        return (Schema<T>) cachedSchema.get(cls);
    }
    
	public Object deserialize(byte[] input,Class cls) {
        try {
            Object message = (Object) objenesis.newInstance(cls);
            Schema<Object> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(input, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
	

    public void serialize(OutputStream output, Object object) {
        Class cls = (Class) object.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(cls);
            ProtostuffIOUtil.writeTo(output, object, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }
}

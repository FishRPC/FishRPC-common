package com.fish.rpc.serialize.kryo;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.fish.rpc.dto.FishRPCResponse;
import com.fish.rpc.util.FishRPCLog;

public class KryoSerialize {
	
	private static final KryoPool pool = KryoPoolFactory.getKryoPoolInstance();
 
	/**
	 * 序列化（对象 -> 字节数组）
	 */
	public synchronized static  byte[] serialize(Object obj) {
		FishRPCLog.debug("serialize obj %s", obj);FishRPCLog.debug("serialize obj %s", obj);
		
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
	
	public static void main(String[] args){
		
		List list1 = new ArrayList();
		list1.add(1);
		byte[] src1 = serialize(list1);
		deserialize(src1);
		
		FishRPCResponse source = new FishRPCResponse();
		List<Person> list = new ArrayList<Person>();
		for(int i=0;i<1;i++){
			Person p = new KryoSerialize().new Person();
			p.setAge(i*10);
			p.setName("测试"+i);
			list.add(p);
		}
		
		Person p = new KryoSerialize().new Person();
		p.setAge(10);
		p.setName("测试");
		p.setList(new Integer[]{1,2,4});
		 
		
		source.setResult(p);
		//source.setResult("test");
		long start = System.currentTimeMillis();
		byte[] src = serialize(source);
		System.out.println("serialize "+(System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		FishRPCResponse dist = (FishRPCResponse)deserialize(src);
		System.out.println(dist);
		System.out.println("deserialize "+(System.currentTimeMillis() - start));
	}
	
	public class Person{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int age;
		private String name;
		private Integer[] list ;
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer[] getList() {
			return list;
		}
		public void setList(Integer[] list) {
			this.list = list;
		}
		 
		
	}
}

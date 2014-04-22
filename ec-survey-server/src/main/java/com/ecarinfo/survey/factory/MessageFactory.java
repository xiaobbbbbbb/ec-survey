package com.ecarinfo.survey.factory;


public class MessageFactory {
	
	//缓存数据，避免频繁分配内存空间
//	public static final int CAPACITY1 = 1024;
//	public static final int CAPACITY2 = 512;
//	public static final int CAPACITY3 = 256;
//	private static final Map<Integer,ArrayBlockingQueue<Message[]>> MSG_MAP = new HashMap<Integer, ArrayBlockingQueue<Message[]>>();
//	private static final ArrayBlockingQueue<Message[]> QUEUE_LEN1 = new ArrayBlockingQueue<Message[]>(CAPACITY1);
//	private static final ArrayBlockingQueue<Message[]> QUEUE_LEN2 = new ArrayBlockingQueue<Message[]>(CAPACITY2);
//	private static final ArrayBlockingQueue<Message[]> QUEUE_LEN3 = new ArrayBlockingQueue<Message[]>(CAPACITY3);
	
//	static {
//		MSG_MAP.put(1, QUEUE_LEN1);
//		MSG_MAP.put(2, QUEUE_LEN2);
//		MSG_MAP.put(3, QUEUE_LEN3);
//		for(Map.Entry<Integer,ArrayBlockingQueue<Message[]>> en:MSG_MAP.entrySet()) {
//			ArrayBlockingQueue<Message[]> queue = en.getValue();
//			int size = 0;
//			if(en.getKey() == 1) {
//				size = CAPACITY1;
//			}
//			if(en.getKey() == 2) {
//				size = CAPACITY2;
//			}
//			if(en.getKey() == 3) {
//				size = CAPACITY3;
//			}
//			for(int i=0;i<size;i++) {
//				Message[] messages = new Message[en.getKey()];
//				queue.offer(messages);
//			}
//		}
//		System.out.println(MSG_MAP.size());
//		System.out.println(MSG_MAP.get(1).size());
//		System.out.println(MSG_MAP.get(2).size());
//		System.out.println(MSG_MAP.get(3).size());
//	}
	
//	public static final Message[] get(int length) {
//		try {
//			ArrayBlockingQueue<Message[]> queue = MSG_MAP.get(length);
//			if(queue == null || queue.isEmpty()) {
//				return new Message[length];
//			}
//			return queue.take();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		return null;
//		return new Message[length];
//	}
	
//	/**
//	 * 取得长度为1的Message数组
//	 * @param message
//	 * @return
//	 */
//	public static final Object get(Message message) {
//		Message[] res = get(1);
//		if(res == null) {
//			res = get(1);
//		}
//		res[0] = message;
//		return message;
//	}
	
//	public static final void free(Message[] messages) {
//		ArrayBlockingQueue<Message[]> queue = MSG_MAP.get(messages.length);
//		if(queue != null) {
//			queue.offer(messages);
//		}
//	}
	
//	public static final void show() {
//		System.err.println("QUEUE_LEN1.size="+QUEUE_LEN1.size());
//		System.err.println("QUEUE_LEN2.size="+QUEUE_LEN2.size());
//		System.err.println("QUEUE_LEN3.size="+QUEUE_LEN3.size());
//		System.out.println("====================================");
//	}
	
	public static void main(String[] args) {
//		show();
//		Message [] msg = get(1);
//		System.out.println(msg.length);
//		show();
//		free(msg);
//		show();
	}
}

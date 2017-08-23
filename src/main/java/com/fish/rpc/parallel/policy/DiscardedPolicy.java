package com.fish.rpc.parallel.policy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.fish.rpc.util.FishRPCLog;

 
public class DiscardedPolicy implements RejectedExecutionHandler {
 
    private String threadName;

    public DiscardedPolicy() {
        this(null);
    }

    public DiscardedPolicy(String threadName) {
        this.threadName = threadName;
    }

    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        if (threadName != null) {
        	FishRPCLog.error("[DiscardedPolicy][rejectedExecution][线程池耗尽][%s]", threadName);
        }

        if (!executor.isShutdown()) {
            BlockingQueue<Runnable> queue = executor.getQueue();
            int discardSize = queue.size() >> 1;
            for (int i = 0; i < discardSize; i++) {
                queue.poll();
            }

            queue.offer(runnable);
        }
    }
}

package com.fish.rpc.parallel.policy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.fish.rpc.util.FishRPCLog;
 
public class RejectedPolicy implements RejectedExecutionHandler {
 
    private final String threadName;

    public RejectedPolicy() {
        this(null);
    }

    public RejectedPolicy(String threadName) {
        this.threadName = threadName;
    }

    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        if (threadName != null) {
        	FishRPCLog.error("[RejectedPolicy][rejectedExecution][线程池耗尽][%s]", threadName);
        }

        if (runnable instanceof RejectedRunnable) {
            ((RejectedRunnable) runnable).rejected();
        } else {
            if (!executor.isShutdown()) {
                BlockingQueue<Runnable> queue = executor.getQueue();
                int discardSize = queue.size() >> 1;
                for (int i = 0; i < discardSize; i++) {
                    queue.poll();
                }

                try {
                    queue.put(runnable);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}


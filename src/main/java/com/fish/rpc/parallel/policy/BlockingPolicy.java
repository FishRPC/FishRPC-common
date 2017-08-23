package com.fish.rpc.parallel.policy;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.fish.rpc.util.FishRPCLog;

 
public class BlockingPolicy implements RejectedExecutionHandler {
 
    private String threadName;

    public BlockingPolicy() {
        this(null);
    }

    public BlockingPolicy(String threadName) {
        this.threadName = threadName;
    }

    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        if (threadName != null) {
        	FishRPCLog.error("[BlockingPolicy][rejectedExecution][线程池耗尽][%s]", threadName);
        }
        if (!executor.isShutdown()) {
            try {
                executor.getQueue().put(runnable);
            } catch (InterruptedException e) {
            }
        }
    }
}


package com.fish.rpc.parallel.policy;

import java.util.concurrent.ThreadPoolExecutor;

import com.fish.rpc.util.FishRPCLog;
 
public class CallerRunsPolicy extends ThreadPoolExecutor.CallerRunsPolicy {
 
    private String threadName;

    public CallerRunsPolicy() {
        this(null);
    }

    public CallerRunsPolicy(String threadName) {
        this.threadName = threadName;
    }

    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        if (threadName != null) {
        	FishRPCLog.error("[CallerRunsPolicy][rejectedExecution][线程池耗尽][%s]", threadName);
        }

        super.rejectedExecution(runnable, executor);
    }
}


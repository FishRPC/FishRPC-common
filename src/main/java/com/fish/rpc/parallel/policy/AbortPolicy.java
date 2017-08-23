package com.fish.rpc.parallel.policy;

import java.util.concurrent.ThreadPoolExecutor;

import com.fish.rpc.util.FishRPCLog;

public class AbortPolicy extends ThreadPoolExecutor.AbortPolicy {
 
    private String threadName;

    public AbortPolicy() {
        this(null);
    }

    public AbortPolicy(String threadName) {
        this.threadName = threadName;
    }

    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        if (threadName != null) {
        	FishRPCLog.error("[AbortPolicy][rejectedExecution][线程池耗尽][%s]", threadName);
         }
        String msg = String.format("RpcServer["
                        + " Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d),"
                        + " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)]",
                threadName, executor.getPoolSize(), executor.getActiveCount(), executor.getCorePoolSize(), executor.getMaximumPoolSize(), executor.getLargestPoolSize(),
                executor.getTaskCount(), executor.getCompletedTaskCount(), executor.isShutdown(), executor.isTerminated(), executor.isTerminating());
        FishRPCLog.error("[AbortPolicy][rejectedExecution][线程池耗尽][%s]", msg);
        super.rejectedExecution(runnable, executor);
    }
}


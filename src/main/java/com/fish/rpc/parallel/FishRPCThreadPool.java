package com.fish.rpc.parallel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import com.fish.rpc.parallel.policy.BlockingPolicy;
import com.fish.rpc.parallel.policy.DiscardedPolicy;
import com.fish.rpc.parallel.policy.RejectedPolicy;
import com.fish.rpc.parallel.policy.RejectedPolicyType;
import com.fish.rpc.util.FishRPCConfig;
import com.fish.rpc.util.FishRPCLog;

 
public class FishRPCThreadPool {
    private static RejectedExecutionHandler createPolicy() {
        RejectedPolicyType rejectedPolicyType = 
        		RejectedPolicyType.fromString(System.getProperty(FishRPCConfig.SYSTEM_PROPERTY_THREADPOOL_REJECTED_POLICY_ATTR, "AbortPolicy"));
        switch (rejectedPolicyType) {
            case BLOCKING_POLICY:
                return new BlockingPolicy();
            case CALLER_RUNS_POLICY:
                return new CallerRunsPolicy();
            case ABORT_POLICY:
                return new AbortPolicy();
            case REJECTED_POLICY:
                return new RejectedPolicy();
            case DISCARDED_POLICY:
                return new DiscardedPolicy();
        }

        return null;
    }
    private static BlockingQueue<Runnable> createBlockingQueue(int queues) {
        BlockingQueueType queueType = BlockingQueueType.fromString(System.getProperty(FishRPCConfig.SYSTEM_PROPERTY_THREADPOOL_QUEUE_NAME_ATTR, "LinkedBlockingQueue"));

        switch (queueType) {
            case LINKED_BLOCKING_QUEUE:
                return new LinkedBlockingQueue<Runnable>();
            case ARRAY_BLOCKING_QUEUE:
                return new ArrayBlockingQueue<Runnable>(FishRPCConfig.PARALLEL * queues);
            case SYNCHRONOUS_QUEUE:
                return new SynchronousQueue<Runnable>();
        }

        return null;
    }
    public static Executor getExecutor(int threads, int queues) {
     	FishRPCLog.debug("[FishRPCThreadPool][getExecutor][threads：%s][queues：%s]", threads,queues);
        String name = "FishRPCThreadPool";
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                createBlockingQueue(queues),
                new NamedThreadFactory(name, true), createPolicy());
        return executor;
    } 
}


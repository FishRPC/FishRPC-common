package com.fish.rpc.parallel.policy;

 
public interface RejectedRunnable extends Runnable {
    void rejected();
}

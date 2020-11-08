package com.denisfeier.pool;

public interface ThreadPool {

    void execute(Runnable task);

    void restart();

    void shutdown();

}

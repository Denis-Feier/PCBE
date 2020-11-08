package com.denisfeier.pool;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolImpl implements ThreadPool{

    private final int threadsCount;
    private final PoolWorker[] threads;

    private final LinkedBlockingQueue<Runnable> queue;

    public ThreadPoolImpl(int threadsCount) {
        this.threadsCount = threadsCount;
        this.queue = new LinkedBlockingQueue<Runnable>();
        this.threads = new PoolWorker[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new PoolWorker(i);
            threads[i].start();
        }
    }

    private class PoolWorker extends Thread {

        private int number;

        private boolean isRunning;

        PoolWorker(int number) {
            this.number = number;
            this.isRunning = true;
        }

        public void setRunning(boolean running) {
            this.isRunning = running;
        }

        public void run() {
            Runnable task;

            while(this.isRunning) {
                synchronized (queue) {
                    while(queue.isEmpty() && isRunning) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }

                    if(!isRunning) {
                        return;
                    }

                    task = queue.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }

        @Override
        public String toString() {
            return ThreadPoolImpl.this.toString() + " -> " + "PoolWorker #" + this.number;
        }
    }

    @Override
    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    @Override
    public void restart() {
        for (int i = 0; i < threadsCount; i++) {
            if(!threads[i].isRunning) {
                threads[i].setRunning(true);
                threads[i].start();
            }
        }
    }

    @Override
    public void shutdown() {
        for (int i = 0; i < threadsCount; i++) {
            threads[i].setRunning(false);
        }

        synchronized (queue) {
            queue.notifyAll();
        }
    }

}


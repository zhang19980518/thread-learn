package com.cloud.workthread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @author zhang
 * 工作者线程
 */
public class WorkThread {

    public static void main(String[] args) {

        Helper helper = new Helper();
        helper.init();
        //此处， helper的客户端线程为main线程
        helper.submit("Something......");
    }

    static class Helper {
        private final BlockingQueue<String> workQueue = new ArrayBlockingQueue<>(100);

        //用于处理队列workQueue中的任务的工作者线程
        private final Thread workerThread = new Thread(() -> {
            String task = null;
            while (true) {
                try {

                    task = workQueue.take();
                    System.out.println(task);
                } catch (InterruptedException e) {
                    break;
                }
                System.out.println(doProcess(task));
            }
        });

        public void init() {
            workerThread.start();
        }

        protected String doProcess(String task) {
            return task + "->processed";
        }

        public void submit(String task) {
            System.out.println("---------");
            try {
                workQueue.put(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            }
        }

    }
}

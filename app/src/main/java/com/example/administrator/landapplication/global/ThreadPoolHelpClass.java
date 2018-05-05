package com.example.administrator.landapplication.global;


import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.luoxudong.app.threadpool.ThreadTaskObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/2/11.
 * 线程管理示例
 */

public class ThreadPoolHelpClass {

    //默认线程执行
    public void TheDefaultThread() {
        new ThreadTaskObject() {
            @Override
            public void run() {
                //线程执行体
            }
        }.start();

    }

    //创建一个可缓存的线程池，并可以指定线程池名字
    public void CreateCacheThreadPool() {
        ThreadPoolHelp.Builder
                .cached()
//                .name("poolName")
                .builder()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        //线程执行体
                    }
                });

    }

    //在指定的线程池中运行
    public void SpecifiedThreadPool() {
        new ThreadTaskObject("poolName") {
            @Override
            public void run() {
                //线程执行体
            }
        }.start();
    }

    //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待，使用默认线程池，size为固定线程池大小
    public void FixedLengthThreadPool(int size) {
        ExecutorService executorService = ThreadPoolHelp.Builder
                .fixed(size)
//                .name("poolName")
                .builder();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //线程执行体
            }
        });

    }


    //创建一个单线程化的线程池,它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
    public void SingleThreadPool() {
        ThreadPoolHelp.Builder
                .single()
//                .name("poolName")
                .builder()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        //线程执行体
                    }
                });
    }

    //创建一个定长线程池定时任务
    public void FixedLengthAndTime(int size) {
        ScheduledExecutorService executorService = ThreadPoolHelp.Builder
                .schedule(size)
                .scheduleBuilder();
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                //线程执行体
            }
        }, 3000, TimeUnit.MILLISECONDS);

    }

    //自定义线程池
    public void TheCustomThreadPool() {
        ExecutorService executorService = ThreadPoolHelp.Builder
                .custom(1,//核心线程池大小
                        8,//最大线程池大小
                        3000, //线程任务空闲保留时间
                        TimeUnit.SECONDS, //线程任务空闲保留时间单位
                        new SynchronousQueue<Runnable>()) //任务等待策略
                .builder();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //线程执行体
            }
        });

    }


}

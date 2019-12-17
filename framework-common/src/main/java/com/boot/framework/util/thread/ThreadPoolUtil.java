package com.boot.framework.util.thread;

import java.util.concurrent.*;

/**
 * @author: lindj
 * @date: 2018/12/10
 * @description: 线程池定义
 */
public class ThreadPoolUtil {

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    private static volatile  ThreadPoolExecutor executor  = null;

    private ThreadPoolUtil(){

    }

    public static ThreadPoolExecutor getThreadPoolInstance(){
        if(executor == null){
            synchronized (ThreadPoolUtil.class){
                if(executor == null){
                    executor = new ThreadPoolExecutor(corePoolSize,
                            corePoolSize + 1, 10L, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>(100),
                            new MyThreadFactory(),
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return executor;
    }

    public static ThreadPoolExecutor getThreadPoolInstance(Integer corePoolSize, Integer maxCorePoolSize,
                                                 Long keepAliveTime, TimeUnit timeUnit,
                                                 BlockingDeque<Runnable> blockingDeque,
                                                 ThreadFactory threadFactory,
                                                 RejectedExecutionHandler handler){
        if(executor == null){
            synchronized (ThreadPoolUtil.class){
                if(executor == null){
                    executor = new ThreadPoolExecutor(corePoolSize,
                            maxCorePoolSize, keepAliveTime, timeUnit,
                            blockingDeque,
                            threadFactory,
                            handler);
                }
            }
        }
        return executor;
    }

}

package com.home.vod.util;

import com.home.apisdk.APIUrlConstant;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by muvi on 31/7/17.
 */

public class AppThreadPoolExecuter {
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    private BlockingQueue<Runnable> workQueue ;

    public Executor getThreadPoolExecutor(){
        workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    }

}

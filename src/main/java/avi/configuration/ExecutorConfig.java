package avi.configuration;


import avi.constants.ConfigConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author avinash.a.mishra
 */

@Configuration
public class ExecutorConfig {


/*
  corePoolSize is the minimum number of workers to keep alive without timing out
*  maxPoolSize defines the maximum number of threads that can ever be created.
   When we submit a new task to the ThreadPoolTaskExecutor, it creates a new thread if
   1.fewer than corePoolSize threads are running, even if there are idle threads in the pool,
   2.or if fewer than maxPoolSize threads are running

* */
    @Bean("fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        Integer threadPoolSize = 12;
        return new ThreadPoolExecutor(ConfigConstant.threadPoolSize, ConfigConstant.threadPoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }
}





package avi.service;


;
import avi.dto.response.AppResponse;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author avinash.a.mishra
 */
@Service
public class PartitionAndExecutionService {

    Logger logger = LoggerFactory.getLogger(PartitionAndExecutionService.class);

    @Qualifier("fixedThreadPool")
    @Autowired
    private ExecutorService executorService;


    public List<List<String>> getPartionedLists(List<String> urls) {
        Integer partition = getPartitionAsRequired(urls.size());
        return ListUtils.partition(urls, partition);
    }

    private Integer getPartitionAsRequired(int size) {
        Integer partionSize = 1;

        if(size!=0 && size > 5) {
           partionSize = size/5;
        }

        return partionSize;
    }

    public ExecutorService getExecutorServiceOfApplcation() {
        return executorService;
    }

    public ExecutorService getNewExecutorService(Integer threadPoolSIze) {
        return Executors.newFixedThreadPool(threadPoolSIze);
    }

    public <T> Future<T> executeCallable(Callable<T> callable) {
        return executorService.submit(callable);
    }

    public boolean isAllDone(ExecutorService executor) {
        boolean executorRunning = true;
        executor.shutdown();
        while (executorRunning) {
            if (executor.isTerminated()) {
                executorRunning = false;
                break;
            }
        }
        return executorRunning;
    }


    public boolean isAllDone(Future<AppResponse> futureTask) {
        boolean allDone = false;
            while (!futureTask.isDone()) {
                //wait for all each task to complete
            }

        return allDone;
    }

    public  List<AppResponse> isAllDone(List<Future<AppResponse>> futureTaskList) throws ExecutionException, InterruptedException {
        boolean allDone = false;
        List<AppResponse> responseList = new CopyOnWriteArrayList<>();
        int totalTask = futureTaskList.size();
        Integer completedTask = 0;
        for (Future<?> futureTask : futureTaskList) {
            while (!futureTask.isDone()) {
               // wait for all each future with all task to complete
            }
             completedTask++;
             AppResponse response = (AppResponse) futureTask.get();
             responseList.add(response);
            if (completedTask == totalTask) {
                logger.error("All task completed");
            }
        }

        return responseList;
    }

}

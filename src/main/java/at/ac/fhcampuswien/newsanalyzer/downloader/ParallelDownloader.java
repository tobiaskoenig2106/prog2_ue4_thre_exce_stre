package at.ac.fhcampuswien.newsanalyzer.downloader;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDownloader extends Downloader {

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public int process(List<String> urls) {
        int count = 0;
        for (String url : urls) {
            try {
                Future<?> taskStatus = executorService.submit(() -> {
                    System.out.printf("Starting Thread %s", Thread.currentThread().getName());
                    saveUrl2File(url);
                });
                count ++;
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
        executorService.shutdown();
        return count;
    }
}

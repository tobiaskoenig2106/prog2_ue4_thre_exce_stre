package at.ac.fhcampuswien.newsanalyzer.downloader;

import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader {

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public int process(List<String> urls) throws InterruptedException {
        int count = 0;
        for (String url : urls) {
            try {
                Future<?> taskStatus = executorService.submit(() -> {
                    System.out.printf("Starting Thread %s", Thread.currentThread().getName());
                    saveUrl2File(url);
                });
                count ++;
            } catch (Exception e) {
                System.out.println("Problem with thread " + Thread.currentThread().getName());
                e.printStackTrace();
            }
        }
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return count;
    }
}

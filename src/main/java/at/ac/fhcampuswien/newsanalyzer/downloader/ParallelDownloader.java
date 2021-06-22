package at.ac.fhcampuswien.newsanalyzer.downloader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader {

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public int process(List<String> urls, List<String> titles) {
        long start = System.nanoTime();
        int count = 0;
        for (String url : urls) {
            try {
                int finalCount = count;
                Future<?> taskStatus = executorService.submit(() -> {
                    System.out.printf("Starting Thread %s\n", Thread.currentThread().getName());
                    System.out.println("Downloaded: " + url);
                    saveUrl2File(url, titles.get(finalCount));
                });
                count++;
            } catch (Exception e) {
                System.out.println("Problem with Thread: " + Thread.currentThread().getName());
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

package at.ac.fhcampuswien.newsanalyzer.downloader;

import java.util.List;
import java.util.Timer;

public class SequentialDownloader extends Downloader {

    @Override
    public int process(List<String> urls) {
        long startTime = System.nanoTime();
        int count = 0;
        for (String url : urls) {
            String fileName = saveUrl2File(url);
            if(fileName != null)
                count++;
        }
        long stopTime = System.nanoTime();
        System.out.println("Time needed: " + (stopTime - startTime)*0.000000001 + " secs");
        return count;
    }
}

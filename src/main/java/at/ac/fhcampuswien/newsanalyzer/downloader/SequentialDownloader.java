package at.ac.fhcampuswien.newsanalyzer.downloader;

import java.util.List;
import java.util.Timer;

public class SequentialDownloader extends Downloader {

    @Override
    public int process(List<String> urls,List<String> titles) {
        int count = 0;
        for (String url : urls) {
            String fileName = saveUrl2File(url,titles.get(count));
            if(fileName != null)
                count++;
        }
        return count;
    }
}

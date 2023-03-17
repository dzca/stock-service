package com.demo.stocks.services.impl;

import com.demo.stocks.domain.Stock;
import com.demo.stocks.services.CsvParser;
import com.demo.stocks.services.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileProcessorImpl implements FileProcessor {
    private static final Logger log = LoggerFactory.getLogger(FileProcessorImpl.class);

    @Autowired
    private CsvParser csvParser;

    @Override
    public void processDataRoot(String directory) {
        try {
            Set<Path> directories = listFiles(directory, true);

            for (Path path : directories) {
                processFiles(path);
            }
        } catch (IOException ioe){
            log.info("Failed to read data directory; {}, error:{}", directory, ioe.getMessage());
        }
    }

    public void processFiles(Path path){
        String accountPath = path.toString();
        log.info("processFiles, accountPath= {}", accountPath);
        try{
            Set<Path> files = listFiles(accountPath, false);

            for (Path p : files) {
                List<Stock> l = csvParser.readStocks(p);

                //TODO: implement the redis inserts and file removal
            }

        } catch (IOException ioe){
            log.error("Failed to read files in account folder; {}, error:{}", accountPath, ioe.getMessage());
        }
    }

    /**
     * load file names under a directory
     * @param dir
     * @return
     */
    public Set<Path> listFiles(String dir, boolean returnDirectory) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> {
                        if(returnDirectory) {
                            return Files.isDirectory(file);
                        } else {
                            return !Files.isDirectory(file);
                        }
                    })

                    .collect(Collectors.toSet());
        }
    }

}

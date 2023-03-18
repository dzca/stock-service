package com.demo.stocks.services.impl;

import com.demo.stocks.domain.Stock;
import com.demo.stocks.services.CsvParser;
import com.demo.stocks.services.FileProcessor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileProcessorImpl implements FileProcessor {
    private static final Logger log = LoggerFactory.getLogger(FileProcessorImpl.class);

    @Autowired
    private CsvParser csvParser;

    @Value("${data.directory}")
    private String DATA_FOLDER;

    @Value("${bad.data.directory}")
    private String BAD_DATA_FOLDER;

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
        String accountName = path.getFileName().toString();
        log.debug("processFiles, accountPath={}, account={}", accountPath, path.getFileName());
        try{
            // list files under the account directory
            Set<Path> files = listFiles(accountPath, false);

            for (Path p : files) {

                log.debug("parse file start, file = {}", p.toString());

                List<Stock> stocks = csvParser.readStocks(p);

                if(stocks.isEmpty()){
                    // move the bad file to bad directory
                    backUpFile(accountName, p.getFileName().toString());

                } else {

                    //TODO: remove file
                }
                //TODO: implement the redis inserts and file removal
            }

        } catch (IOException ioe){
            log.error("Failed to read files in account folder; {}, error:{}", accountPath, ioe.getMessage());
        }
    }

    /**
     * TODO: This method could be abstract to utility function
     * @param accountName
     * @param fileName
     * @throws IOException
     */
    public void backUpFile(String accountName, String fileName)
            throws IOException {
        log.info("backup bad stock file; {}", accountName + "/" + fileName);

        Path badFilePath = Paths.get(BAD_DATA_FOLDER, accountName);

        if (!Files.exists(badFilePath)) {
            Files.createDirectories(badFilePath);
        }

        Path sourcePath = Paths.get(DATA_FOLDER, accountName, fileName);
        Path destinationPath = Paths.get(BAD_DATA_FOLDER, accountName, fileName);

        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
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

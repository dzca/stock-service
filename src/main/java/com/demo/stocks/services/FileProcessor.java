package com.demo.stocks.services;

import com.demo.stocks.domain.Stock;

import java.util.List;

/**
 * parse data files under upload directory,
 * delete file that has been read,
 * move bad file to bad directory.
 */
public interface FileProcessor {

    /**
     * read files under the directory, load the data into cache server
     * @param directory
     * @return
     */
    void processDataRoot(String directory);
}

package com.demo.stocks.controller;


import com.demo.stocks.domain.FileUploadResponse;
import com.demo.stocks.domain.Stock;
import com.demo.stocks.exception.ErrorCode;
import com.demo.stocks.exception.StockFileUploadException;
import com.demo.stocks.exception.InvalidAccountNameException;
import com.demo.stocks.repository.StockRepository;
import com.demo.stocks.services.AuthenticationService;
import com.demo.stocks.services.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="/stocks")
public class StockController {

    private static final Logger log = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private UploadService uploadService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping(value="/ticker/{ticker}")
    public ResponseEntity<List<Stock>> getByTicker (@PathVariable String ticker) {
        List<Stock> stocks = stockRepository.searchStocksByTicker(ticker);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @PostMapping(value="/upload")
    public ResponseEntity<FileUploadResponse> handleFileUpload(
            @RequestHeader("account") String accountName,
            @RequestParam("file") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes) throws InvalidAccountNameException, StockFileUploadException {
        log.info("handleFileUpload() accountName={}", accountName);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        if(!authenticationService.isAccountNameValid(accountName)){
            throw new InvalidAccountNameException(ErrorCode.INVALID_ACCOUNT);
        }
        try{
            uploadService.saveFile(accountName, fileName, multipartFile);
        } catch (IOException e){
            log.error("File upload error", e.getMessage());
            throw new StockFileUploadException(ErrorCode.FILE_UPLOAD_ERROR, e);
        }

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value="/")
    public ResponseEntity<Stock> saveStock(
            @RequestBody Stock stock) {

        stockRepository.saveStock(stock);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }
}

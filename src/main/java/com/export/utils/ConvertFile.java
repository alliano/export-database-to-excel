package com.export.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConvertFile {

    public static File convert(MultipartFile multipartFile){
        File covertFile = new File(System.getProperty("user.dir")+"/src/main/java/com/export/tmp/"+UUID.randomUUID().toString()+".xlsx");
        try {
            covertFile.createNewFile();
            FileOutputStream outputStrem = new FileOutputStream(covertFile);
            outputStrem.write(multipartFile.getBytes());
            outputStrem.close();
            log.info("success write file");
        } catch (IOException IOX) {
            covertFile.delete();
            covertFile = null;
            throw new RuntimeException(IOX.getMessage());
        }
        return covertFile;
    }
}
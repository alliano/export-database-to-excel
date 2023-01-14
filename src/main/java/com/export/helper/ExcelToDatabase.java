package com.export.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.export.utils.ConvertFile;
import com.export.utils.ExecuteXlsx;

@Configuration
public class ExcelToDatabase {

    private XSSFWorkbook workbook = new XSSFWorkbook();

    private final ExecuteXlsx executeXlsx;

    public ExcelToDatabase(ExecuteXlsx executeXlsx){
        this.executeXlsx = executeXlsx;
    }



    public void execute(MultipartFile excelFile) throws IOException {
        File file = ConvertFile.convert(excelFile);
        FileInputStream inputStrem = new FileInputStream(file);
        ZipSecureFile.setMinInflateRatio(0);
        this.workbook = new XSSFWorkbook(inputStrem);

        XSSFSheet sheet = this.workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
            List<String> header = new ArrayList<>();
            Row headerRow = rowIterator.next();

            List<List<String>> datas = new ArrayList<List<String>>();
            headerRow.forEach(h -> header.add(h.getStringCellValue()));
            rowIterator.forEachRemaining(r -> {
                List<String> dataList = new ArrayList<String>();
                Iterator<Cell> cellIterator =  r.iterator();
                cellIterator.forEachRemaining(c -> {
                dataList.add(c.getStringCellValue());
                });
                datas.add(dataList);
            });

            datas.removeIf(data -> data.isEmpty());
            this.executeXlsx.batchInsert();
            file.delete();
    }





}

package com.export.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;

import com.export.domains.entities.User;

@Configuration
public class WriteExcel {

    public void dataUser(Iterable<User> users){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet;
        int rowNum = 0;
        sheet = workbook.createSheet("users");
        for (User user : users) {
            rowNum++;
            Row row = sheet.createRow(rowNum);
            Cell cell1 = row.createCell(0);
            Cell cell2 = row.createCell(1);
            Cell cell3 = row.createCell(2);
            Cell cell4 = row.createCell(3);
            if(rowNum == 1){
                cell1.setCellValue("id");
                cell2.setCellValue("name");
                cell3.setCellValue("email");
                cell4.setCellValue("password");
            }
            else{
                cell1.setCellValue(user.getId());
                cell2.setCellValue(user.getName());
                cell3.setCellValue(user.getEmail());
                cell4.setCellValue(user.getPassword());
            }
        }
        try {
            FileOutputStream fileOutput = new FileOutputStream(new File("test.xlsx"));
            workbook.write(fileOutput);
            fileOutput.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

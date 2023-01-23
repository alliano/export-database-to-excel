package com.export.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.export.dtos.TableDto;
import com.export.utils.ConvertFile;
import com.export.utils.ExecuteXlsx;

@Configuration
public class ExcelToDatabase {

    private XSSFWorkbook workbook = new XSSFWorkbook();

    private final ExecuteXlsx executeXlsx;

    public ExcelToDatabase(ExecuteXlsx executeXlsx){
        this.executeXlsx = executeXlsx;
    }

    public void execute(MultipartFile excelFile) {
        try {
            //convert file dari Object MultipartFile menjadi File
            File file = ConvertFile.convert(excelFile);
            FileInputStream inputStrem = new FileInputStream(file);
            // untuk meng meng izinkan jika data excel nya sangat banayak
            ZipSecureFile.setMinInflateRatio(0);
            this.workbook = new XSSFWorkbook(inputStrem);
            // ambil sheet pertama
            XSSFSheet sheet = this.workbook.getSheetAt(0);
            // pisahkan tabel header dengan body nya
            TableDto table = this.executeXlsx.sperate(sheet);
            // buat schema database nya
            this.executeXlsx.createSchema(table.getHeader(), table.getDataType(), sheet.getSheetName());
            // insert semua data kedalam database
            this.executeXlsx.batchInsert(table.getBody(), sheet.getSheetName(), table.getHeader());
            // hapus file uploadnya karna sudah tidak digunakan
            if(file.exists()) file.delete();
        } catch (IOException IOX) {
            IOX.printStackTrace();
            throw new RuntimeException(IOX.getMessage(), IOX.getCause());
        } catch (SQLException SQLEX) {
            SQLEX.printStackTrace();
            throw new RuntimeException(SQLEX.getMessage(), SQLEX.getCause());
        }

    }





}

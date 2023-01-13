package com.export.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.sql.DataSource;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.export.utils.ConvertFile;

@Configuration
public class ExcelToDatabase {

    private XSSFWorkbook workbook = new XSSFWorkbook();

    private final DataSource dataSource;

    public ExcelToDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void execureQuery() throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        String ddl = """
                """;
        statement.execute(ddl);
        connection.close();
        statement.close();
    }

    public void createSchema(MultipartFile excelFile) throws IOException {
        File file = ConvertFile.convert(excelFile);
        FileInputStream inputStrem = new FileInputStream(file);
        ZipSecureFile.setMinInflateRatio(0);
        this.workbook = new XSSFWorkbook(inputStrem);
        XSSFSheet sheet = this.workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while(rowIterator.hasNext()) {

        }

    }



}

package com.export.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.context.annotation.Configuration;

import com.export.dtos.TableDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration @AllArgsConstructor
public class ExecuteXlsx {

    private final DataSource dataSource;

    private final SqlUtil sqlUtil;

    public void createSchema(List<String> tableHeader, List<DataType> dataType, String tableName) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String ddl = this.sqlUtil.ddlBuilder(tableHeader, dataType, tableName);
            System.out.println(ddl);
            statement.execute(ddl);
            connection.close();
            statement.close();
            log.info("Success create a schema");
        } catch (SQLException SQLEX) {
            log.info("entity Exist");
        }
    }

    public TableDto sperate(XSSFSheet sheet){
        Iterator<Row> rowIterator = sheet.iterator();
        List<String> header = new ArrayList<>();
        TableDto table = new TableDto();
        List<List<String>> body = new ArrayList<List<String>>();
        rowIterator.next().forEach( h -> header.add(h.getStringCellValue()));
        header.removeIf( h -> h.isEmpty());
        table.setHeader(header);
        int dataTypeCounter = 0;
        while(rowIterator.hasNext()) {
            List<String> dataList = new ArrayList<>();
            if(dataTypeCounter == 0){
                rowIterator.next().forEach( cell -> {
                    if(cell.getCellType() == CellType.STRING){
                        dataList.add(cell.getStringCellValue());
                        table.addDataType(DataType.STRING);
                    }
                    else if(cell.getCellType() == CellType.NUMERIC) {
                        dataList.add(String.valueOf(cell.getNumericCellValue()));
                        table.addDataType(DataType.INTEGER);
                    }
                    else if(cell.getCellType() == CellType.BOOLEAN) {
                        dataList.add(String.valueOf(cell.getBooleanCellValue()));
                        table.addDataType(DataType.BOLLEAN);
                    }
                });
                dataList.removeIf(d -> d.isEmpty());
                body.add(dataList);
                dataTypeCounter++;
            }
            else {
                rowIterator.next().forEach( cell -> {
                    if(cell.getCellType() == CellType.STRING){
                        dataList.add(cell.getStringCellValue());
                    }
                    else if(cell.getCellType() == CellType.NUMERIC) {
                        dataList.add(String.valueOf(cell.getNumericCellValue()));
                    }
                    else if(cell.getCellType() == CellType.BOOLEAN) {
                        dataList.add(String.valueOf(cell.getBooleanCellValue()));
                    }
                });
                dataList.removeIf(d -> d.isEmpty());
                body.add(dataList);
                body.removeIf(d -> d.isEmpty());
                table.setBody(body);
            }
        }
        return table;
    }

    public void batchInsert(List<List<String>> bodyOfTable, String entityName, List<String> tableHeader) {
        String data = this.sqlUtil.batchInsert(bodyOfTable, entityName, tableHeader);
        System.out.println(data);
        try {
            this.dataSource.getConnection().createStatement().executeUpdate(data);
        } catch (SQLException SQLEX) {
            SQLEX.printStackTrace();
            throw new RuntimeException(SQLEX.getMessage(), SQLEX.getCause());
        }
    }
}

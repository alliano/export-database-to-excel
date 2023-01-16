package com.export.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.context.annotation.Configuration;

import com.export.dtos.TableDto;
import lombok.AllArgsConstructor;


@Configuration @AllArgsConstructor
public class ExecuteXlsx {

    private final DataSource dataSource;

    public void createSchema(List<String> tableHeader, List<DataType> dataType, String tableName) throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        tableHeader = tableHeader.stream().map(l -> {
            String[] replaceSpace = l.split(" ");
            return Arrays.asList(replaceSpace).stream().reduce(( first, last ) -> {
                return first.concat("_").concat(last);
            }).get();
        }).collect(Collectors.toList());
        String ddl = SqlUtil.ddlBuilder(tableHeader, dataType, tableName);
        statement.execute(ddl);
        connection.close();
        statement.close();
    }

    public TableDto sperate(XSSFSheet sheet){
        Iterator<Row> rowIterator = sheet.iterator();
        List<String> header = new ArrayList<>();
        Row headerRow = rowIterator.next();
        TableDto table = new TableDto();
        headerRow.forEach(h -> header.add(h.getStringCellValue()));
        Row takeDataType = rowIterator.next();
        takeDataType.forEach(c -> {
            if(c.getCellType() == CellType.STRING ) table.addDataType(DataType.STRING);
            else if(c.getCellType() == CellType.NUMERIC ) table.addDataType(DataType.INTEGER);
            else if(c.getCellType() == CellType.BOOLEAN) table.setDataType(List.of(DataType.BOLLEAN));
        });
        List<List<String>> body = new ArrayList<List<String>>();
        rowIterator.forEachRemaining(r -> {
            List<String> dataList = new ArrayList<String>();
            Iterator<Cell> cellIterator =  r.iterator();
            cellIterator.forEachRemaining(c -> {
                if(c.getCellType() == CellType.NUMERIC){
                    String intToStr = String.valueOf(c.getNumericCellValue());
                    dataList.add(intToStr);
                }
                else if(c.getCellType() == CellType.STRING) {
                    dataList.add(c.getStringCellValue());
                }
            });
                body.add(dataList);
        });
        body.removeIf(data -> data.isEmpty());
        table.setHeader(header);
        table.setBody(body);
        return table;
    }

    public void batchInsert(List<List<String>> bodyOfTable){

    }
}

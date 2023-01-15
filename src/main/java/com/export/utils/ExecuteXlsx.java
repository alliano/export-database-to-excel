package com.export.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public void createSchema(List<String> patren) throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        String ddl = """
                """;
        statement.execute(ddl);
        connection.close();
        statement.close();
    }

    public void batchInsert(List<List<String>> bodyOfTable){

    }

    public TableDto sperate(XSSFSheet sheet){
        // ubah mejadi iterator untuk di loop
        Iterator<Row> rowIterator = sheet.iterator();
        List<String> header = new ArrayList<>();
        // select headernya
        Row headerRow = rowIterator.next();
        // masukan tabel headernya ke variabel header
        headerRow.forEach(h -> header.add(h.getStringCellValue()));
        TableDto table = new TableDto();
        List<List<String>> body = new ArrayList<List<String>>();
        // loop body tabel nya
        rowIterator.forEachRemaining(r -> {
            List<String> dataList = new ArrayList<String>();
            Iterator<Cell> cellIterator =  r.iterator();
            // loop cell nya
            cellIterator.forEachRemaining(c -> {

                if(c.getCellType() == CellType.NUMERIC){
                    table.setDataType(List.of(DataType.INTEGER));

                }
                else if(c.getCellType() == CellType.STRING) {
                    table.setDataType(List.of(DataType.STRING));
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
}

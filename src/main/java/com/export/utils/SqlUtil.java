package com.export.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlUtil {

    public static String ddlBuilder(List<String> tableHeader, List<DataType> dataType, String tableName){
        String[] replaceSpace = tableName.split(" ");
        tableName = Arrays.asList(replaceSpace).stream().reduce(( first, last ) -> {
            return first.concat("_").concat(last);
        }).get();
        List<String> prepareStmt = new ArrayList<String>();
        prepareStmt.add("CREATE TABLE "+tableName+"(");
        for (int i = 0; i < tableHeader.size(); i++) {
            if(dataType.get(i) == DataType.STRING) {
                prepareStmt.add(" ".concat(tableHeader.get(i).concat(" VARCHAR(100)")));
            }
            else if(dataType.get(i) == DataType.INTEGER){
                prepareStmt.add(" ".concat(tableHeader.get(i).concat(" INT NOT NULL")));
            }
            else if(dataType.get(i) == DataType.BOLLEAN){
                prepareStmt.add(" ".concat(tableHeader.get(i).concat(" BOOLEAN NOT NULL")));
            }
        }
        String stmt = prepareStmt.toString().replaceFirst("]", ");");
        String rep = stmt.replace("[", "");
        String ddl = rep.replaceFirst(",", "id INT NOT NULL PRIMARY KEY,");
        return ddl;
    }
}

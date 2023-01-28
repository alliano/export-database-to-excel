package com.export.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlUtil {

    public String ddlBuilder(List<String> tableHeader, List<DataType> dataType, String tableName){
        tableHeader.removeIf(d -> d.isEmpty() || d.equalsIgnoreCase("id"));
        tableName = FormatterString.toSnakeCase(tableName);
        tableHeader = FormatterString.toSnakeCase(tableHeader);
        List<String> prepareStmt = new ArrayList<String>();
        prepareStmt.add("CREATE TABLE ".concat(tableName).concat("("));
        for (int i = 0; i < tableHeader.size(); i++) {
            if(dataType.get(i) == DataType.STRING) {
                if(tableHeader.get(i).matches("^description")||tableHeader.get(i).matches("[a-zA-Z_]+description*")) {
                    prepareStmt.add(" ".concat(tableHeader.get(i).concat(" VARCHAR(500)")));
                }
                else if(tableHeader.get(i).equalsIgnoreCase("name") || tableHeader.get(i).equalsIgnoreCase("email")){
                    prepareStmt.add(" ".concat(tableHeader.get(i).concat(" VARCHAR(50)")));
                }
                else {
                    prepareStmt.add(" ".concat(tableHeader.get(i).concat(" VARCHAR(255)")));
                }
            }
            else if(dataType.get(i) == DataType.INTEGER){
                prepareStmt.add(" ".concat(tableHeader.get(i).concat(" INT NOT NULL")));
            }
            else if(dataType.get(i) == DataType.BOLLEAN){
                prepareStmt.add(" ".concat(tableHeader.get(i).concat(" BOOLEAN NOT NULL")));
            }
        }
        if(!prepareStmt.contains("id")) {
            prepareStmt.add(1, "id SERIAL NOT NULL PRIMARY KEY ");
        }
        else {
            prepareStmt.stream().map( d -> {
                if(d == "id") {
                    d.concat("SERIAL NOT NULL PRIMARY KEY ");
                }
                return d;
            });
        }
        return prepareStmt.toString().replaceAll("[\\[\\]]", "").replaceFirst(",", "").concat(");");
    }

    public String batchInsert(List<List<String>> bodyOfTable, String entityName, List<String> tableHeader) {
        String dml = "";
        for (List<String> list : bodyOfTable) {
            list = list.stream().map( d -> {
               if(Pattern.compile("(true|false)",Pattern.CASE_INSENSITIVE).matcher(d).find(0)){
                    return d;
                }
                else if(Pattern.compile("^[0-9]+$").matcher(d).find(0)){
                    return d;
                }
                else if(Pattern.compile("[^\\s,\\.\\(\\)]'[^\\s,\\.\\(\\)]").matcher(d).find(0)){
                     Matcher matcher = Pattern.compile("[^\\s,\\.\\(\\)]'[^\\s,\\.\\(\\)]").matcher(d);
                     String strReplacement = new String();
                     if(matcher.find(0)){
                            String fristword = d.replaceAll("'", "");
                            String group = matcher.group(0).replaceAll("'", "''");
                            strReplacement = fristword+group;
                     }
                     return "\'"+strReplacement+"\'";
                }
                else {
                    try {
                        int parseInt = Integer.parseInt(String.valueOf(((int)Double.parseDouble(d))));
                        return String.valueOf(parseInt);
                    } catch (Exception e) {
                        return "\'"+d+"\'";
                    }
                }
            }).collect(Collectors.toList());
            String data = list.toString().replaceAll("[\\[]", "(").replaceAll("[\\]]", "),");
            dml = dml.concat(data);
        }
        return "INSERT INTO ".concat(entityName)
        .concat(FormatterString.toSnakeCase(tableHeader).toString().replaceAll("[\\[]", "(")
        .replaceAll("[\\]]", ")")).concat(" VALUES")
        .concat(dml.replaceAll("[^a-zA-Z0-9@\\(\\)\\.\\s]$", ";"));
    }


}

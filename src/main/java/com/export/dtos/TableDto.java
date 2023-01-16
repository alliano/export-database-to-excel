package com.export.dtos;

import java.util.ArrayList;
import java.util.List;

import com.export.utils.DataType;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TableDto {

    private List<String> header;

    private List<List<String>> body;

    private List<DataType> dataType = new ArrayList<>();

    public void addDataType(DataType dataType){
        this.dataType.add(dataType);
    }
}

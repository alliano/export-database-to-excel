package com.export.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;

import com.export.domains.entities.User;
import com.export.dtos.UserDetailDto;

@Configuration
public class WriteExcel {

    private XSSFWorkbook workbook = new XSSFWorkbook();

    public void dataUser(Iterable<User> users){
        int rowNum = 0;
        XSSFSheet sheet = this.workbook.createSheet("users");
        for (User user : users) {
            rowNum++;
            Row row = sheet.createRow(rowNum);
            Cell cell1 = row.createCell(0);
            Cell cell2 = row.createCell(1);
            Cell cell3 = row.createCell(2);
            Cell cell4 = row.createCell(3);
            if(rowNum == 1){
                List<Cell> cels = List.of(cell1, cell2, cell3, cell4);
                for(Cell cell : cels) {
                    CellStyle cellStyle = cell.getCellStyle();
                    cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                    cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.index);
                    cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
                    cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
                    cell.setCellStyle(cellStyle);
                }
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
    }

    public void userDetails(List<UserDetailDto> users) {

        // XSSFSheet sheet = this.workbook.createSheet("user details");
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.size());
        }

    }

    public void execute() throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(new File("test.xlsx"));
        this.workbook.write(fileOutput);
        fileOutput.close();
        this.workbook.close();
    }
}

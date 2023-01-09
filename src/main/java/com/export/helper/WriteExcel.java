package com.export.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;

import com.export.domains.entities.User;
import com.export.dtos.UserDetailDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WriteExcel {

    private XSSFWorkbook workbook = new XSSFWorkbook();

    // method ini digunakan untuk mengeksport data user
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

    // method ini digunakan untuk mengeksport user secara detail
    public void userDetails(List<UserDetailDto> users) {

        XSSFSheet sheet = (this.workbook.getSheet("user details") == null ? this.workbook.createSheet("user details") : this.workbook.getSheet("user details"));
        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
        for (int i = 0; i < users.size(); i++) {

            /*
             * buat baris dan kolom sesuai dengan data yang ingin di export
             * dalam case ini bentuk data yang akan di export sebagai berikut
             * secure id => string
             * name => string
             * email => string
             * password => string
             * role => string
             * role description => string
             * */
            Row row = sheet.createRow(i);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);
            Cell cell5 = row.createCell(5);
            Cell cell6 = row.createCell(6);
            if(i == 0){
                // beri style kepada tabel heder nya
                List<Cell> cells = List.of(cell1, cell2, cell3, cell4, cell5, cell6);
                for (Cell cell : cells) {
                    CellStyle cellStyle = cell.getCellStyle();
                    cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                    cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.index);
                    cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
                    cellStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.index);
                    cell.setCellStyle(cellStyle);
                }
                // buat cell nya
                cell1.setCellValue("secure id");
                cell2.setCellValue("name");
                cell3.setCellValue("email");
                cell4.setCellValue("password");
                cell5.setCellValue("role");
                cell6.setCellValue("role description");
            }
            else {
                /**
                 * beri warna cell untuk role name nya
                 * jika role name nya Admin maka warna cell nya Aqua
                 * jika role name nya User maka warna cell nya Merah
                 */
                cell1.setCellValue(users.get(i).getSecureId());
                cell2.setCellValue(users.get(i).getName());
                cell3.setCellValue(users.get(i).getEmail());
                cell4.setCellValue(users.get(i).getPassword());
                cell5.setCellValue(users.get(i).getRoleNaname());
                cell6.setCellValue(users.get(i).getRoleDescription());
            }
            ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule("=IF(F"+i+"=Admin)");
            ConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule("=IF(F"+i+"=User)");
            PatternFormatting fill1 = rule1.createPatternFormatting();
            fill1.setFillBackgroundColor(IndexedColors.AQUA.index);
            fill1.setFillPattern(PatternFormatting.BIG_SPOTS);
            PatternFormatting fill2 = rule2.createPatternFormatting();
            fill2.setFillBackgroundColor(IndexedColors.LIGHT_YELLOW.index);
            fill2.setFillPattern(PatternFormatting.BIG_SPOTS);
            CellRangeAddress[] regions = {
                CellRangeAddress.valueOf("B1:G1")
            };
            sheetCF.addConditionalFormatting(regions, rule1);
            sheetCF.addConditionalFormatting(regions, rule2);

            log.info("=IF(F"+i+"='Admin')");
            log.info("=IF(F"+i+"='User')");
        }
    }

    // method ini digunakan untuk menulis file excel nya
    public void execute() throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(new File("test.xlsx"));
        this.workbook.write(fileOutput);
        fileOutput.close();
        this.workbook.close();
        log.info("success export -:)");
    }
}

package com.vistula.magazyn.service;

import com.vistula.magazyn.domain.Produkt;
import com.vistula.magazyn.repository.ProduktRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ProduktXlsxService {
    private final ProduktRepository produktRepository;

    private final Logger log = LoggerFactory.getLogger(ProduktXlsxService.class);

    public ProduktXlsxService(ProduktRepository produktRepository) {
        this.produktRepository = produktRepository;
    }

    public List<Produkt> getAllProdukts() {
        return produktRepository.findAll();
    }

    static void createCell(CellStyle cellStyle, Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    public byte[] utworzXlsxProdukty() throws IOException, NoSuchFieldException {
        List<Produkt> allProdukty = getAllProdukts();
        return utworzProduktyXlsx(allProdukty);
    }

    public byte[] utworzProduktyXlsx(List<Produkt> produktList) throws IOException, NoSuchFieldException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Create Workbook instance holding .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a new Worksheet
        XSSFSheet sheet = workbook.createSheet("WindykacjaFaktura");

        CellStyle cs = workbook.createCellStyle();
        cs.setAlignment(HorizontalAlignment.LEFT);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);

        int rowNum = 0;
        int colNum = 0;
        Row header = sheet.createRow(rowNum);

        createCell(cs, header, colNum++, "nazwa");
        createCell(cs, header, colNum, "opis");

        int rownum = 1;

        log.info("Lista Produktów:");

        for (Produkt p : produktList) {
            log.info(p.toString());

            try {

                Row row = sheet.createRow(rownum++);
                colNum = 0;

                createCell(cs, row, colNum++, p.getNazwa());
                createCell(cs, row, colNum, p.getOpis());

            } catch (Exception e) {
                log.error("Błąd:", e);
            }
        }
        //Write workbook into the excel
        workbook.write(baos);
        baos.close();
        byte[] targetArray = baos.toByteArray();
        //Close the workbook
        workbook.close();
        return targetArray;
    }

    public void savaXlsxFile(byte[] bytesArray, String fileDest) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileDest);
        fos.write(bytesArray);
        fos.close();
    }

    //@Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void getProduktyXlsxFile() throws IOException, NoSuchFieldException {
        String fileDestination = "D:/PIMK/magazyn-grzewczy/src/main/resources/templates/mail/zalaczniki/test.xlsx";
        savaXlsxFile(utworzXlsxProdukty(),fileDestination);
    }
}

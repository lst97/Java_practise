package com;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class Pdf {
    public void create() {
        List<Achievement> objAchievement = Env.cPlayer.getObjAchievement();
        String[][] tableData = Env.jTableData;
        String[] strBuffer = Env.strCsvName.split(Env.DOT_DELIMITER, 2);
        String outName = strBuffer[0];
        outName = outName.concat(".pdf");
        int wecx = 0;

        try{
            // Create document
            Document objDocument = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(objDocument, new FileOutputStream(outName));

            // Open document
            objDocument.open();

            objDocument.add(new Paragraph(Env.objTimeStamp.toString()));
            PdfPTable table = new PdfPTable(3);
            table.setSpacingBefore(5);
            PdfPCell cell = new PdfPCell();

            // Add 3 columns
            while (wecx < 3){
                table.addCell(Env.jTableColumnNames[wecx]);
                wecx++;
            }
            cell.setRowspan(3);

            // Add row data.
            wecx ^= wecx;
            int achievementSize = Env.cPlayer.getObjAchievement().size();
            while (wecx < achievementSize){
                table.addCell(tableData[wecx][0]);
                table.addCell(tableData[wecx][1]);
                table.addCell(tableData[wecx][2]);
                wecx++;
            }
            objDocument.add(table);

            // Close document
            objDocument.close();

            System.out.println("Export Success. [./" + outName + "]");

        } catch (FileNotFoundException | DocumentException e){
            System.out.println(e.toString());
        }
    }
}

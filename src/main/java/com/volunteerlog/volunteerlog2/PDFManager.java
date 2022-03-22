package com.volunteerlog.volunteerlog2;

import java.io.File;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import javafx.application.HostServices;

//Implemented using the iText Core 7 Library

/*
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.
*/

public class PDFManager {
    private PdfDocument pdf;
	private Document document;
	private HostServices hostServices;
	private String PATH_TO_BLANK;

    PDFManager(HostServices host){
		hostServices = host;
		String dir = System.getProperty("user.dir");
        PATH_TO_BLANK = dir + "\\res\\pdf\\BlankVolunteerForm.pdf";

    }
	public void saveToPDF(Entry entry, String filePath){
		Entry currEntry = entry;
		String dir = System.getProperty("user.dir");
		String PATH_TO_OUTPUT = dir + "\\output\\" + filePath;
		try {
            pdf = new PdfDocument(new PdfReader(PATH_TO_BLANK), new PdfWriter(PATH_TO_OUTPUT));
			document = new Document(pdf, PageSize.LETTER);

            PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
			//Fields
		    PdfTextFormField lastNameField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 660, 150, 15), "lastName", "");
			lastNameField.setFontSize(10);
			form.addField(lastNameField);
			PdfTextFormField firstNameField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(205, 660, 150, 15), "firstName", "");
			firstNameField.setFontSize(10);
			form.addField(firstNameField);
			PdfTextFormField gradYearField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(365, 660, 65, 15), "gradYear", "");
			gradYearField.setFontSize(10);
			form.addField(gradYearField);
			PdfTextFormField phoneNumField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(440, 660, 100, 15), "phoneNum", "");
			phoneNumField.setFontSize(10);
			form.addField(phoneNumField);
			PdfTextFormField orgNameField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 625, 500, 15), "orgName", "");
			orgNameField.setFontSize(10);
			form.addField(orgNameField);

			PdfTextFormField date1Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 594, 100, 8), "date1", "");
			date1Field.setFontSize(8);
			form.addField(date1Field);
			PdfTextFormField date2Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 582, 100, 8), "date2", "");
			date2Field.setFontSize(8);
			form.addField(date2Field);
			PdfTextFormField date3Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 570, 100, 8), "date3", "");
			date3Field.setFontSize(8);
			form.addField(date3Field);
			PdfTextFormField date4Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 558, 100, 8), "date4", "");
			date4Field.setFontSize(8);
			form.addField(date4Field);
			PdfTextFormField date5Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 546, 100, 8), "date5", "");
			date5Field.setFontSize(8);
			form.addField(date5Field);

			PdfTextFormField hour1Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(175, 594, 100, 8), "hour1", "");
			hour1Field.setFontSize(8);
			form.addField(hour1Field);
			PdfTextFormField hour2Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(175, 582, 100, 8), "hour2", "");
			hour2Field.setFontSize(8);
			form.addField(hour2Field);
			PdfTextFormField hour3Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(175, 570, 100, 8), "hour3", "");
			hour3Field.setFontSize(8);
			form.addField(hour3Field);
			PdfTextFormField hour4Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(175, 558, 100, 8), "hour4", "");
			hour4Field.setFontSize(8);
			form.addField(hour4Field);
			PdfTextFormField hour5Field = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(175, 546, 100, 8), "hour5", "");
			hour5Field.setFontSize(8);
			form.addField(hour5Field);

			PdfTextFormField totalHoursField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(450, 534, 100, 8), "totalHours", "");
			totalHoursField.setFontSize(8);
			form.addField(totalHoursField);

			PdfTextFormField prepareAreaField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 425, 510, 55), "prepareArea", "");
			prepareAreaField.setFontSize(10);
			form.addField(prepareAreaField);

			PdfTextFormField accomplishAreaField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 335, 510, 65), "accomplishArea", "");
			accomplishAreaField.setFontSize(10);
			form.addField(accomplishAreaField);

			PdfTextFormField learnAreaField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 245, 510, 55), "learnArea", "");
			learnAreaField.setFontSize(10);
			form.addField(learnAreaField);

			PdfTextFormField supervisorNameField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(50, 110, 200, 15), "supervisorName", "");
			supervisorNameField.setFontSize(10);
			form.addField(supervisorNameField);

			PdfTextFormField supervisorTitleField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(265, 110, 130, 15), "supervisorTitle", "");
			supervisorTitleField.setFontSize(10);
			form.addField(supervisorTitleField);

			PdfTextFormField supervisorPhoneField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(410, 110, 140, 15), "supervisorPhone", "");
			supervisorPhoneField.setFontSize(10);
			form.addField(supervisorPhoneField);

			PdfTextFormField supervisorEmailField = PdfTextFormField.createText(document.getPdfDocument(), new Rectangle(410, 88, 140, 15), "supervisorEmail", "");
			supervisorEmailField.setFontSize(10);
			form.addField(supervisorEmailField);
			

			for(String key : currEntry.getFields().keySet()){
				if(form.getFormFields().get(key) != null){
					form.getFormFields().get(key).setValue(currEntry.getFields().getString(key));
				}
			}
 
		    document.close();

			File outputFile = new File(PATH_TO_OUTPUT);

			//Open in default browser
			new Thread(()->{
				hostServices.showDocument(outputFile.getAbsolutePath());
			}).start();
		} catch (Exception e) {
		    e.printStackTrace();
	    }
	}
}

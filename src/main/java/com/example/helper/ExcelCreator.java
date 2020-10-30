package com.example.helper;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ExcelCreator {
	static String SHEET="EmployeeList";

	public XSSFWorkbook exportInExcel(List<Employee> listEmp) {
		  String filePath = "C:\\Users\\hungh\\Desktop\\ab.png"; 
		  int size = 200; 
		  String fileType = "png";
		  File qrFile = new File(filePath);
		XSSFWorkbook wb =null;
		try {
			wb= new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Employee list");
			
			Row rowHeading =sheet.createRow(0);
			rowHeading.createCell(0).setCellValue("name");
			rowHeading.createCell(1).setCellValue("PassWord");
			rowHeading.createCell(2).setCellValue("email");
			rowHeading.createCell(3).setCellValue("Designation");
			rowHeading.createCell(4).setCellValue("Salary");
			rowHeading.createCell(5).setCellValue("QR code");
			for(int i=0; i<6 ; i++) {
				CellStyle styleRowHeading =wb.createCellStyle();
				styleRowHeading.setBorderBottom(BorderStyle.MEDIUM);
				styleRowHeading.setBorderLeft(BorderStyle.MEDIUM);
				styleRowHeading.setBorderRight(BorderStyle.MEDIUM);
				styleRowHeading.setBorderTop(BorderStyle.MEDIUM);
				Font font =wb.createFont();
				font.setBold(true);
				font.setFontHeightInPoints((short)11);
				styleRowHeading.setFont(font);
				rowHeading.getCell(i).setCellStyle(styleRowHeading);
			}
			int r=1;
			for(Employee emp :listEmp) {
				Row row = sheet.createRow(r);
				sheet.setDefaultRowHeight((short)1000);
				Cell cellName = row.createCell(0) ;
				cellName.setCellValue(emp.getUname());
				
				Cell cellPass = row.createCell(1);
				cellPass.setCellValue(emp.getUpass());
				
				Cell cellEmail = row.createCell(2);
				cellEmail.setCellValue(emp.getUemail());
				
				Cell cellDeg = row.createCell(3);
				cellDeg.setCellValue(emp.getUdeg());
				  String qrCodeText = emp.getUemail(); 
				  
				Cell cellQr = row.createCell(4);
				cellQr.setCellValue(emp.getUsalary());
				createQRImage(qrFile, qrCodeText, size, fileType);
				
				InputStream image = new FileInputStream(filePath);
				byte[] bytes = IOUtils.toByteArray(image);
				int image_id=wb.addPicture(bytes, wb.PICTURE_TYPE_PNG);
				image.close();
				XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
	            XSSFClientAnchor my_anchor = new XSSFClientAnchor();
	            
	            my_anchor.setCol1(5); //Column B
	            my_anchor.setRow1(r+1); //Row 3
	            my_anchor.setCol2(6); //Column C
	            my_anchor.setRow2(r); //Row 4
	            XSSFPicture my_picture = drawing.createPicture(my_anchor, image_id);
	            //========adding image END
				r++;
			}
			for(int i=0; i< 5;i++) {
				sheet.autoSizeColumn(i);
			}
			Row rowTotal =sheet.createRow(r);
			Cell cellTotalText =rowTotal.createCell(3);
			cellTotalText.setCellValue("Total: ");
			String formular=String.valueOf(r);
			Cell cellTotal =rowTotal.createCell(4);
			cellTotal.setCellFormula("SUM(E2:E"+formular+")");
			System.out.println("Excel written successful");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	public List<Employee> importExcelData(String excelFileName,Employee employee) {	
		String directory="/upload/Excel";
		String targetDirectory=ServletActionContext.getServletContext().getRealPath(directory);
		File files = new File(targetDirectory,excelFileName);
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			FileInputStream fis = new FileInputStream(files) ;
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet =wb.getSheetAt(0);
			XSSFRow row = null;
			XSSFCell cell = null;
			
			for(int i=1;i<=sheet.getLastRowNum(); i++) {
				row=sheet.getRow(i);
				employee= new Employee();
				for(int j=0; j<row.getLastCellNum(); j++) {
					cell=row.getCell(j);
					if(j==0) {
						employee.setUname(cell.getStringCellValue());
					}
					else if(j==1) {
						employee.setUemail(cell.getStringCellValue());
					}
					else if(j==2) {
						employee.setUpass(cell.getStringCellValue());
					}else if(j==3) {
						employee.setUdeg(cell.getStringCellValue());
					}else if(j==4) {
						employee.setUsalary(cell.getNumericCellValue());
					}
				}
				employeeList.add(employee);
			}
			Employee emp=null;
			for(int x=0 ; x <employeeList.size(); x++) {
				emp = new Employee();
				emp.setUname(employeeList.get(x).getUname());
				emp.setUemail(employeeList.get(x).getUemail());
				emp.setUpass(employeeList.get(x).getUpass());
				emp.setUdeg(employeeList.get(x).getUdeg());
				emp.setUsalary(employeeList.get(x).getUsalary());
				EmployeeDAO dao = new EmployeeDAO();
				dao.save(emp);
			}
			System.out.println("import excel complete");	
		}catch (Exception e) {
			// TODO: handle exception
		}
		return employeeList;
	}
	
	private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
			throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable hintMap = new Hashtable();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
	}
}

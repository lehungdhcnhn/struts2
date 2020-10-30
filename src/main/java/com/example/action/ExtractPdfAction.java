package com.example.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;

public class ExtractPdfAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	/**
	 * 
	 */
	 private static final int BLACK = 0xFF000000;
     private static final int WHITE = 0xFFFFFFFF;
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private EmployeeDAO dao = new EmployeeDAO();
	List<Employee> listEmp = new ArrayList<Employee>();
	@Override
	public String execute() throws Exception{
		try {
			Document document = new Document(PageSize.A4,20,20,20,20);
			
			String assets =ServletActionContext.getServletContext().getRealPath("/assets");
			PdfWriter writer =PdfWriter.getInstance(document, new FileOutputStream(assets+"/temp1"+".pdf"));
			document.open();
			
			Image image =Image.getInstance(assets+"/download.png");
			image.scaleToFit(90f, 90f);
			image.setAlignment(Image.MIDDLE);
			image.setAbsolutePosition(70, 770);
			image.scaleAbsolute(100, 40);
			document.add(image);
			
			Paragraph p1 = new Paragraph("RikkeiSoft technologies", FontFactory.getFont(FontFactory.HELVETICA,14, Font.BOLD, new BaseColor(0,0,0)));
			Paragraph p2 = new Paragraph("Employee Details", FontFactory.getFont(FontFactory.HELVETICA,10, Font.UNDERLINE, new BaseColor(0,0,0)));
			Paragraph p3 = new Paragraph("\n\n\n");
			p1.setAlignment(Element.ALIGN_CENTER);
			p2.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			document.add(p2);
			document.add(p3);
			
			PdfPTable t = new PdfPTable(6);
			float width[]= { 3, 4, 5 ,3, 3,3 };
			t.setWidths(width);
			t.setHeaderRows(1);
			t.setTotalWidth(100f);
			
			PdfPCell c1 = new PdfPCell(new Phrase("User name", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c1.setBorderWidth((float) 0.25);
			c1.setBackgroundColor(new BaseColor(232,232,232));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("email", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c1.setBorderWidth((float) 0.25);
			c1.setBackgroundColor(new BaseColor(232,232,232));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Password", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c1.setBorderWidth((float) 0.25);
			c1.setBackgroundColor(new BaseColor(232,232,232));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Description", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c1.setBorderWidth((float) 0.25);
			c1.setBackgroundColor(new BaseColor(232,232,232));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Salary", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c1.setBorderWidth((float) 0.25);
			c1.setBackgroundColor(new BaseColor(232,232,232));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("QR Code", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c1.setBorderWidth((float) 0.25);
			c1.setBackgroundColor(new BaseColor(232,232,232));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c1);
			
			
			Phrase ph;
			double total=0;
			listEmp=dao.getAllData();
			for(int i=0 ; i<listEmp.size(); i++) {
				
				c1 = new PdfPCell();
				c1.setBorderWidth((float) 0.25);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				ph = new Phrase(listEmp.get(i).getUname(),FontFactory.getFont(FontFactory.HELVETICA,8));
				c1.addElement(ph);
				t.addCell(c1);
				
				c1 = new PdfPCell();
				c1.setBorderWidth((float) 0.25);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				ph = new Phrase(listEmp.get(i).getUemail(),FontFactory.getFont(FontFactory.HELVETICA,8));
				c1.addElement(ph);
				t.addCell(c1);
				
				c1 = new PdfPCell();
				c1.setBorderWidth((float) 0.25);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				ph = new Phrase(listEmp.get(i).getUpass(),FontFactory.getFont(FontFactory.HELVETICA,8));
				c1.addElement(ph);
				t.addCell(c1);
				
				c1 = new PdfPCell();
				c1.setBorderWidth((float) 0.25);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				ph = new Phrase(listEmp.get(i).getUdeg(),FontFactory.getFont(FontFactory.HELVETICA,8));
				c1.addElement(ph);
				t.addCell(c1);
				
				c1 = new PdfPCell();
				c1.setBorderWidth((float) 0.25);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				double a=listEmp.get(i).getUsalary();
				String salary = String.valueOf(a);
				ph = new Phrase(salary,FontFactory.getFont(FontFactory.HELVETICA,8));
				c1.addElement(ph);
				t.addCell(c1);
				
				c1 = new PdfPCell();
				c1.setBorderWidth((float) 0.25);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setImage(getQRCodeImage(listEmp.get(i).getUname(), "UTF-8", 40, 40));
				t.addCell(c1);
				
				total=total+a;
			}
		
			PdfPCell c2 = new PdfPCell(new Phrase("Total", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c2.setBorderWidth((float) 0.25);
			c2.setBackgroundColor(new BaseColor(232,232,232));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c2);
			
			c2 = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c2.setBorderWidth((float) 0.25);
			c2.setBackgroundColor(new BaseColor(232,232,232));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c2);
			
			c2 = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c2.setBorderWidth((float) 0.25);
			c2.setBackgroundColor(new BaseColor(232,232,232));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c2);
			
			c2 = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c2.setBorderWidth((float) 0.25);
			c2.setBackgroundColor(new BaseColor(232,232,232));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c2);
			
			String totalSalary=String.valueOf(total);
			c2 = new PdfPCell(new Phrase(totalSalary, FontFactory.getFont(FontFactory.HELVETICA,8)));
			c2.setBorderWidth((float) 0.25);
			c2.setBackgroundColor(new BaseColor(232,232,232));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c2);
			
			c2 = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA,8)));
			c2.setBorderWidth((float) 0.25);
			c2.setBackgroundColor(new BaseColor(232,232,232));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			t.addCell(c2);
			response.setHeader("Content-disposition", "inline; filename=\"Employee Details.pdf\"");
			document.add(t);
			
			document.close();
			addPageNumber("temp1.pdf", "newName.pdf",request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addPageNumber(String oldFileName, String newFileName, HttpServletRequest request, HttpServletResponse response) {
		try {
			String realPath =ServletActionContext.getServletContext().getRealPath("/assets");
			FileInputStream fis = new FileInputStream(realPath+"/"+"temp1.pdf");
			PdfReader reader = new PdfReader(fis);
			int totalPages = reader.getNumberOfPages();
			PdfStamper stamper= new PdfStamper(reader, response.getOutputStream());
			for(int i=1; i<=totalPages; i++) {
				getHeaderTable(i, totalPages).writeSelectedRows(0,-1, 34, 30, stamper.getOverContent(i));	
			}
			stamper.close();
			fis.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PdfPTable getHeaderTable(int x, int y) {
		PdfPTable table = new PdfPTable(2);
		try {
			table.setTotalWidth(490);
			table.setLockedWidth(true);
			table.getDefaultCell().setFixedHeight(20);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			
			PdfPCell cell = new PdfPCell(new Phrase((""), new Font(Font.FontFamily.HELVETICA,4)));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			
			cell = new PdfPCell(new Phrase(String.format("Page %d of %d", x,y), new Font(Font.FontFamily.HELVETICA,4)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			table.addCell(cell);
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		return table;
	}
	
	public static BufferedImage createQRCode(String qrCodeData, String charset, int qrCodeheight, int qrCodewidth) throws 
											WriterException,IOException,WriterException{
		BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
										BarcodeFormat.QR_CODE, qrCodewidth,qrCodeheight);
		int width=matrix.getWidth();
		int height = matrix.getHeight();
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int x=0; x< width; x++) {
			for(int y=0; y<height; y++) {
				image.setRGB(x, y, matrix.get(x, y)? BLACK : WHITE);
			}
		}
		System.out.println("qr code image was generated for "+qrCodeData);
		return image;
	}
	
	public static Image getQRCodeImage(String qrCodeData, String charset, int qrCodeHeight, int qrCodewidth) throws
										com.itextpdf.text.pdf.qrcode.WriterException, WriterException, BadElementException, IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedImage bi = createQRCode(qrCodeData, charset,qrCodeHeight,qrCodewidth);
		ImageIO.write(bi, "png", baos);
		baos.flush();
		byte[] imageInByte =baos.toByteArray();
		baos.close();
		Image image =Image.getInstance(imageInByte);
		System.out.println("image was generated for qr code image");
		return image;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}

}

package com.example.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.dao.EmployeeDAO;
import com.example.helper.ExcelCreator;
import com.example.model.Employee;
import com.opensymphony.xwork2.ActionSupport;

public class exportExcel extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Employee> listEmp =null;
	private EmployeeDAO dao = null;
	private ExcelCreator ex;
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String exportInExcel() {
		listEmp= new ArrayList<Employee>();
		dao = new EmployeeDAO();
		listEmp =dao.getAllData();
		ex= new ExcelCreator();
		
		try {
			XSSFWorkbook wb =ex.exportInExcel(listEmp);
			ByteArrayOutputStream boas = new ByteArrayOutputStream();
			wb.write(boas);
			setInputStream(new ByteArrayInputStream(boas.toByteArray()));
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		return SUCCESS;
	}
}

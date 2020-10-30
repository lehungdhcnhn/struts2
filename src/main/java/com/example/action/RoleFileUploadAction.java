package com.example.action;	

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.example.dao.EmployeeDAO;
import com.example.helper.ExcelCreator;
import com.example.model.Employee;
import com.opensymphony.xwork2.ActionSupport;

public class RoleFileUploadAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	ExcelCreator ex = new  ExcelCreator();
	EmployeeDAO emp =null;
	/*
	 * public FileInputStream getFile() { return file; }
	 * 
	 * public void setFile(FileInputStream file) { this.file = file; }
	 */

	

	public void setEmp(EmployeeDAO emp) {
		this.emp = emp;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	@Override
	public String execute() throws Exception{
		String tempDir="/upload/Excel";
		String temDirRealPath=ServletActionContext.getServletContext().getRealPath(tempDir);
		Employee employee= (Employee) ServletActionContext.getRequest().getSession().getAttribute("employee");
		File target = new File(temDirRealPath,fileUploadFileName);
		if(target.exists()) {
			target.delete();
		}

		try{
			FileUtils.copyFile(fileUpload, target);
			ex.importExcelData(fileUploadFileName, employee);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}

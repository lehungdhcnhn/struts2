package com.example.action;

import com.example.dao.EmployeeDAO;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uemail, msg;
	EmployeeDAO dao = null;
	
	@Override
	public String execute() throws Exception{
		dao = new EmployeeDAO();
		msg = dao.delete(uemail);
		return "DELETE";
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public EmployeeDAO getDao() {
		return dao;
	}

	public void setDao(EmployeeDAO dao) {
		this.dao = dao;
	}
	

}

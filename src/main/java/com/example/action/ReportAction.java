package com.example.action;

import java.util.ArrayList;
import java.util.List;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.opensymphony.xwork2.ActionSupport;

public class ReportAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Employee> listEmp = new ArrayList<Employee>();
	List<Employee> list =null;
	double total;
	EmployeeDAO dao = new EmployeeDAO();
	@Override
	public String execute() throws Exception{
		listEmp=dao .getAllData();
		list=listEmp;
		total=dao.toTal(listEmp);
		return "REPORT";
	}
	public List<Employee> getListEmp() {
		return listEmp;
	}
	public void setListEmp(List<Employee> listEmp) {
		this.listEmp = listEmp;
	}
	public EmployeeDAO getDao() {
		return dao;
	}
	public void setDao(EmployeeDAO dao) {
		this.dao = dao;
	}
	
	
}

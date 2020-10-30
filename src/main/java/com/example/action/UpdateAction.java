package com.example.action;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uname, udeg, uemail, upass, msg;
	private double usalary;
	private String hiddenuemail;
	EmployeeDAO dao = null;
	Employee employee = null;
	@Override
	public String execute() throws Exception{
		dao = new EmployeeDAO();
		employee = new Employee(uname,uemail,udeg,upass,usalary);
		msg=dao.update(employee);
		return "UPDATE";
	}
	
	

	public double getUsalary() {
		return usalary;
	}



	public void setUsalary(double usalary) {
		this.usalary = usalary;
	}



	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUdeg() {
		return udeg;
	}
	public void setUdeg(String udeg) {
		this.udeg = udeg;
	}
	public String getUemail() {
		return uemail;
	}
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	public String getUpass() {
		return upass;
	}
	public void setUpass(String upass) {
		this.upass = upass;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getHiddenuemail() {
		return hiddenuemail;
	}
	public void setHiddenuemail(String hiddenuemail) {
		this.hiddenuemail = hiddenuemail;
	}
	public EmployeeDAO getDao() {
		return dao;
	}
	public void setDao(EmployeeDAO dao) {
		this.dao = dao;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	

}

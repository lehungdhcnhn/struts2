package com.example.action;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uname, udeg, uemail, upass, msg,usalary;
	
	EmployeeDAO dao = null;
	Employee employee = null;
	
	@Override
	public String execute() throws Exception{
		double sala= Double.valueOf(usalary);
		employee = new Employee(uname,uemail,udeg,upass,sala);
		dao = new EmployeeDAO();
		msg= dao.save(employee);
		return "REGISTER";
	}
	
	public String getUsalary() {
		return usalary;
	}

	public void setUsalary(String usalary) {
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
	
}

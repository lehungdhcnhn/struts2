package com.example.model;

public class Employee {
	private String uname;
	private String uemail;
	private String udeg;
	private String upass;
	private double usalary;
	
	
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
	public String getUemail() {
		return uemail;
	}
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	public String getUdeg() {
		return udeg;
	}
	public void setUdeg(String udeg) {
		this.udeg = udeg;
	}
	public String getUpass() {
		return upass;
	}
	public void setUpass(String upass) {
		this.upass = upass;
	}
	public Employee() {
		
	}
	public Employee(String uname, String uemail, String udeg, String upass,double usalary) {
		super();
		this.uname = uname;
		this.uemail = uemail;
		this.udeg = udeg;
		this.upass = upass;
		this.usalary=usalary;
	}
	
}

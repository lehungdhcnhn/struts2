package com.example.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.example.model.Employee;
import com.example.util.MyBatisUtil;

public class EmployeeDAO {
	public String save(Employee employee) {
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		session.insert("com.example.mapper.EmployeeMapper.insertEmployee",employee);
		session.commit();
		session.close();
		return "Register Successful";
	}
	
	public String update(Employee employee) {
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		session.update("com.example.mapper.EmployeeMapper.updateEmployee",employee);
		session.commit();
		session.close();
		return "Update Successful";
	}
	public String delete(String email) {
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		session.delete("com.example.mapper.EmployeeMapper.deleteEmployee", email);
		session.commit();
		session.close();
		return "Delete Successful";
	}
	
	public List<Employee> getAllData() {
		 SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();	
		 List<Employee> list =session.selectList("com.example.mapper.EmployeeMapper.getAllEmployee");
		  session.close();
		  return list;
	}
	public double toTal(List<Employee> listEmp) {
		double toTal=0;
		for(int i=0; i<listEmp.size(); i++) {
			toTal=toTal+listEmp.get(i).getUsalary();
		}
		return toTal;
	}
}

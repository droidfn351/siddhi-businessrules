package com.droidfn.salver;

public class Employee {

	private String name;
	
	private String company;
	
	private double salary;
	private double dif;
	private double grantamnt;
	
	public Employee() {
		
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	public Double getSalary(){
		return salary;
		
	}
	public void setSalary(Double salary){
		this.salary=salary;
	}
	public Double getDif(){
		return dif;
		
	}
	public void setDif(){
		this.dif=this.salary-500000.00;
	}
	public Double getGrant(){
		return grantamnt;}
	public void setGrant(Double grnt){
		this.grantamnt=grnt;
	}
}

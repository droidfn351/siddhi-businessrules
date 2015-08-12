package com.droidfn.salver;

import java.util.ArrayList;
import java.util.List;


public class Result {
	private Employee emp;
	private List<String> messages = new ArrayList<String>();
public Result() {	
	}
 public Result(Employee emp){
	 this.emp=emp;
 }
 public Employee getEmp(){
	 return emp;
 }
 public void setEmp(Employee emp){
	 this.emp=emp;
 }
 public List<String> getMessages() {
		return messages;
	}
 public void addMessage(String message) {
		this.messages.add(message);
	}
}

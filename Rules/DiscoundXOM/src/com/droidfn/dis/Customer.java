package com.droidfn.dis;

public class Customer {

	private String cid;
	private int c1;
	private int c2;
	private int c3;
	private int disc;
	
	public Customer(){
	}
	public String getCID(){
		return cid;
		
	}
	public int getc1(){
		return c1;
	}
	public int getc2(){
		return c2;
	}
	public int getc3(){
		return c3;}
	public void setCID(String cid){
		this.cid=cid;
	}
	public void setC1(int c1){
		this.c1=c1;
	}
	public void setC2(int c2){
		this.c2=c2;
	}
	public void setC3(int c3){
		this.c3=c3;
	}
	public void setdisc(int disc){
		this.disc=disc;
	}
	public int getdisc(){
		return disc;
	}
}

package com.droidfn.st;

public class Customer {
	
		String cid;
		String state;
		int stax;
		int ship;
		
		public Customer(){	
		}
		public String getCID(){
			return cid;
		}

		public String getState(){
			return state;

		}
		public int getTax(){
			return stax;
		}
		public int getship(){
			return ship;
		}
		public void setCID(String cid){
			this.cid=cid;
		}
		public void setState(String state){
			this.state=state;
		}
		public void setTax(int stax){
			this.stax=stax;
		}
		public void setShip(int ship){
			this.ship=ship;
		}
}

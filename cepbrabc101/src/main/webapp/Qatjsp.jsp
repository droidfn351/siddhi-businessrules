<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.ibm.nosql.json.api.BasicDBList"
	import="com.ibm.nosql.json.api.BasicDBObject"
	import="com.ibm.nosql.json.util.Base64"
	import="com.ibm.nosql.json.util.JSON"
	import="org.apache.http.client.methods.CloseableHttpResponse"
	import="org.apache.http.client.methods.HttpPost"
	import="org.apache.http.entity.StringEntity"
	import="org.apache.http.impl.client.CloseableHttpClient"
	import="org.apache.http.impl.client.HttpClients"
	import="org.apache.http.util.EntityUtils"
	import="org.wso2.siddhi.core.SiddhiManager"
	import="org.wso2.siddhi.core.event.Event"
	import="org.wso2.siddhi.core.stream.input.InputHandler"
	import="org.wso2.siddhi.core.stream.output.StreamCallback"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>
</head>
<body bgcolor="#e0ffff"><center>
<h1>Shopping Cart List</h1>
<%
String cid=request.getParameter("cid");
String cat1=request.getParameter("cat1");
String cat2=request.getParameter("cat2");
String cat3=request.getParameter("cat3");
String state=request.getParameter("cstate");
%>
<h3>
Category 1-$<%=cat1%><br>
Category 2-$<%=cat2%><br>
Category 3-$<%=cat3%><br>
State =<%=state%><br>

<%-- Insert code to send data to the IBM Business Rules here --%>
<% String VCAP_SERVICES=System.getenv("VCAP_SERVICES");
   BasicDBObject obj= (BasicDBObject)JSON.parse(VCAP_SERVICES);
   BasicDBList db= (BasicDBList)obj.get("businessrules");
	
	obj=(BasicDBObject)db.get("0");
	
	obj=(BasicDBObject)obj.get("credentials");
	
	final String resturl=(String)obj.get("executionRestUrl");
	final String username=(String)obj.get("user");
	final String password=(String)obj.get("password");
	String bas=username+":"+password;
	final String authorization="Basic "+Base64.encode(bas.getBytes());
	
	//Acquire the data provided to us

	int c1=Integer.parseInt(cat1);
	int c2=Integer.parseInt(cat2);
	int c3=Integer.parseInt(cat3);
	
	if(c1==-99)
		c1=0;
	if(c2==-99)
		c2=0;
	if(c3==-99)
		c3=0;
	//Pass data to calculate the discount
	String resttmp=resturl+"/DiscountAPP/1.0/DiscountRules/1.0";
	
	String payload="{\"thecustomer\":{\"cid\":\""+cid+"\",\"c1\":"+c1+",\"c2\":"+c2+",\"c3\":"+c3+",\"disc\":0},\"__DecisionID__\":\"q1\"}";
	CloseableHttpClient client = HttpClients.createDefault();
	HttpPost htpo=new HttpPost(resttmp);
	htpo.addHeader("Authorization",authorization);
	htpo.addHeader("Content-Type","application/json");

	htpo.setEntity(new StringEntity(payload));
	CloseableHttpResponse responsec = client.execute(htpo);
	
	String result= EntityUtils.toString(responsec.getEntity());
	 BasicDBObject obj12=(BasicDBObject)JSON.parse(result);
	 obj12=(BasicDBObject)obj12.get("thecustomer");
	 int discount=Integer.parseInt(obj12.get("disc").toString());

	 //Pass the data to calculate the state tax and shipping rules
	 resttmp=resturl+"/StateAPP/1.0/StateRules/1.0";
	 payload="{\"cust\":{\"cid\":\""+cid+"\",\"state\":\""+state+"\",\"ship\":0,\"tax\":0},\"__DecisionID__\":\"q12\"}";
	
	 client = HttpClients.createDefault();
	 htpo=new HttpPost(resttmp);
	 htpo.addHeader("Authorization",authorization);
	 htpo.addHeader("Content-Type","application/json");

	 htpo.setEntity(new StringEntity(payload));
     responsec = client.execute(htpo);
		
	 result= EntityUtils.toString(responsec.getEntity());
	 obj12=(BasicDBObject)JSON.parse(result);
	 obj12=(BasicDBObject)obj12.get("cust");
	 int stax=Integer.parseInt(obj12.get("tax").toString());
	 int shipcost=Integer.parseInt(obj12.get("ship").toString());	 
	 
	 session.setAttribute("cid",cid);
	 
%>
Discount =<%=discount%>%<br>
State Tax =<%=stax%>%<br>
Shipping Cost=$<%=shipcost%><br>
<% 
double total_cost=0;
 total_cost=c1+c2+c3;
 //total cost
 double dic=total_cost*(discount/100.00);
 total_cost-=dic;
 //Cost after discount
 total_cost*=(1.12+(stax/100.00));
 //Cost after sales tax
 total_cost+=shipcost;
 //Total cost
 session.setAttribute("value",total_cost);
%>
Total Cost=$<%=total_cost%>
</h3>
<form action="Qat" method="POST">
<input type="submit" value="Order">
</form>
</center>
</body>
</html>
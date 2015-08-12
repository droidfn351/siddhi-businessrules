package com.blg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.config.SiddhiConfiguration;
import org.wso2.siddhi.core.config.SiddhiContext;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

/**
 * Servlet implementation class Qat
 */
public class Qat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Qat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		response.getWriter().println("Connected");
		
		HttpSession session = request.getSession(true);
		String cid=session.getAttribute("cid").toString();
		float val=Float.parseFloat(session.getAttribute("value").toString());
	
		
		SiddhiManager siddhimanager=new SiddhiManager();
		SiddhiConfiguration sc=new SiddhiConfiguration();
		sc.setAsyncProcessing(true);
		siddhimanager.defineStream("define stream StatusTime(cid string,value int)");
		siddhimanager.addQuery("from StatusTime[cid=='sr1']#window.time(1 min) insert into CSR");
   		siddhimanager.addCallback("CSR",new StreamCallback()
   		{public void receive(Event[] arg0)
   			{
   			  try {
				response.getWriter().println("Notify Campaign Management Team");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   			}
   		});
   		
   		siddhimanager.addQuery("from StatusTime#window.time(3 min) select cid as CID,sum(value) as SUM group by cid having (SUM>40000) insert into Printme for all-events");
   		siddhimanager.addCallback("Printme",new StreamCallback(){

			@Override
			public void receive(Event[] arg0) {
				// TODO Auto-generated method stub
				try {
					response.getWriter().println("Preint");
					for(Event e : arg0){
						response.getWriter().println(e);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
   			
   		});
   		siddhimanager.addQuery("from every a1=StatusTime -> b1=StatusTime[cid==a1.cid] -> c1=StatusTime[cid==a1.cid] within 5 min insert into Pym for all-events");
   		siddhimanager.addCallback("Pym", new StreamCallback(){

			@Override
			public void receive(Event[] arg0) {
				// TODO Auto-generated method stub
				try{
					for(Event e: arg0){
						response.getWriter().println(e);
					}
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
   			
   		});
   		
   		InputHandler inputHandler = siddhimanager.getInputHandler("StatusTime");
   		try {
			inputHandler.send(new Object[]{cid,val});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
   		
		
	}

}

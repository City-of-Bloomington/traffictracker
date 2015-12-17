/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package traffic;
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;  
import org.apache.struts2.dispatcher.SessionMap;  
import org.apache.struts2.interceptor.SessionAware;  
import org.apache.struts2.util.ServletContextAware;  

import org.apache.log4j.Logger;

public abstract class TopAction extends ActionSupport implements SessionAware, ServletContextAware{

		static final long serialVersionUID = 80L;	
   
		String action="", url="", id="";
		static Logger logger = Logger.getLogger(TopAction.class);
		// Report report = null;
		User user = null;
		private ServletContext ctx;
		private Map<String, Object> sessionMap;

		public void setAction(String val){
				action = val;
		}
		public String getAction(){
				return action;
		}
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public String getId(){
				return id;
		}
		public User getUser(){
				return user;
		}		
		String doPrepare(){
				String back = "";
				try{
						user = (User)sessionMap.get("user");
						if(user == null){
								back = LOGIN;
						}
						if(url.equals("")){
								String val = ctx.getInitParameter("url");
								if(val != null)
										url = val;
						}
				}catch(Exception ex){
						System.out.println(ex);
				}		
				return back;
		}		
		@Override  
		public void setSession(Map<String, Object> map) {  
				sessionMap=map;  
		}
		@Override  	
		public void setServletContext(ServletContext ctx) {  
        this.ctx = ctx;  
    }  	
}






































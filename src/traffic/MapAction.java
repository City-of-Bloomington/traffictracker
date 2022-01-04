package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*; 
import javax.servlet.ServletContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;
import org.apache.struts2.interceptor.SessionAware;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapAction extends ActionSupport implements SessionAware, ServletContextAware{
    private static final long serialVersionUID = 290L;
		static Logger logger = LogManager.getLogger(MapAction.class);
		private ServletContext ctx;
		private Map<String, Object> sessionMap;
		private MapItem mapItem = null;
		private User user;
		String action = "", id="", url="";
		//
		// if we have global list we can set them here and will
		// be available for all pages
		//
    @Override
		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(!back.equals("")){
						try{
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = url+"Login";
								res.sendRedirect(str);
								return super.execute();
						}catch(Exception ex){
								System.err.println(ex);
						}	
				}				
				if(action.startsWith("Save")){
						ret = SUCCESS;
						back = mapItem.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Saved Successfully");
								/*
								sessionMap.put("message","Saved Successfully");
								try{
										HttpServletResponse res = ServletActionContext.getResponse();
										String str = url+"project.action?action=Edit&id="+mapItem.getId();
										res.sendRedirect(str);
										return super.execute();
								}catch(Exception ex){
										System.err.println(ex);
								}
								*/
						}
				}
				else if(!id.equals("")){ 
						mapItem = new MapItem(id);
						back = mapItem.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
				}		
				return ret;
		}
		String doPrepare(){
				String back = "";
				try{
						user = (User)sessionMap.get("user");
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
		public void setMapItem(MapItem val){
				if(val != null)
						mapItem = val;
		}
		public MapItem getMapItem(){ 
				if(mapItem == null){
						if(!id.equals("")){
								mapItem = new MapItem(id);
						}
						else{
								mapItem = new MapItem();
						}
				}		
				return mapItem;
		}
		
		@Override  
		public void setSession(Map<String, Object> map) {  
				sessionMap=map;  
		}
		@Override  	
		public void setServletContext(ServletContext ctx) {  
        this.ctx = ctx;  
    }  	 
		/**
			 public key
			 old url and token
			             "url": "http:\/\/api.tiles.mapbox.com\/v4\/mapbox.streets\/{z}\/{x}\/{y}.png?access_token=pk.eyJ1IjoiaW5naGFtbiIsImEiOiJkYjI4YjY4NTQzZmU3YzU1NjNhNWY5YWQ1MGYzNWM1MSJ9.bTz47lsKieDIZoLusUS99A"

				new token
				
       pk.eyJ1Ijoic2lib3ciLCJhIjoiY2t5MGcyYnY0MDB0OTJxcGRrNnJocGk4ZSJ9.0ku_IM1zq8fUJThadGNs9A

		 */

}



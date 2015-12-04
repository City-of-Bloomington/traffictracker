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
import org.apache.log4j.Logger;

public class MapAction extends ActionSupport implements SessionAware, ServletContextAware{
    private static final long serialVersionUID = 290L;
		static Logger logger = Logger.getLogger(MapAction.class);
		private ServletContext ctx;
		private Map<String, Object> sessionMap;
		private MapItem mapItem = null;
		private User user;
		String action = "", id="";
		//
		// if we have global list we can set them here and will
		// be available for all pages
		//
    @Override
		public String execute(){
				String ret = SUCCESS;
				String back = "";
				doPrepare();
				if(user == null){
						ret = LOGIN;
				}
				if(action.startsWith("Save")){ 
						back = mapItem.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Saved Successfully");
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
		void doPrepare(){
				String back = "";
				try{
						user = (User)sessionMap.get("user");
				}catch(Exception ex){
						System.out.println(ex);
				}		
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
}



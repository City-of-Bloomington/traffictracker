package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
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

public class SearchAction extends ActionSupport implements SessionAware, ServletContextAware{

		static final long serialVersionUID = 33L;	
		static private String url="";		
		String id = "", action="";
		static Logger logger = Logger.getLogger(SearchAction.class);
		//
		ProjectList projectList = null;
		List<Project> projects = null;
		List<Type> types = null,
				features = null,
				owners = null,
				sources=null,
				ranks=null;
		List<User> leads = null;
		private Map<String, Object> sessionMap;
		private ServletContext ctx;
		private User user;
		String projectsTitle = "Most Recent Projects";		
		public String execute(){
				String ret = SUCCESS;
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
				if(action.equals("Submit")){ 
						back = projectList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								projects = projectList.getProjects();
								if(projects != null && projects.size() > 0){
										projectsTitle = "Matching Projects "+projects.size();
								}
								else{
										addActionMessage("No match found");
								}
						}
				}
				else{ // show most recent
						if(projectList == null)
								getProjectList();
						back = projectList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								projects = projectList.getProjects();
						}
				}
				return ret;
		}
		/**
		 * this method is used to get user param
		 */
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

		public ProjectList getProjectList(){ // starting a new redeem
				if(projectList == null){
						projectList = new ProjectList();
				}		
				return projectList;
		}
		public void setProjectList(ProjectList val){
				if(val != null)
						projectList = val;
		}
		public String getProjectsTitle(){
				return projectsTitle;
		}		
		public void setAction(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public String getAction(){
				return action;
		}
		// most recent
		public List<Project> getProjects(){ 
				return projects;
		}
		public List<Type> getTypes(){ 
				if(types == null){
						TypeList dl = new TypeList();
						String back = dl.find();
						if(back.equals(""))
								types = dl.getTypes();
				}		
				return types;
		}
		public List<Type> getFeatures(){ 
				if(features == null){
						TypeList dl = new TypeList("features");
						String back = dl.find();
						if(back.equals(""))
								features = dl.getTypes();
				}		
				return features;
		}
		public List<Type> getOwners(){ 
				if(owners == null){
						TypeList dl = new TypeList("owners");
						String back = dl.find();
						if(back.equals(""))
								owners = dl.getTypes();
				}		
				return owners;
		}
		public List<Type> getSources(){ 
				if(sources == null){
						TypeList dl = new TypeList("funding_sources");
						String back = dl.find();
						if(back.equals(""))
								sources = dl.getTypes();
				}		
				return sources;
		}
		public List<Type> getRanks(){ 
				if(ranks == null){
						TypeList dl = new TypeList("phase_ranks");
						String back = dl.find();
						if(back.equals(""))
								ranks = dl.getTypes();
				}		
				return ranks;
		}
		public List<User> getLeads(){ 
				if(leads == null){
						UserList dl = new UserList();
						String back = dl.find();
						if(back.equals(""))
								leads = dl.getUsers();
				}		
				return leads;
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






































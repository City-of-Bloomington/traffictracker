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

public class ProjectUpdateAction extends ActionSupport implements SessionAware, ServletContextAware{

		static final long serialVersionUID = 29L;	
		static private String url="";		
		String id ="", project_id="", action="";
		static Logger logger = Logger.getLogger(ProjectUpdateAction.class);
		//
		ProjectUpdate projectUpdate = null;
		
		List<Project> projects = null;
		List<Type> ranks = null;
		List<User> users = null;		
		List<ProjectUpdate> updates = null;
		private Map<String, Object> sessionMap;
		private ServletContext ctx;
		private User user;
		
		String updatesTitle = "Most Recent Updates";
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

				if(action.equals("Save")){ 
						projectUpdate.setUser(user);
						back = projectUpdate.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Saved Successfully");
								ret = "view";
						}
				}				
				else if(action.equals("Save Changes")){
						/*
						projectUpdate.setUser(user);
						back = projectUpdate.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
						*/
				}
				else if(action.equals("Delete")){ 
						back = projectUpdate.doDelete();
						if(!back.equals("")){
								// back to the same page 
								addActionError(back);
						}
						else{
								ret = "search";
						}
				}		
				else if(!id.equals("")){
						projectUpdate = new ProjectUpdate(id);
						back = projectUpdate.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								project_id = projectUpdate.getProject_id();
								ret = "view";
						}
				}
				else if(!project_id.equals("")){
						if(projectUpdate == null)
								projectUpdate = new ProjectUpdate();

						projectUpdate.setProject_id(project_id);
				}
				else {
						ret = "noproject";
						addActionMessage("You need to pick a project so that you can add updates");
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
		//
		public ProjectUpdate getProjectUpdate(){ // starting a new redeem
				if(projectUpdate == null){
						if(!id.equals("")){
								projectUpdate = new ProjectUpdate(id);
						}
						else{
								projectUpdate = new ProjectUpdate();
								if(!project_id.equals("")){
										projectUpdate.setProject_id(project_id);
										System.err.println(" setting "+project_id);
								}
						}
				}
				
				return projectUpdate;
		}
		public void setProjectUpdate(ProjectUpdate val){
				if(val != null)
						projectUpdate = val;
		}
		public String getUpdatesTitle(){
				return updatesTitle;
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
		public void setId(String val){
				if(val != null && !val.equals(""))		
						id = val;
		}
		public String getId(){
				if(id.equals("") && projectUpdate != null){
						id = projectUpdate.getId();
				}
				return id;
		}
		public void setProject_id(String val){
				if(val != null && !val.equals("")){		
						project_id = val;
						if(projectUpdate == null){
								projectUpdate = new ProjectUpdate();
						}
						projectUpdate.setProject_id(project_id);
				}
		}
		public String getProject_id(){
				if(project_id.equals("") && projectUpdate != null){
						project_id = projectUpdate.getProject_id();
				}
				return project_id;
		}		
		// most recent
		public List<Project> getProjects(){ 
				if(projects == null){
						ProjectList dl = new ProjectList();
						dl.setCanBeUpdated();
						String back = dl.find();
						projects = dl.getProjects();
				}		
				return projects;
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
		public List<User> getUsers(){ 
				if(users == null){
						UserList dl = new UserList();
						String back = dl.find();
						if(back.equals(""))
								users = dl.getUsers();
				}		
				return users;
		}		
		public List<ProjectUpdate> getUpdates(){
				if(!project_id.equals("") && updates == null){
						ProjectUpdateList pul = new ProjectUpdateList(project_id);
						String back = pul.find();
						if(back.equals("")){
								updates = pul.getUpdates();
						}
				}
				return updates;
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






































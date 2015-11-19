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


public class ProjectAction extends ActionSupport implements SessionAware, ServletContextAware{

		static final long serialVersionUID = 28L;	
		static private String url="";		
		String id = "", action="";
		static Logger logger = Logger.getLogger(ProjectAction.class);
		//
		Project project = null;
		Feature feature = null;
		List<Project> projects = null;
		List<Type> types = null,
				features = null,
				owners = null,
				sources=null,
				ranks=null;
		List<SubType> sub_features = null, sub_sub_features = null;
		List<ProjectUpdate> updates = null;
		List<User> leads = null;
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
						project.setUser(user);
						back = project.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = project.getId();
								if(feature.hasData()){
										feature.setProject_id(id);
										back = feature.doSave();
										if(!back.equals("")){
												addActionError(back);
										}
										else{
												feature = new Feature();
										}
								}
								if(back.equals(""))
										addActionMessage("Saved Successfully");
						}
				}				
				else if(action.equals("Save Changes")){ 
						project.setUser(user);
						back = project.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								if(feature.hasData()){
										feature.setProject_id(id);
										back = feature.doSave();
										if(!back.equals("")){
												addActionError(back);
										}
										else{
												feature = new Feature();
										}
								}
								if(back.equals(""))
										addActionMessage("Updated Successfully");
						}
				}
				else if(action.equals("Delete")){ 
						back = project.doDelete();
						if(!back.equals("")){
								// back to the same page 
								addActionError(back);
						}
						else{
								ret = "search";
						}
				}
				else if(action.equals("Edit")){ 
						project = new Project(id);
						back = project.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
				}
				else if(action.equals("Refresh")){
						// nothing
				}				
				else if(!id.equals("")){ 
						project = new Project(id);
						back = project.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								ret = "view";
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

		public Project getProject(){ 
				if(project == null){
						if(!id.equals("")){
								project = new Project(id);
						}
						else{
								project = new Project();
						}
				}		
				return project;
		}
		public Feature getFeature(){ 
				if(feature == null){
						feature = new Feature();
				}		
				return feature;
		}		
		public void setProject(Project val){
				if(val != null)
						project = val;
		}
		public void setFeature(Feature val){
				if(val != null)
						feature = val;
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
		public void setAction2(String val){
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
				if(id.equals("") && project != null){
						id = project.getId();
				}
				return id;
		}
		// most recent
		public List<Project> getProjects(){ 
				if(projects == null){
						ProjectList dl = new ProjectList();
						String back = dl.find();
						projects = dl.getProjects();
				}		
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
		public List<SubType> getSub_features(){
				if(feature == null) getFeature();
				if(sub_features == null && feature.hasSubFeature()){
						SubTypeList dl = new SubTypeList("sub_features");
						dl.setFeature_id(feature.getFeature_id());
						String back = dl.find();
						if(back.equals(""))
								sub_features = dl.getSubTypes();
				}		
				return sub_features;
		}
		public List<SubType> getSub_sub_features(){ 
				if(sub_sub_features == null && feature.hasSubSubFeature()){
						SubTypeList dl = new SubTypeList("sub_sub_features");
						dl.setSub_id(feature.getSub_id());
						String back = dl.find();
						if(back.equals(""))
								sub_sub_features = dl.getSubTypes();
				}		
				return sub_sub_features;
		}
		public List<ProjectUpdate> getUpdates(){
				if(!id.equals("") && updates == null){
						ProjectUpdateList pul = new ProjectUpdateList(id);
						String back = pul.find();
						if(back.equals("")){
								updates = pul.getUpdates();
						}
				}
				return updates;
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






































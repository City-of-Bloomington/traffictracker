package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProjectUpdateAction extends TopAction{

		static final long serialVersionUID = 29L;	
		String project_id="";
		static Logger logger = LogManager.getLogger(ProjectUpdateAction.class);
		//
		ProjectUpdate projectUpdate = null;
		Project project = null;
		//
		List<Project> projects = null;
		List<Type> ranks = null;
		List<User> users = null;		
		List<ProjectUpdate> updates = null;
		
		String updatesTitle = "Most Recent Project Phases";
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
						ret = INPUT;
						if(projectUpdate == null)
								projectUpdate = new ProjectUpdate();
						getProject();
						ProjectUpdate prevPhase = project.getLastProjectUpdate();
						if(prevPhase != null){
								projectUpdate.setPrev_date(prevPhase.getDate());
						}
						projectUpdate.setProject_id(project_id);
				}
				else {
						ret = "noproject";
						addActionMessage("You can pick a project to edit or add phase rank");
				}
				return ret;
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
										getProject();
										ProjectUpdate prevPhase = project.getLastProjectUpdate();
										if(prevPhase != null){
												projectUpdate.setPrev_date(prevPhase.getDate());
										}										
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
		public Project getProject(){
				getProject_id();
				if(project == null && !project_id.equals("")){
						Project one = new Project(project_id);
						String back = one.doSelect();
						if(back.equals("")){
								project = one;
						}
				}
				return project;
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
				
				if(updates == null){
						ProjectUpdateList pul = new ProjectUpdateList();
						if(!project_id.equals("")){
								pul.setProject_id(project_id);
								pul.setNoLimit();
						}
						else{
								pul.setLasPerProject();
						}
						String back = pul.find();
						if(back.equals("")){
								updates = pul.getUpdates();
						}
				}
				return updates;
		}

}






































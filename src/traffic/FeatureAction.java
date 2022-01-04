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

public class FeatureAction extends TopAction{

		static final long serialVersionUID = 288L;	
		static Logger logger = LogManager.getLogger(FeatureAction.class);
		//
		String project_id = "";
		Feature feature = null;
		List<Feature> projectFeatures = null;
		List<Type> features = null;
		List<SubType> sub_features = null, sub_sub_features = null;
		String featuresTitle = "Features related to this project";
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
				if(action.equals("Save Changes")){ 
						// feature.setUser(user);
						back = feature.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.equals("Delete")){
						getFeature();
						project_id = feature.getProject_id();
						back = feature.doDelete();
						if(!back.equals("")){
								// back to the same page 
								addActionError(back);
						}
						else{
								addActionMessage("Deleted Successfully");
								ret = "delete";
						}
				}
				else if(!id.equals("")){ 
						getFeature();
				}		
				return ret;
		}
		public Feature getFeature(){ 
				if(feature == null){
						if(!id.equals("")){
								feature = new Feature(id);
								String back = feature.doSelect();
								if(!back.equals(""))
										addActionError(back);
						}
						else{
								feature = new Feature();
						}
				}		
				return feature;
		}
		
		public void setFeature(Feature val){
				if(val != null)
						feature = val;
		}
		public String getFeaturesTitle(){
				return featuresTitle;
		}		
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public void setProject_id(String val){
				if(val != null && !val.equals(""))		
						project_id = val;
		}
		public String getProject_id(){
				getFeature();
				if(project_id.equals("") && feature != null){
						project_id = feature.getProject_id();
				}
				return project_id;
		}		
		public String getId(){
				if(id.equals("") && feature != null){
						id = feature.getId();
				}
				return id;
		}
		
		// related to project
		public List<Feature> getProjectFeatures(){
				getProject_id();
				if(projectFeatures == null && !project_id.equals("")){
						FeatureList dl = new FeatureList(project_id);
						String back = dl.find();
						projectFeatures = dl.getFeatures();
				}		
				return projectFeatures;
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
		

}






































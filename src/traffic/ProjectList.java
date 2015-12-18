package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.sql.*;
import javax.naming.directory.*;
import org.apache.log4j.Logger;

public class ProjectList implements java.io.Serializable{

		static final long serialVersionUID = 37L;	
   
    boolean canBeUpdated = false, canNotBeUpdated = false;
		static Logger logger = Logger.getLogger(ProjectList.class);
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		String id="", which_date="r.date", type_id="", feature_id="",funding_source_id= "", lead_id="", owner_id="", phase_rank_id="";
		String eng_lead_id="", length_from="", length_to="", name="", des_no="";
		String date_from="", date_to="", sortBy="r.id DESC";
		String est_cost_from = "", est_cost_to="", actual_cost_from="", actual_cost_to="", status="";
		String phase_rank_from="", phase_rank_to="";
		String excludeStatus = "";
		List<Project> projects = null;
	
		public ProjectList(){
		}	
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setDes_no(String val){
				if(val != null)
						des_no = val;
		}		
		public void setFunding_source_id(String val){
				if(val != null && !val.equals("-1"))
						funding_source_id = val;
		}		
		public void setType_id(String val){
				if(val != null && !val.equals("-1"))
						type_id = val;
		}
		public void setOwner_id(String val){
				if(val != null && !val.equals("-1"))
						owner_id = val;
		}
		public void setLead_id(String val){
				if(val != null && !val.equals("-1"))
						lead_id = val;
		}
		public void setFeature_id(String val){
				if(val != null && !val.equals("-1"))
						feature_id = val;
		}		
		public void setEng_lead_id(String val){
				if(val != null && !val.equals("-1"))
						eng_lead_id = val;
		}		
		public void setWhich_date(String val){
				if(val != null)
						which_date = val;
		}
		public void setDate_from(String val){
				if(val != null)
						date_from = val;
		}
		public void setDate_to(String val){
				if(val != null)
						date_to = val;
		}
		public void setEst_cost_from(String val){
				if(val != null)
						est_cost_from = val;
		}
		public void setEst_cost_to(String val){
				if(val != null)
						est_cost_to = val;
		}
		public void setActual_cost_from(String val){
				if(val != null)
						actual_cost_from = val;
		}
		public void setActual_cost_to(String val){
				if(val != null)
						actual_cost_to = val;
		}
		public void setLength_from(String val){
				if(val != null)
						length_from = val;
		}
		public void setLength_to(String val){
				if(val != null)
						length_to = val;
		}		
		public void setPhase_rank_id(String val){
				if(val != null && !val.equals("-1"))
						phase_rank_id = val;
		}
		public void setPhase_rank_from(String val){
				if(val != null && !val.equals("-1"))
						phase_rank_from = val;
		}
		public void setPhase_rank_to(String val){
				if(val != null && !val.equals("-1"))
						phase_rank_to = val;
		}		
		public void setSortBy(String val){
				if(val != null)
						sortBy = val;
		}
		public void setExcludeStatus(String val){
				if(val != null)
						excludeStatus = val;
		}		
		public void setCanBeUpdated(){
				canBeUpdated = true;
		}
		public void setStatus(String val){
				if(val != null && !val.equals("-1")){
						status = val;
				}
		}
		//
		public String getId(){
				return id;
		}
		public String getFeature_id(){
				return feature_id;
		}
		public String getName(){
				return name;
		}
		public String getDes_no(){
				return des_no;
		}		
		public String getFunding_source_id(){
				return funding_source_id;
		}
		public String getType_id(){
				return type_id;
		}
		public String getOwner_id(){
				return owner_id;
		}
		public String getLead_id(){
				return lead_id;
		}
		public String getEng_lead_id(){
				return eng_lead_id;
		}		
		public String getWhich_date(){
				return which_date;
		}
		public String getDate_from(){
				return date_from ;
		}
		public String getDate_to(){
				return date_to ;
		}
		public String getPhase_rank_id(){
				return phase_rank_id ;
		}
		public String getPhase_rank_from(){
				return phase_rank_from ;
		}
		public String getPhase_rank_to(){
				return phase_rank_to ;
		}
		public String getSortBy(){
				return sortBy ;
		}
		public String getEst_cost_from(){
				return est_cost_from ;
		}
		public String getEst_cost_to(){
				return est_cost_to ;
		}
		public String getActual_cost_from(){
				return actual_cost_from ;
		}
		public String getActual_cost_to(){
				return actual_cost_to ;
		}
		public String getLength_from(){
				return length_from ;
		}
		public String getLength_to(){
				return length_to ;
		}
		public String getStatus(){
				if(status.equals(""))
						return "-1";
				return status;
		}				
		public List<Project> getProjects(){
				return projects;
		}
		
		//
		String find(){

				String qq = "select r.id,r.name,r.owner_id,r.type_id,r.funding_source_id,r.description,r.lead_id,r.eng_lead_id,date_format(r.date,'%m/%d/%Y'),r.length,r.file_path,r.DES_no,date_format(r.est_end_date,'%m/%d/%Y'),date_format(r.actual_end_date,'%m/%d/%Y'),r.est_cost,r.actual_cost,AsText(r.geometry),r.status ";
				String qf = " from projects r ";
				String qw = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(!id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " r.id = ? ";
				}
				else {
						if(!name.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.name like ? ";
						}
						if(!des_no.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.des_no = ? ";
						}						
						if(!type_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.type_id = ? ";
						}
						if(!feature_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qf += " left join project_features pf on pf.project_id=r.id ";
								qw += " pf.feature_id = ? ";
						}						
						if(!owner_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.owner_id = ? ";
						}
						if(!funding_source_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.funding_resource_id = ? ";
						}
						if(!lead_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.lead_id = ? ";
						}
						if(!eng_lead_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.eng_lead_id = ? ";
						}						
						if(!length_from.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.length >= ?";
						}
						if(!length_to.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.length <= ?";
						}
						if(!est_cost_from.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.est_cost >= ?";
						}
						if(!est_cost_to.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.est_cost <= ?";
						}
						if(!actual_cost_from.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.actual_cost >= ?";
						}
						if(!actual_cost_to.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.actual_cost <= ?";
						}
						
						if(!status.equals("")){
								if(!qw.equals("")) qw += " and ";								
								qw += " r.status = ? ";
						}						
						if(!phase_rank_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.phase_rank_id = ? ";
								qw += " and u.id in (select max(id) from project_updates u2 where u2.project_id=r.id) ";
								qf += " inner join project_updates u on u.project_id=r.id ";
								
						}
						else if(!phase_rank_from.equals("") || !phase_rank_to.equals("")){
								qf += " inner join project_updates u on u.project_id=r.id ";
								if(!qw.equals("")) qw += " and ";
								qw += " u.id in (select max(id) from project_updates u2 where u2.project_id=r.id) ";								
								if(!phase_rank_from.equals("")){
										qw += " and u.phase_rank_id >= ? ";
								}
								if(!phase_rank_to.equals("")){
										qw += " and u.phase_rank_id <= ? ";
								}								
						}						
						if(!which_date.equals("")){
								if(!date_from.equals("")){
										if(!qw.equals("")) qw += " and ";					
										qw += which_date+" >= ? ";					
								}
								if(!date_to.equals("")){
										if(!qw.equals("")) qw += " and ";
										qw += which_date+" <= ? ";					
								}
						}
						if(!excludeStatus.equals("")){
								if(!qw.equals("")) qw += " and ";								
								qw += " not r.status = ? ";
						}
						/*
						if(canBeUpdated){
								if(!qw.equals("")) qw += " and ";
								qw += " r.id not in (select project_id from project_updates pu where pu.phase_rank_id = 1 or pu.phase_rank_id = 13) ";
						}
						else if(canNotBeUpdated){
								if(!qw.equals("")) qw += " and ";
								qw += " r.id in (select project_id from project_updates pu where pu.phase_rank_id = 1 or pu.phase_rank_id = 13) ";
						}
						*/
				}
				qq += qf;
				if(!qw.equals(""))
						qq += " where "+qw;
				if(!sortBy.equals("")){
						qq += " order by "+sortBy;
				}
				logger.debug(qq);
				//
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!id.equals("")){
								pstmt.setString(jj++,id);
						}
						else{
								if(!name.equals("")){
										pstmt.setString(jj++,"%"+name+"%");
								}
								if(!des_no.equals("")){
										pstmt.setString(jj++,des_no);
								}								
								if(!type_id.equals("")){
										pstmt.setString(jj++,type_id);
								}
								if(!feature_id.equals("")){
										pstmt.setString(jj++,feature_id);
								}
								if(!owner_id.equals("")){
										pstmt.setString(jj++,owner_id);
								}
								if(!funding_source_id.equals("")){
										pstmt.setString(jj++,funding_source_id);
								}								
								if(!lead_id.equals("")){
										pstmt.setString(jj++,lead_id);
								}
								if(!eng_lead_id.equals("")){								
										pstmt.setString(jj++,eng_lead_id);										
								}
								if(!length_from.equals("")){
										pstmt.setString(jj++,length_from);
								}
								if(!length_to.equals("")){
										pstmt.setString(jj++,length_to);
								}
								if(!est_cost_from.equals("")){
										pstmt.setString(jj++,est_cost_from);
								}
								if(!est_cost_to.equals("")){
										pstmt.setString(jj++,est_cost_to);
								}
								if(!actual_cost_from.equals("")){
										pstmt.setString(jj++,actual_cost_from);
								}
								if(!actual_cost_to.equals("")){
										pstmt.setString(jj++,actual_cost_to);
								}
								if(!status.equals("")){
										pstmt.setString(jj++,status);
								}								
								if(!phase_rank_id.equals("")){
										pstmt.setString(jj++,phase_rank_id);
								}
								else{
										if(!phase_rank_from.equals("")){
												pstmt.setString(jj++,phase_rank_from);
										}
										if(!phase_rank_to.equals("")){
												pstmt.setString(jj++,phase_rank_to);
										}
								}
								if(!which_date.equals("")){
										if(!date_from.equals("")){
												pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										}
										if(!date_to.equals("")){
												pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										}
								}
								if(!excludeStatus.equals("")){
										pstmt.setString(jj++, excludeStatus);
								}												
						}
						rs = pstmt.executeQuery();
						projects = new ArrayList<Project>();
						while(rs.next()){
								Project one = new Project(
																					rs.getString(1),
																					rs.getString(2),
																					rs.getString(3),
																					rs.getString(4),
																					rs.getString(5),
																					rs.getString(6),
																					rs.getString(7),
																					rs.getString(8),
																					rs.getString(9),
																					rs.getString(10),
																					rs.getString(11),
																					rs.getString(12),
																					rs.getString(13),
																					rs.getString(14),
																					rs.getString(15),
																					rs.getString(16),
																					rs.getString(17),
																					rs.getString(18) 
																					);
								if(!projects.contains(one))
										projects.add(one);
						}
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;			
		}
}






































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


public class ProjectUpdateList implements java.io.Serializable{

		static final long serialVersionUID = 38L;	
   
		static Logger logger = Logger.getLogger(ProjectUpdateList.class);
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		String id="", which_date="r.date", project_id="",
				phase_rank_id="", user_id="";

		String date_from="", date_to="", sortBy=" r.id DESC ";
		String limit = " limit 15 ";
		List<ProjectUpdate> updates = null;
	
		public ProjectUpdateList(){
		}
		public ProjectUpdateList(String val){
				setProject_id(val);
		}		
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setPhase_rank_id(String val){
				if(val != null)
						phase_rank_id = val;
		}		
		public void setProject_id(String val){
				if(val != null)
						project_id = val;
		}
		public void setUser_id(String val){
				if(val != null)
						user_id = val;
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
		public void setSortBy(String val){
				if(val != null)
						sortBy = val;
		}
		public void setNoLimit(){
				limit = "";
		}
		//
		public String getId(){
				return id;
		}
		public String getProject_id(){
				return project_id;
		}
		public String getPhase_rank_id(){
				return phase_rank_id;
		}
		public String getUser_id(){
				return user_id;
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
		public String getSortBy(){
				return sortBy ;
		}

		public List<ProjectUpdate> getUpdates(){
				return updates;
		}
		//
		String find(){

				String qq = "select r.id,r.project_id,r.phase_rank_id,date_format(r.date,'%m/%d/%Y'),r.notes,r.user_id ";
				String qf = " from project_updates r ";
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
						if(!project_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.project_id = ? ";
						}
						if(!phase_rank_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.phase_rank_id = ? ";
						}						
						if(!user_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.user_id = ? ";
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
				}
				qq += qf;
				if(!qw.equals(""))
						qq += " where "+qw;
				if(!sortBy.equals("")){
						qq += " order by "+sortBy;
				}
				if(!limit.equals("")){
						qq += limit;
				}
				logger.debug(qq);
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
								if(!project_id.equals("")){
										pstmt.setString(jj++,project_id);
								}
								if(!phase_rank_id.equals("")){
										pstmt.setString(jj++,phase_rank_id);
								}
								if(!user_id.equals("")){
										pstmt.setString(jj++,user_id);
								}
								if(!which_date.equals("")){
										if(!date_from.equals("")){
												pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										}
										if(!date_to.equals("")){
												pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										}
								}
						}
						rs = pstmt.executeQuery();
						updates = new ArrayList<ProjectUpdate>();
						while(rs.next()){
								ProjectUpdate one = new ProjectUpdate(
																					rs.getString(1),
																					rs.getString(2),
																					rs.getString(3),
																					rs.getString(4),
																					rs.getString(5),
																					rs.getString(6)
																					);
								updates.add(one);
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






































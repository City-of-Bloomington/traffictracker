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


public class ProjectUpdate implements java.io.Serializable{

		static final long serialVersionUID = 18L;	
   
		static Logger logger = Logger.getLogger(ProjectUpdate.class);
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String id="", project_id="", phase_rank_id="", date="", notes="",
				user_id="";

		//
		// objects
		//
		User user = null;
		Project project = null;
		Type phase_rank = null;
		
		public ProjectUpdate(){
		}	
		public ProjectUpdate(String val){
				setId(val);
		}
		public ProjectUpdate(String val, String val2){
				setId(val);
				setProject_id(val2);
		}		
		public ProjectUpdate(
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6
									 ){
				setValues( val,
									 val2,
									 val3,
									 val4,
									 val5,
									 val6
									 );
		
		}
		void setValues(
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6
									 ){
				setId(val);
				setProject_id(val2);
				setPhase_rank_id(val3);
				setDate(val4);
				setNotes(val5);
				setUser_id(val6);		
		}
				 

		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setProject_id(String val){
				if(val != null)
						project_id = val;
		}		
		public void setNotes(String val){
				if(val != null)
						notes = val;
		}	
		public void setDate(String val){
				if(val != null)
						date = val;
		}
		public void setPhase_rank_id(String val){
				if(val != null && !val.equals("-1"))
						phase_rank_id = val;
		}
		public void setUser_id(String val){
				if(val != null && !val.equals("-1"))
						user_id = val;
		}				
		public void setUser(User val){
				if(val != null){
						user = val;
						if(user_id.equals("")){
								user_id = user.getId();
						}
				}
		}		
		//
		public String getId(){
				return id;
		}
		public String getProject_id(){
				return project_id;
		}
		public String getDate(){
				if(date.equals(""))
						date = Helper.getToday();
				return date;
		}	
		public String getPhase_rank_id(){
				return phase_rank_id;
		}
		public String getUser_id(){
				return user_id;
		}
		public String getNotes(){
				return notes;
		}
		
		public boolean hasNotes(){
				return !notes.equals("");
		}
		public String toString(){
				return notes;
		}
		public Type getPhase_rank(){
				if(!phase_rank_id.equals("") && phase_rank == null){
						Type one = new Type(phase_rank_id, null, "phase_ranks");
						String back = one.doSelect();
						if(back.equals("")){
								phase_rank = one;
						}
				}
				return phase_rank;
		}
		
		public User getUser(){
				if(!user_id.equals("") && user == null){
						User one = new User(user_id);
						String back = one.doSelect();
						if(back.equals("")){
								user = one;
						}
				}
				return user;
		}
		public Project getProject(){
				if(!project_id.equals("") && project == null){
						Project one = new Project(project_id);
						String back = one.doSelect();
						if(back.equals("")){
								project = one;
						}
				}
				return project;
		}
		public boolean isCancelled(){
				return phase_rank_id.equals("1");
		}
		public boolean isComplete(){
				return phase_rank_id.equals("13");
		}
		//
		// No updates allowed, just Save
		//
		public String doSave(){

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(project_id.equals("")){
						msg = " project id not set";
						return msg;
				}
				if(user_id.equals("")){
						msg = " user id not set";
						return msg;
				}
				if(phase_rank_id.equals("")){
						msg = " phase_rank not set";
						return msg;
				}
				if(date.equals(""))
						date = Helper.getToday();
				String qq = "insert into project_updates values(0,?,?,?,?,?)";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						msg = fillData(pstmt);
						if(msg.equals("")){
								pstmt.executeUpdate();
								qq = "select LAST_INSERT_ID() ";
								logger.debug(qq);
								pstmt = con.prepareStatement(qq);
								rs = pstmt.executeQuery();
								if(rs.next()){
										id = rs.getString(1);
								}
						}
				}
				catch(Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
		String fillData(PreparedStatement pstmt){
				String msg = "";
				try{
						pstmt.setString(1, project_id);
						pstmt.setString(2, phase_rank_id);
						pstmt.setDate(3, new java.sql.Date(dateFormat.parse(date).getTime()));						
						if(notes.equals(""))
								pstmt.setNull(4, Types.VARCHAR);
						else
								pstmt.setString(4, notes);
						pstmt.setString(5, user_id);
				}
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
				}
				return msg;
		}
		//
		String doDelete(){
				//
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
				String qq = "delete from project_updates where id=?";
				//
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						pstmt.executeUpdate();
				}
				catch(Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
			
		}	

		String doSelect(){
		
				String qq = "select r.id,r.project_id,r.phase_rank_id,date_format(r.date,'%m/%d/%Y'),r.notes,r.user_id from project_updates r where r.id=? ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						rs = pstmt.executeQuery();	
						if(rs.next()){
								setValues(rs.getString(1),
													rs.getString(2),
													rs.getString(3),
													rs.getString(4),
													rs.getString(5),
													rs.getString(6)
													);
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






































package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.Date;
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
		static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String id="", project_id="", phase_rank_id="", date="", notes="",
				user_id="";
		int update_length = -1; // unset
		//
		// the date should not be less than previous phase date
		// unless it is the first phase
		//
		String prev_date=""; 
		String end_date = "";
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
		public void setPrev_date(String val){
				if(val != null)
						prev_date = val;
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
		public int getUpdate_length(){
				if(update_length < 0)
						findPhaseLength();
				if(update_length < 0) return 0;
				return update_length;
		}
		public String getEnd_date(){
				if(end_date.equals("")){
						findPhaseLength();
				}
				if(end_date.equals("") && !date.equals("")){
						end_date = date;
				}
				return end_date;
		}
		/**
		 * check if the new date is after the previous phase date
		 */
		private boolean checkIfDateIsGreaterThanPreviousPhaseDate(){		
						boolean ret = true;
				if(!prev_date.equals("")){
						try{
								Date old_date = df.parse(prev_date);
								if(date.equals("")) date = Helper.getToday();
								Date new_date = df.parse(date);
								if(!new_date.equals(old_date)								
									 && !new_date.after(old_date)) ret = false;
						}catch(Exception ex){
								System.err.println(ex);
						}
				}
				return ret;
		}
		public String findPhaseLength(){
				//
				String qq = "select datediff(p2.date,p.date) as days,date_format(p2.date,'%m/%d/%Y') from project_updates p, project_updates p2 where p.project_id=p2.project_id and p.phase_rank_id < p2.phase_rank_id and p.id=? and p.project_id=? order by days asc limit 1 ";

				String qq2 = "select datediff(now(),p.date) as days from project_updates p where p.id=? and p.project_id=? ";				
				//
				// if this is the 
				String qw = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(id.equals("") || project_id.equals("")){
						msg = " phase id or project id not set";
						return msg;
				}
				getPhase_rank();
				// if the phase is complete or cancelled, end_date will be the same date
				if(phase_rank.getName().equals("Complete") ||
					 phase_rank.getName().equals("Cancelled")){
						end_date = date;
						update_length = 0;
						return msg;
				}
				getProject();
				ProjectUpdate lastUpdate = project.getLastProjectUpdate();
				if(lastUpdate.getId().equals(id)){
						if(project.getStatus().equals("Closed")){
								end_date = date;
								update_length = 0;
								return msg;
						}
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
						pstmt.setString(jj++,id);
						pstmt.setString(jj++,project_id);
						rs = pstmt.executeQuery();
						if(rs.next()){ // we want the first one only 
								update_length = rs.getInt(1);
								end_date = rs.getString(2);								
						}
						else {
								logger.debug(qq2);
								pstmt = con.prepareStatement(qq2);
								jj=1;
								pstmt.setString(jj++,id);
								pstmt.setString(jj++,project_id);
								rs = pstmt.executeQuery();
								if(rs.next()){
										update_length = rs.getInt(1);
										if(update_length > 0)
												end_date = Helper.getToday();
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
						pstmt.setDate(3, new java.sql.Date(df.parse(date).getTime()));						
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






































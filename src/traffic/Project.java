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

public class Project implements java.io.Serializable{

		static final long serialVersionUID = 17L;	
   
		static Logger logger = Logger.getLogger(Project.class);
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String id="", name=""; 
		String owner_id ="", type_id="", funding_source_id="",date_time="", category_id="", sub_category_id="", sub_sub_category_id="", description="",lead_id="",eng_lead_id="",request_date = "", length="", file_path="", des_no = "", est_end_date="", actual_end_date="", est_cost="", actual_cost="";
		//
		// objects
		//
		User user = null;
		Type type = null;
		Type owner = null, funding_source = null, category = null;
		SubType	sub_category = null, sub_sub_category = null;
		User lead = null, eng_lead = null;
		ProjectUpdate lastProjectUpdate = null;
		
		public Project(){
		}	
		public Project(String val){
				setId(val);
		}	
		public Project(
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9,
									 String val10,
									 String val11,
									 String val12,
									 String val13,
									 String val14,
									 String val15,
									 String val16,
									 String val17,
									 String val18,
									 String val19									 
									 ){
				setValues( val,
									 val2,
									 val3,
									 val4,
									 val5,
									 val6,
									 val7,
									 val8,
									 val9,
									 val10,
									 val11,
									 val12,
									 val13,
									 val14,
									 val15,
									 val16,
									 val17,
									 val18,
									 val19									 
									 );
		
		}
		void setValues(
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9,
									 String val10,
									 String val11,
									 String val12,
									 String val13,
									 String val14,
									 String val15,
									 String val16,
									 String val17,
									 String val18,
									 String val19						   
									 ){
				setId(val);
				setName(val2);
				setOwner_id(val3);
				setType_id(val4);
				setFunding_source_id(val5);
				setCategory_id(val6);		
				setSub_category_id(val7);
				setSub_sub_category_id(val8);
				setDescription(val9);
				setLead_id(val10);
				setEng_lead_id(val11);
				setRequest_date(val12);
				setLength(val13);
				setFile_path(val14);
				setDes_no(val15);
				setEst_end_date(val16);
				setActual_end_date(val17);
				setEst_cost(val18);
				setActual_cost(val19);
		}
				 

		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setName(String val){
				if(val != null)
						name = val;
		}	
		public void setOwner_id(String val){
				if(val != null && !val.equals("-1"))
						owner_id = val;
		}
		public void setType_id(String val){
				if(val != null && !val.equals("-1"))
						type_id = val;
		}	
		public void setFunding_source_id(String val){
				if(val != null && !val.equals("-1"))
						funding_source_id = val;
		}	
		public void setCategory_id(String val){
				if(val != null && !val.equals("-1"))
						category_id = val;
		}
		public void setSub_category_id(String val){
				if(val != null && !val.equals("-1"))
						sub_category_id = val;
		}
		public void setSub_sub_category_id(String val){
				if(val != null && !val.equals("-1"))
						sub_sub_category_id = val;
		}	
		public void setDescription(String val){
				if(val != null)
						description = val;
		}
		public void setLead_id(String val){
				if(val != null && !val.equals("-1"))
						lead_id = val;
		}
		public void setEng_lead_id(String val){
				if(val != null && !val.equals("-1"))
						eng_lead_id = val;
		}
		public void setRequest_date(String val){
				if(val != null)
						request_date = val;
		}
		public void setLength(String val){
				if(val != null)
						length = val;
		}
		public void setFile_path(String val){
				if(val != null)
						file_path = val;
		}
		public void setDes_no(String val){
				if(val != null)
						des_no = val;
		}
		public void setEst_end_date(String val){
				if(val != null)
						est_end_date = val;
		}
		public void setActual_end_date(String val){
				if(val != null)
						actual_end_date = val;
		}
		public void setEst_cost(String val){
				if(val != null)
						est_cost = val;
		}
		public void setActual_cost(String val){
				if(val != null)
						actual_cost = val;
		}
		public void setUser(User val){
				if(val != null)
						user = val;
		}		
		//
		public String getId(){
				return id;
		}
		public String getName(){
				return name;
		}
		public String getOwner_id(){
				return owner_id;
		}	
		public String getType_id(){
		
				return type_id;
		}
		public String getFunding_source_id(){
		
				return funding_source_id;
		}
		public String getCategory_id(){
				return category_id;
		}	
		public String getSub_category_id(){
				return sub_category_id;
		}
		public String getSub_sub_category_id(){
				return sub_sub_category_id;
		}		
		public String getDescription(){
				return description;
		}
		public String getLead_id(){
				return lead_id;
		}
		public String getEng_lead_id(){
				return eng_lead_id;
		}
		public String getRequest_date(){
				return request_date;
		}
		public String getLength(){
				return length;
		}
		public String getFile_path(){
				return file_path;
		}
		public String getDes_no(){
				return des_no;
		}
		public String getEst_end_date(){
				return est_end_date;
		}
		public String getActual_end_date(){
				return actual_end_date;
		}
		public String getEst_cost(){
				return est_cost;
		}
		public String getActual_cost(){
				return actual_cost;
		}
		
		public boolean hasSubCategory(){
			 return (!category_id.equals("") &&
								(category_id.equals("1") ||
								 category_id.equals("5") ||
								 category_id.equals("6")));
		}
		public boolean hasSubSubCategory(){
			 return (!category_id.equals("") &&
								(sub_category_id.equals("4") ||
								 sub_category_id.equals("5")));
		}		
		public boolean hasDescription(){
				return !description.equals("");
		}
		public String toString(){
				return name;
		}
		public Type getOwner(){
				if(!owner_id.equals("") && owner == null){
						Type one = new Type(owner_id, null, "project_owners");
						String back = one.doSelect();
						if(back.equals("")){
								owner = one;
						}
				}
				return owner;
		}
		public Type getType(){
				if(!type_id.equals("") && type == null){
						Type one = new Type(type_id, null, "project_types");
						String back = one.doSelect();
						if(back.equals("")){
								type = one;
						}
				}
				return type;
		}
		public Type getFunding_source(){
				if(!funding_source_id.equals("") && funding_source == null){
						Type one = new Type(funding_source_id, null, "funding_sources");
						String back = one.doSelect();
						if(back.equals("")){
								funding_source = one;
						}
				}
				return funding_source;
		}
		public Type getCategory(){
				if(!category_id.equals("") && category == null){
						Type one = new Type(category_id, null, "categories");
						String back = one.doSelect();
						if(back.equals("")){
								category = one;
						}
				}
				return category;
		}
		//
		public SubType getSub_category(){
				if(!sub_category_id.equals("") && sub_category == null){
						SubType one = new SubType(sub_category_id, null, null, "sub_categories");
						String back = one.doSelect();
						if(back.equals("")){
								sub_category = one;
						}
				}
				return sub_category;
		}
		public SubType getSub_sub_category(){
				if(!sub_sub_category_id.equals("") && sub_sub_category == null){
						SubType one = new SubType(sub_sub_category_id, null, null, "sub_sub_categories");
						String back = one.doSelect();
						if(back.equals("")){
								sub_sub_category = one;
						}
				}
				return sub_sub_category;
		}		
		public User getLead(){
				if(!lead_id.equals("") && lead == null){
						User one = new User(lead_id);
						String back = one.doSelect();
						if(back.equals("")){
								lead = one;
						}
				}
				return lead;
		}
		public User getEng_lead(){
				if(!eng_lead_id.equals("") && eng_lead == null){
						User one = new User(eng_lead_id);
						String back = one.doSelect();
						if(back.equals("")){
								eng_lead = one;
						}
				}
				return eng_lead;
		}		
		public ProjectUpdate getLastProjectUpdate(){
				if(lastProjectUpdate == null && !id.equals("")){
						ProjectUpdateList pul = new ProjectUpdateList(id);
						String back = pul.find();
						if(back.equals("")){
								List<ProjectUpdate> all = pul.getUpdates();
								if(all != null && all.size() > 0){
										lastProjectUpdate = all.get(0);
								}
						}
				}
				return lastProjectUpdate;
		}
		public boolean hasProjectUpdates(){
				return getLastProjectUpdate() != null;
		}
		/**
		 * check if we can add more updates to this project
		 */
		public boolean canHaveMoreUpdates(){
				getLastProjectUpdate();
				return (lastProjectUpdate == null ||
								!(lastProjectUpdate.isCancelled() ||
									lastProjectUpdate.isComplete()));
		}
		public String doSave(){

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(request_date.equals(""))
						request_date = Helper.getToday();
				String qq = "insert into projects values(0,?,?,?,?, ?,?,?,?,?, "+
						"?,?,?,?,?, ?,?,?,?)";
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
						pstmt.setString(1, name);
						if(owner_id.equals(""))
								pstmt.setNull(2, Types.INTEGER);		
						else
								pstmt.setString(2, owner_id);
						if(type_id.equals(""))
								pstmt.setNull(3, Types.INTEGER);
						else
								pstmt.setString(3, type_id);
						if(funding_source_id.equals(""))
								pstmt.setNull(4, Types.INTEGER);
						else
								pstmt.setString(4, funding_source_id);
						if(category_id.equals(""))
								pstmt.setNull(5, Types.INTEGER);
						else
								pstmt.setString(5, category_id);
						if(sub_category_id.equals(""))
								pstmt.setNull(6, Types.INTEGER);
						else
								pstmt.setString(6, sub_category_id);
						if(sub_sub_category_id.equals(""))
								pstmt.setNull(7, Types.INTEGER);
						else
								pstmt.setString(7, sub_sub_category_id);
				
						if(!description.equals(""))
								pstmt.setString(8, description);
						else
								pstmt.setNull(8, Types.VARCHAR);				
						if(!lead_id.equals(""))
								pstmt.setString(9, lead_id);
						else
								pstmt.setNull(9, Types.INTEGER);
						if(!eng_lead_id.equals(""))
								pstmt.setString(10, eng_lead_id);
						else
								pstmt.setNull(10, Types.INTEGER);
						// 
						if(!request_date.equals(""))
								pstmt.setDate(11, new java.sql.Date(dateFormat.parse(request_date).getTime()));
						else
								pstmt.setNull(11, Types.DATE);
						if(!length.equals(""))
								pstmt.setString(12, length);
						else
								pstmt.setNull(12, Types.INTEGER);
						if(!file_path.equals(""))
								pstmt.setString(13, file_path);
						else
								pstmt.setNull(13, Types.VARCHAR);
						if(!des_no.equals(""))
								pstmt.setString(14, des_no);
						else
								pstmt.setNull(14, Types.VARCHAR);
						if(!est_end_date.equals(""))
								pstmt.setDate(15, new java.sql.Date(dateFormat.parse(est_end_date).getTime()));
						else
								pstmt.setNull(15, Types.DATE);
						if(!actual_end_date.equals(""))
								pstmt.setDate(16, new java.sql.Date(dateFormat.parse(actual_end_date).getTime()));
						else
								pstmt.setNull(16, Types.DATE);
						if(!est_cost.equals(""))
								pstmt.setString(17, est_cost);
						else
								pstmt.setNull(17, Types.DECIMAL);
						if(!actual_cost.equals(""))
								pstmt.setString(18, actual_cost);
						else
								pstmt.setNull(18, Types.DECIMAL);
				}
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
				}
				return msg;
		}
		//
		String doUpdate(){
				//
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "update projects set name=?,owner_id=?,type_id=?,"+
						"funding_source_id=?,category_id=?,sub_category_id=?,"+
						"sub_sub_category_id=?,description=?,lead_id=?,eng_lead_id=?,"+
						"request_date=?,length=?,file_path=?,DES_no=?,est_end_date=?,"+
						"actual_end_date=?,est_cost=?,actual_cost=? "+
						"where id=?";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						fillData(pstmt);
						pstmt.setString(19, id);
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
		String doDelete(){
				//
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
				String qq = "delete from projects where id=?";
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
		
				String qq = "select r.id,r.name,r.owner_id,r.type_id,r.funding_source_id,r.category_id, r.sub_category_id,r.sub_sub_category_id,r.description,r.lead_id,r.eng_lead_id,date_format(r.request_date,'%m/%d/%Y'),r.length,r.file_path,r.DES_no,date_format(r.est_end_date,'%m/%d/%Y'),date_format(r.actual_end_date,'%m/%d/%Y'),r.est_cost,r.actual_cost from projects r where r.id=? ";
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
													rs.getString(18),
													rs.getString(19)
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






































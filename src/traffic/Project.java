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
		String owner_id ="", type_id="", funding_source_id="",date_time="", description="",lead_id="",eng_lead_id="",date = "", length="", file_path="", des_no = "", est_end_date="", actual_end_date="", est_cost="", actual_cost="";
		// example of geometry POINT(18 23), LINESTRING(0 0, 1 2,2 4)
		// polygons consist of linestrings, a closed exterior boundary and 
		// POLYGON((0 0,8 0,12 9,0 9,0 0),(5 3,4 5,7 9,3 7, 2 5))
		//
		String geometry ="";// holds map info lines, points, polys 
		//
		// objects
		//
		User user = null;
		Type type = null;
		Type owner = null, funding_source = null;
		
		User lead = null, eng_lead = null;
		List<Feature> features = null;
		ProjectUpdate lastProjectUpdate = null;
		String[] del_feature = null;
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
									 String val17
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
									 val17
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
									 String val17
									 ){
				setId(val);
				setName(val2);
				setOwner_id(val3);
				setType_id(val4);
				setFunding_source_id(val5);
				setDescription(val6);
				setLead_id(val7);
				setEng_lead_id(val8);
				setDate(val9);
				setLength(val10);
				setFile_path(val11);
				setDes_no(val12);
				setEst_end_date(val13);
				setActual_end_date(val14);
				setEst_cost(val15);
				setActual_cost(val16);
				setGeometry(val17);
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
		public void setDate(String val){
				if(val != null)
						date = val;
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
		public void setGeometry(String val){
				if(val != null){
						geometry = val;
				}
		}		
		// delete features
		public void setDel_feature(String[] vals){
				del_feature = vals;
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

		public String getDescription(){
				return description;
		}
		public String getLead_id(){
				return lead_id;
		}
		public String getEng_lead_id(){
				return eng_lead_id;
		}
		public String getDate(){
				if(date.equals("")){
						date = Helper.getToday();
				}
				return date;
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
		public boolean hasDescription(){
				return !description.equals("");
		}
		public String toString(){
				return name;
		}
		public String getGeometry(){
				return geometry;
		}
		public boolean hasGeometry(){
				return !geometry.equals("");
		}		
		public Type getOwner(){
				if(!owner_id.equals("") && owner == null){
						Type one = new Type(owner_id, null, "owners");
						String back = one.doSelect();
						if(back.equals("")){
								owner = one;
						}
				}
				return owner;
		}
		public List<Feature> getFeatures(){
				if(!id.equals("") && features == null){
						FeatureList fl = new FeatureList(id);
						String back = fl.find();
						if(back.equals("")){
								features = fl.getFeatures();
						}
				}
				return features;
		}
		public String getFirstFeatureId(){
				String ret = "0"; // no feature for map purpose
				if(features == null) getFeatures();
				if(features != null){
						Feature one = features.get(0);
						ret = one.getFeature_id();
				}
				return ret;
		}
		public boolean hasFeatures(){
				getFeatures();
				return features != null && features.size() > 0;
		}
		public String getAllFeaturesText(){
				String all="";
				getFeatures();
				if(features != null){
						for(Feature one:features){
								if(!all.equals(""))
										all += ", ";
								all += one.toString();
						}
				}
				return all;
		}
		public Type getType(){
				if(!type_id.equals("") && type == null){
						Type one = new Type(type_id, null, "types");
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
		//
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
		@Override
		public int hashCode() {
				int hash = 3, id_int = 0;
				if(!id.equals("")){
						try{
								id_int = Integer.parseInt(id);
						}catch(Exception ex){}
				}
				hash = 53 * hash + id_int;
				return hash;
		}
		@Override
		public boolean equals(Object obj) {
				if (obj == null) {
						return false;
				}
				if (getClass() != obj.getClass()) {
						return false;
				}
				final Project other = (Project) obj;
				return this.id.equals(other.id);
		}
		//
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
				if(date.equals(""))
						date = Helper.getToday();
				String qq = "insert into projects values(0,?,?,?,?, ?,?, "+
						"?,?,?,?,?, ?,?,?,?,";
				if(geometry.equals("")){
						qq += "null)";
				}
				else{
						qq += " GeomFromText(?))";
				}
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
						if(!description.equals(""))
								pstmt.setString(5, description);
						else
								pstmt.setNull(5, Types.VARCHAR);				
						if(!lead_id.equals(""))
								pstmt.setString(6, lead_id);
						else
								pstmt.setNull(6, Types.INTEGER);
						if(!eng_lead_id.equals(""))
								pstmt.setString(7, eng_lead_id);
						else
								pstmt.setNull(7, Types.INTEGER);
						// 
						if(!date.equals(""))
								pstmt.setDate(8, new java.sql.Date(dateFormat.parse(date).getTime()));
						else
								pstmt.setNull(8, Types.DATE);
						if(!length.equals(""))
								pstmt.setString(9, length);
						else
								pstmt.setNull(9, Types.INTEGER);
						if(!file_path.equals(""))
								pstmt.setString(10, file_path);
						else
								pstmt.setNull(10, Types.VARCHAR);
						if(!des_no.equals(""))
								pstmt.setString(11, des_no);
						else
								pstmt.setNull(11, Types.VARCHAR);
						if(!est_end_date.equals(""))
								pstmt.setDate(12, new java.sql.Date(dateFormat.parse(est_end_date).getTime()));
						else
								pstmt.setNull(12, Types.DATE);
						if(!actual_end_date.equals(""))
								pstmt.setDate(13, new java.sql.Date(dateFormat.parse(actual_end_date).getTime()));
						else
								pstmt.setNull(13, Types.DATE);
						if(!est_cost.equals(""))
								pstmt.setString(14, est_cost);
						else
								pstmt.setNull(14, Types.DECIMAL);
						if(!actual_cost.equals(""))
								pstmt.setString(15, actual_cost);
						else
								pstmt.setNull(15, Types.DECIMAL);
						if(!geometry.equals(""))
								pstmt.setString(16, geometry);
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
				int cc = 17;
				String qq = "update projects set name=?,owner_id=?,type_id=?,"+
						"funding_source_id=?,"+
						"description=?,lead_id=?,eng_lead_id=?,"+
						"date=?,length=?,file_path=?,DES_no=?,est_end_date=?,"+
						"actual_end_date=?,est_cost=?,actual_cost=?,";
				if(geometry.equals("")){
						qq += "geometry=null ";
						cc = 16;
				}
				else
						qq += "geometry=GeomFromText(?) ";
				qq += "where id=?";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						fillData(pstmt);
						pstmt.setString(cc, id);
						pstmt.executeUpdate();
				}
				catch(Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				if(del_feature != null){
						msg += doDeleteFeatures();
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
		String doDeleteFeatures(){
				//
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
				String qq = "delete from project_features where id=?";
				//
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						for(String str:del_feature){
								pstmt.setString(1, str);
								pstmt.executeUpdate();
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
		String doSelect(){
		
				String qq = "select r.id,r.name,r.owner_id,r.type_id,r.funding_source_id,r.description,r.lead_id,r.eng_lead_id,date_format(r.date,'%m/%d/%Y'),r.length,r.file_path,r.DES_no,date_format(r.est_end_date,'%m/%d/%Y'),date_format(r.actual_end_date,'%m/%d/%Y'),r.est_cost,r.actual_cost,AsText(r.geometry) from projects r where r.id=? ";
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
													rs.getString(17)
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






































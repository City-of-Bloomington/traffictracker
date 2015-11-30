package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.log4j.Logger;

public class Feature implements java.io.Serializable{

    String id = "", project_id="", feature_id="", sub_id="", sub_sub_id="",
				name="", sub_name="", sub_sub_name="", tableName="features", type="New";
		static final long serialVersionUID = 115L;		
		static Logger logger = Logger.getLogger(Feature.class);
    public Feature(){
    }
    public Feature(String val){
				setId(val);
    }
    public Feature(String val, String val2){
				setId(val);
				setProject_id(val2);
    }
    public Feature(String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9
									 ){
				setValues(val, val2, val3, val4, val5, val6, val7, val8, val9);
    }
		void setValues(String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9){
				setId(val);
				setProject_id(val2);
				setFeature_id(val3);
				setSub_id(val4);
				setSub_sub_id(val5);
				setName(val6);
				setSub_name(val7);
				setSub_sub_name(val8);
				setType(val9);
		}
    //
    //
    public String getId(){
				return id;
    }
    public String getProject_id(){
				return project_id;
    }
    public String getFeature_id(){
				return feature_id;
    }
    public String getSub_id(){
				return sub_id;
    }
    public String getSub_sub_id(){
				return sub_sub_id;
    }		
    public String getName(){
				return name;
    }
    public String getSub_name(){
				return sub_name;
    }
    public String getSub_sub_name(){
				return sub_sub_name;
    }
		public String getType(){
				return type;
    }
    //
    // setters
    //
    public void setId (String val){
				if(val != null)
						id = val;
    }
    public void setName (String val){
				if(val != null)
						name = val;
    }
    public void setType (String val){
				if(val != null)
						type = val;
    }		
    public void setSub_name (String val){
				if(val != null)
						sub_name = val;
    }
    public void setSub_sub_name (String val){
				if(val != null)
						sub_sub_name = val;
    }		
    public void setProject_id (String val){
				if(val != null)
						project_id = val;
    }
    public void setFeature_id (String val){
				if(val != null && !val.equals("-1"))
						feature_id = val;
    }
    public void setSub_id (String val){
				if(val != null && !val.equals("-1"))
						sub_id = val;
    }
    public void setSub_sub_id (String val){
				if(val != null && !val.equals("-1"))
						sub_sub_id = val;
    }		
    public String toString(){
				String ret = name;
				if(!sub_name.equals("")){
						if(!ret.equals("")) ret += "/";
						ret += sub_name;
				}
				if(!sub_sub_name.equals("")){
						if(!ret.equals("")) ret += "/";
						ret += sub_sub_name;
				}
				if(!type.equals("")){
						ret += " ("+type+")";
				}
				return ret;
    }
		public boolean hasSubFeature(){
				return !feature_id.equals("") &&
						(feature_id.equals("1") ||
						 feature_id.equals("5") ||
						 feature_id.equals("6"));
		}
		public boolean hasSubSubFeature(){
				return !feature_id.equals("") &&
						!sub_id.equals("") &&
						(sub_id.equals("4") ||
						 sub_id.equals("5"));
		}		
		/**
		 * needed for the project to issue a Save or not
		 */
		public boolean hasData(){
				return !feature_id.equals(""); 
		}
		String doSelect(){
				String msg = "";
				String qq = " select pf.id,pf.project_id,pf.feature_id,pf.sub_id,pf.sub_sub_id,f.name,sf.name,ssf.name,pf.type "+
						"from project_features pf "+
						"left join features f on f.id=pf.feature_id "+
						"left join sub_features sf on sf.id=pf.sub_id "+
						"left join sub_sub_features ssf on ssf.id=pf.sub_sub_id "+
						"where pf.id=?";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
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
								setId(rs.getString(1));
								setProject_id(rs.getString(2));
								setFeature_id(rs.getString(3));
								setSub_id(rs.getString(4));
								setSub_sub_id(rs.getString(5));
								setName(rs.getString(6));
								setSub_name(rs.getString(7));
								setSub_sub_name(rs.getString(8));
								setType(rs.getString(9));
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
		public String doSave(){

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				if(project_id.equals("")){
						msg = " project id not set";
						return msg;
				}
				if(feature_id.equals("")){ // we need at least the main feature
						msg = " no feature set";
						return msg;
				}
				String qq = "insert into project_features values(0,?,?,?,?,?)";
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
				if(!id.equals("")){
						msg += doSelect(); // so that we can get the names
				}
				return msg;
		}
		String fillData(PreparedStatement pstmt){
				String msg = "";
				try{
						pstmt.setString(1, project_id);
						pstmt.setString(2, feature_id);
						if(sub_id.equals(""))
								pstmt.setNull(3, Types.INTEGER);
						else
								pstmt.setString(3, sub_id);
						if(sub_sub_id.equals(""))
								pstmt.setNull(4, Types.INTEGER);
						else
								pstmt.setString(4, sub_sub_id);
						if(type.equals(""))
								pstmt.setNull(5, Types.INTEGER);
						else
								pstmt.setString(5, type);						
				}
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
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
}

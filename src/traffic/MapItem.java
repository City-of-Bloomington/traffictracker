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
/**
 * we are assuming that each project has one map item, either point
 * or linestring but this may change later
 */
public class MapItem implements java.io.Serializable{

    String id = "", geometry="";
		static final long serialVersionUID = 115L;		
		static Logger logger = Logger.getLogger(MapItem.class);
    public MapItem(){
    }
    public MapItem(String val){
				setId(val);
    }
    public MapItem(String val, String val2){
				setId(val);
				setGeometry(val2);
    }		
    //
    //
    public String getId(){
				return id;
    }
    public String getGeometry(){
				return geometry;
    }
    //
    // setters
    //
    public void setId (String val){
				if(val != null)
						id = val;
    }
    public void setGeometry(String val){
				if(val != null)
						geometry = val;
    }
    public String toString(){
				return geometry;
    }
		String doSelect(){
				String msg = "";
				String qq = " select asText(geometry) from projects where id=?";
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
								geometry = rs.getString(1);
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
		/**
		 * handle save or update as project is created first then map item
		 * is added later
		 */
		String doSave(){
				//
				String msg = "";
				String qq = " update projects set geometry= ";
				if(geometry.equals(""))
						qq += " null ";
				else
						qq += " GeomFromText(?)";
				qq += " where id=?";
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
						int cc = 1;
						pstmt = con.prepareStatement(qq);
						if(!geometry.equals(""))
								pstmt.setString(cc++, geometry);
						pstmt.setString(cc++, id);
						pstmt.executeUpdate();	
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

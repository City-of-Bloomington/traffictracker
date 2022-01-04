package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Type implements java.io.Serializable{

    String id = "", name="", tableName="types";
    boolean debug = false;
		static final long serialVersionUID = 110L;		
		static Logger logger = LogManager.getLogger(Type.class);
    public Type(){
    }
    public Type(String val){
				setId(val);
    }
    public Type(String val, String val2){
				setId(val);
				setName(val2);
    }		
    public Type(String val, String val2, String val3){
				setId(val);
				setName(val2);
				setTableName(val3);
    }
		


    //
    //
    public String getId(){
				return id;
    }
    public String getName(){
				return name;
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
    public void setTableName (String val){
				if(val != null)
						tableName = val;
    }	
    public String toString(){
				return name;
    }
		String doSelect(){
				String msg = "";
				String qq = " select name from "+tableName+" where id=?";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(debug)
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
								name = rs.getString(1);
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

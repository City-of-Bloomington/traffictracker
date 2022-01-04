package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * SubType class
 *
 */

public class SubType extends Type{

		String type_id = ""; 
		static final long serialVersionUID = 115L;
		//
		static Logger logger = LogManager.getLogger(SubType.class);
    public SubType(){
    }
    public SubType(String val){
				super(val);
    }
    public SubType(String val, String val2, String val3){
				super(val, val3);
				setType_id(val2);
    }
    public SubType(String val, String val2, String val3, String val4){
				super(val, val3);
				setType_id(val2);
				setTableName(val4);
    }		
		
    //
    public String getType_id(){
				return type_id; // could be sub_id or sub_sub_id
    }
    //
    // setters
    //
    public void setType_id (String val){
				if(val != null)
						type_id = val;
    }
    public String toString(){
				return name;
    }
		String doSelect(){
				String msg = "";
				String qq = " select * from "+tableName+" where id=?";
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
								type_id = rs.getString(2);								
								name = rs.getString(3);
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

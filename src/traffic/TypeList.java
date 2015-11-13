package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.log4j.Logger;

public class TypeList{

    String whichList ="project_types";
		static final long serialVersionUID = 120L;		
		static Logger logger = Logger.getLogger(TypeList.class);
		List<Type> types = null;
		String name = "";
    public TypeList(){
    }	
    public TypeList(String val){
				setWhichList(val);
    }
    //
    // setters
    //
    public void setWhichList(String val){
				if(val != null)
						whichList = val;
    }
    public void setName(String val){
				if(val != null)
						name = val;
    }	
		public List<Type> getTypes(){
				return types;
		}
		String find(){
				String msg = "";
				String qq = " select id,name from "+whichList;
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(!name.equals("")){
						qq += " where name like ? ";
				}
				String qo = " order by id ";
				qq += qo;
				logger.debug(qq);
				try{
						types = new ArrayList<Type>();
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						if(!name.equals("")){
								pstmt.setString(1, "%"+name+"%");
						}
						rs = pstmt.executeQuery();	
						while(rs.next()){
								Type one = new Type(rs.getString(1), rs.getString(2));
								types.add(one);
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

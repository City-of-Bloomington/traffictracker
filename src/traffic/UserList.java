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

public class UserList{

		static final long serialVersionUID = 1120L;		
		static Logger logger = Logger.getLogger(UserList.class);
		String fullname = "";
		List<User> users = null;
		String name = "";

    public UserList(){
    }	
    //
    // setters
    //
		public List<User> getUsers(){
				return users;
		}
		String find(){
				String msg = "";
				String qq = " select * from users ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				qq += " where type is not null and active is not null"; // type Plan, Eng All to avoid IT staff
				if(!fullname.equals("")){
						qq += " and fullname like ? ";
				}
				String qo = " order by fullname ";
				qq += qo;
				//
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						users = new ArrayList<User>();
						pstmt = con.prepareStatement(qq);
						if(!fullname.equals("")){
								pstmt.setString(1, "%"+fullname+"%");
						}
						rs = pstmt.executeQuery();	
						while(rs.next()){
								User one = new User(rs.getString(1),
																		rs.getString(2),
																		rs.getString(3),
																		rs.getString(4),
																		rs.getString(5),
																		rs.getString(6)
																		);
								users.add(one);
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

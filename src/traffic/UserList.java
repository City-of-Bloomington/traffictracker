package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList{

		static final long serialVersionUID = 1120L;		
		static Logger logger = LogManager.getLogger(UserList.class);
		String fullname = "";
		boolean eng_only = false, all = false;
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
		public void setEngOnly(){
				eng_only = true;
		}
		public void setAll(){
				all = true;
		}
		String find(){
				String msg = "";
				String qq = " select * from users ", qw = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(!all){
						qw += " type is not null and active is not null"; // type Plan, Eng All to avoid IT staff
				}
				if(!fullname.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " fullname like ? ";
				}
				if(eng_only){
						if(!qw.equals("")) qw += " and ";						
						qw += " type = 'Eng' ";
				}
				if(!qw.equals("")){
						qw = " where "+qw;
				}
				String qo = " order by fullname ";
				qq += qw+" "+qo;
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

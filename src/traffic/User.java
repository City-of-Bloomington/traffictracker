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
 * User class
 *
 */

public class User implements java.io.Serializable{

    String userid="", fullname="", role="", id="", active="",
				type=""; // Plan Eng All
    boolean userExists = false;
		static final long serialVersionUID = 135L;		
		static Logger logger = Logger.getLogger(User.class);
    String errors = "";
    public User(){
    }		
    public User(String val){
				setId(val);
    }	
    public User(String val, String val2){
				setId(val);
				setUserid(val2);
    }
		public User(String val, String val2,
								String val3, String val4,
								String val5, String val6){
				setValues(val, val2, val3, val4, val5, val6);
		}
		void setValues(String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6){
				setId(val);
				setUserid(val2);
				setFullname(val3);
				setRole(val4);
				setActive(val5);
				setType(val6);
				userExists = true;
		}
    //
    public boolean hasRole(String val){
				if(role.indexOf(val) > -1) return true;
				return false;
    }
		public boolean canEdit(){
				return hasRole("Edit");
    }
		public boolean canDelete(){
				return (hasRole("Delete") || isAdmin());
    }
		public boolean isAdmin(){
				return hasRole("Admin");
    }
		public boolean hasUserid(){
				return !userid.equals("");
		}
		//
    // getters
    //
    public String getId(){
				return id;
    }	
    public String getUserid(){
				return userid;
    }
    public String getFullname(){
				return fullname;
    }
    public String getRole(){
				return role;
    }
		public boolean isActive(){
				return !active.equals("");
		}
    public String getType(){
				return type;
    }
		public boolean isTypePlan(){
				return type.equals("Plan") || type.equals("All");
		}
		public boolean isTypeEng(){
				return type.equals("Eng") || type.equals("All");
		}
		public boolean hasType(){
				return !type.equals("");
		}
    //
    // setters
    //
    public void setId(String val){
				if(val != null)
						id = val;
    }	
    public void setUserid (String val){
				if(val != null)
						userid = val;
    }
    public void setFullname (String val){
				if(val != null)
						fullname = val;
    }
    public void setRole (String val){
				if(val != null)
						role = val;
    }
    public void setType(String val){
				if(val != null)
						type = val;
    }		
    public void setActive (String val){
				if(val != null)
						active = val;
    }
		public boolean userExists(){
				return userExists;
		}
    public String toString(){
				return fullname;
    }
		@Override
		public int hashCode() {
				int hash = 7, id_int = 0;
				if(!id.equals("")){
						try{
								id_int = Integer.parseInt(id);
						}catch(Exception ex){}
				}
				hash = 67 * hash + id_int;
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
				final User other = (User) obj;
				return this.id.equals(other.id);
		}	
		public String doSelect(){
				String msg="";
				PreparedStatement pstmt = null;
				Connection con = null;
				ResultSet rs = null;		
				String qq = "select * from users ", qw="";
				if(!userid.equals("")){
						qw += " where userid = ?";
				}
				else if(!id.equals("")){
						qw += " where id = ?";
				}
				qq += qw;
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg += " could not connect to database";
						// System.err.println(msg);
						return msg;
				}		
				try{
						pstmt = con.prepareStatement(qq);
						if(!userid.equals(""))
								pstmt.setString(1, userid);
						else
								pstmt.setString(1, id);				
						rs = pstmt.executeQuery();
						if(rs.next()){
								String str = rs.getString(1);
								id = str;
								str = rs.getString(2);
								if(str != null)
										userid = str;
								str = rs.getString(3);
								if(str != null)
										fullname = str;
								str = rs.getString(4);
								if(str != null)
										role = str;
								str = rs.getString(5);
								if(str != null)
										active = str;
								str = rs.getString(6);
								if(str != null)
										type = str;								
								userExists = true;
						}
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
	
}

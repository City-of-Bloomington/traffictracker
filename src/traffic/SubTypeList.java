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

public class SubTypeList{

    String whichList ="sub_features";
		static final long serialVersionUID = 130L;		
		static Logger logger = Logger.getLogger(SubTypeList.class);
		
		List<SubType> subTypes = null;
		String name = "", feature_id="", sub_id="";
    public SubTypeList(){
    }	
    public SubTypeList(String val){
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
 
    public void setFeature_id(String val){
				if(val != null)
						feature_id = val;
    }
    public void setSub_id(String val){
				if(val != null)
						sub_id = val;
    }		
		public List<SubType> getSubTypes(){
				return subTypes;
		}
		String find(){
				String msg = "";
				String qq = " select * from "+whichList, qw="";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				if(!feature_id.equals("")){
						qw += " feature_id = ? ";
				}
				if(!sub_id.equals("")){
						qw += " sub_id = ? ";
				}				
				if(!name.equals("")){
						qw += " name like ? ";
				}
				if(!qw.equals("")){
						qq = qq + " where "+qw;
				}
				String qo = " order by id ";
				qq += qo;
				logger.debug(qq);
				try{
						subTypes = new ArrayList<SubType>();
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!feature_id.equals("")){
								pstmt.setString(jj++, feature_id);
						}
						if(!sub_id.equals("")){
								pstmt.setString(jj++, sub_id);
						}						
						if(!name.equals("")){
								pstmt.setString(jj++, "%"+name+"%");
						}
						rs = pstmt.executeQuery();	
						while(rs.next()){
								SubType one = new SubType(rs.getString(1), rs.getString(2), rs.getString(3));
								subTypes.add(one);
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

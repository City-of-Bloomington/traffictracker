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

public class FeatureList{

		static final long serialVersionUID = 1225L;		
		static Logger logger = LogManager.getLogger(FeatureList.class);
		String project_id = "", exclude_id="";
		List<Feature> features = null;
		String name = "";

    public FeatureList(){
    }
    public FeatureList(String val){
				setProject_id(val);
    }		
    //
    // setters
    //
		public void setProject_id(String val){
				if(val != null)
						project_id = val;
		}
		public void exclude_id(String val){
				if(val != null)
						exclude_id = val;
		}		
		//
		// getters
		//
		public List<Feature> getFeatures(){
				return features;
		}
		String find(){
				String msg = "";
				String qq = " select pf.id,pf.project_id,pf.feature_id,pf.sub_id,pf.sub_sub_id,f.name,sf.name,ssf.name,pf.type,pf.length "+
						"from project_features pf "+
						"left join features f on f.id=pf.feature_id "+
						"left join sub_features sf on sf.id=pf.sub_id "+
						"left join sub_sub_features ssf on ssf.id=pf.sub_sub_id ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qw = "";
				if(!project_id.equals("")){
						qw = " pf.project_id = ? ";
				}
				if(!exclude_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " not pf.id = ? ";
				}
				String qo = " order by pf.id ";
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				qq += qo;
				//
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj =1;
						if(!project_id.equals("")){
								pstmt.setString(jj++, project_id);
						}
						if(!exclude_id.equals("")){
								pstmt.setString(jj++, exclude_id);
						}
						rs = pstmt.executeQuery();	
						while(rs.next()){
								if(features == null)
										features = new ArrayList<Feature>();
								Feature one = new Feature(rs.getString(1),
																					rs.getString(2),
																					rs.getString(3),
																					rs.getString(4),
																					rs.getString(5),
																					rs.getString(6),
																					rs.getString(7),
																					rs.getString(8),
																					rs.getString(9),
																					rs.getString(10)
																		);
								features.add(one);
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

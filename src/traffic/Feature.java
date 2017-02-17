package traffic;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import org.apache.log4j.Logger;

public class Feature implements java.io.Serializable{

    String id = "", project_id="", feature_id="", sub_id="", sub_sub_id="",
				name="", sub_name="", sub_sub_name="", tableName="features",
				type="New",
				length=""; // length or count
		// we need these to determine the length field as length or count
		private static final Set<Integer> f_ids = new HashSet<Integer>(Arrays.asList(2, 3, 4));
		private static final Set<Integer> s_ids = new HashSet<Integer>(Arrays.asList(5));
		private static final Set<Integer> s_s_ids = new HashSet<Integer>(Arrays.asList(8,9,10,11,12));

		static final long serialVersionUID = 115L;		
		static Logger logger = Logger.getLogger(Feature.class);
		List<Feature> features = null;
		List<SubType> sub_features = null, sub_sub_features = null;		
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
									 String val9,
									 String val10
									 ){
				setValues(val, val2, val3, val4, val5, val6, val7, val8, val9, val10);
    }
		void setValues(String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9,
									 String val10){
				setId(val);
				setProject_id(val2);
				setFeature_id(val3);
				setSub_id(val4);
				setSub_sub_id(val5);
				setName(val6);
				setSub_name(val7);
				setSub_sub_name(val8);
				setType(val9);
				setLength(val10);
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
		public String getLength(){
				return length;
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
		public void setLength(String val){
				if(val != null)
						length = val;
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
				if(!length.equals("")){
						ret += " ("+getLengthOrCount()+":"+length+")";
				}
				return ret;
    }
		public String getLengthOrCountUnit(){
				String ret = "ft";
				if(getLengthOrCount().equals("Count")) ret = "";
				return ret;
		}
		public String getLengthOrCount(){
				// feature 2,3,4 count
				// sub_feature 5 count
				// sub_sub_feature 8,9,10,11,12 count
				//
				// sub_feature 1,2,3,4,6,7,8,9,10  length
				// sub_sub_feature 1,2,3,4,5,6,7  length

				//
				String ret = "Length";
				int f_id=0,s_id=0,s_s_id=0;
				try{
						if(!feature_id.equals("")){
								f_id = Integer.parseInt(feature_id);
								if(!sub_id.equals("")){
										s_id = Integer.parseInt(sub_id);
										if(!sub_sub_id.equals("")){
												s_s_id = Integer.parseInt(sub_sub_id);
										}
								}
						}
				}catch(Exception ex){
						logger.error(ex);
				}
				if(f_ids.contains(f_id) ||
					 s_ids.contains(s_id) ||
					 s_s_ids.contains(s_s_id)){
						ret = "Count";
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
		public List<Feature> getFeatures(){
				if(features == null && !project_id.equals("")){
						FeatureList dl = new FeatureList(project_id);
						if(!id.equals("")){
								dl.exclude_id(id);
						}
						String back = dl.find();
						features = dl.getFeatures();
				}
				return features;
		}
		public boolean hasOtherFeatures(){
				getFeatures();
				return features != null && features.size() > 0;
		}
		public List<SubType> getSub_features(){
				if(sub_features == null && hasSubFeature()){
						SubTypeList dl = new SubTypeList("sub_features");
						dl.setFeature_id(feature_id);
						String back = dl.find();
						if(back.equals("")){
								sub_features = dl.getSubTypes();
								sub_features.add(0, new SubType("-1",null,"Pick Sub Feature"));
						}
				}
				else{
						List<SubType> temp = new ArrayList<SubType>();
						temp.add(new SubType("-1",null,"Pic Sub Feature"));
						return temp;
				}
				return sub_features;
		}
		public List<SubType> getSub_sub_features(){ 
				if(sub_sub_features == null && hasSubSubFeature()){
						SubTypeList dl = new SubTypeList("sub_sub_features");
						dl.setSub_id(sub_id);
						String back = dl.find();
						if(back.equals("")){
								sub_sub_features = dl.getSubTypes();
								sub_sub_features.add(0, new SubType("-1",null,"Pick Sub Feature"));
						}
				}
				else{
						List<SubType> temp = new ArrayList<SubType>();
						temp.add(new SubType("-1",null,"Pic Sub Feature"));
						return temp;
				}
				return sub_sub_features;
		}		
		/**
		 * needed for the project to issue a Save or not
		 */
		public boolean hasData(){
				return !feature_id.equals(""); 
		}
		String doSelect(){
				String msg = "";
				String qq = " select pf.id,pf.project_id,pf.feature_id,pf.sub_id,pf.sub_sub_id,f.name,sf.name,ssf.name,pf.type,pf.length "+
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
								setLength(rs.getString(10));
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
				String qq = "insert into project_features values(0,?,?,?,?,?,?)";
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
		public String doUpdate(){

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
				System.err.println(" id, length, project_id "+id+" "+length+" "+project_id);
				String qq = "update project_features set project_id=?,feature_id=?,sub_id=?,sub_sub_id=?,type=?,length=? where id=?";
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						msg = fillData(pstmt);
						pstmt.setString(7, id);
						pstmt.executeUpdate();
				}
				catch(Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				if(msg.equals("")){
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
						if(length.equals(""))
								pstmt.setNull(6, Types.INTEGER);
						else
								pstmt.setString(6, length);						
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

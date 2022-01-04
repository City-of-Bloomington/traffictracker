/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package traffic;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Report{
	
		static Logger logger = LogManager.getLogger(Report.class);
		static final long serialVersionUID = 70L;
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();	
		String year = "",date_from="",date_to="", type="";
		String title = "", which_date="p.date",by="", day="", prev_year="", next_year="";
		boolean byType=false, byOwner=true, byFund=false, byFeature=false, byLead=false, byRank=false, byStatus=false;
		List<List<ReportRow>> all = new ArrayList<List<ReportRow>>();
		Hashtable<String, ReportRow> all2 = new Hashtable<String, ReportRow>(4);
		DecimalFormat decFormat = new DecimalFormat("###,###.##");
		List<ReportRow> rows = null; 
		ReportRow columnTitle = null;
		//
		int totalIndex = 2; // DB index for row with 2 items
		public Report(){
		}
		public void setYear(String val){
				if(val != null && !val.equals("-1"))
						year = val;
		}
		public void setPrev_year(String val){
				if(val != null && !val.equals("-1"))
						prev_year = val;
		}
		public void setNext_year(String val){
				if(val != null && !val.equals("-1"))
						next_year = val;
		}	
		public void setDay(String val){
				if(val != null && !val.equals(""))
						day = val;
		}	
		public void setDate_from(String val){
				if(val != null)
						date_from = val;
		}	
		public void setDate_to(String val){
				if(val != null)
						date_to = val;
		}

		public void setByOwner(Boolean val){
				byOwner = val;
		}
		public void setByType(Boolean val){
				byType = val;
		}
		public void setByFund(Boolean val){
				byFund = val;
		}
		public void setByFeature(Boolean val){
				byFeature = val;
		}
		public void setByLead(Boolean val){
				byLead = val;
		}
		public void setByRank(Boolean val){
				byRank = val;
		}
		public void setByStatus(Boolean val){
				byStatus = val;
		}		
		//
		// getters
		//
		public String getYear(){
				return year;
		}
		public String getPrev_year(){
				return prev_year;
		}
		public String getNext_year(){
				return next_year;
		}	
		public String getDay(){
				return day;
		}	
		public String getDate_from(){
				return date_from ;
		}	
		public String getDate_to(){
				return date_to ;
		}
		public boolean getByOwner(){
				return byOwner;
		}
		public boolean getByType(){
				return byType;
		}
		public boolean getByFund(){
				return byFund;
		}
		public boolean getByFeature(){
				return byFeature;
		}
		public boolean getByLead(){
				return byLead;
		}
		public boolean getByRank(){
				return byRank;
		}
		public boolean getByStatus(){
				return byStatus;
		}		
		public String getTitle(){
				return title;
		}	
		public List<ReportRow> getRows(){
				return rows;
		}
		public List<List<ReportRow>> getAll(){
				return all;
		}
		public List<ReportRow> getInventoryList(){
				List<ReportRow> list = new ArrayList<ReportRow>();
				if(all2 != null){
						for(String key:all2.keySet()){
								ReportRow one = all2.get(key);
								list.add(one);
						}
				}
				return list;
		}
		public ReportRow getColumnTitle(){
				return columnTitle;
		}
		public String find(){
				String msg = "";
				if(!day.equals("")){
						date_from = day;
						date_to = Helper.getNextDay(day);
				}
				if(byOwner){
						msg +=  byOwner();				
				}
				if(byType){
						msg +=  byType();
				}
				if(byFund){
						msg +=  byFund();
				}
				if(byFeature){
						msg +=  byFeature();
				}
				if(byLead){
						msg +=  byLead();
				}
				if(byRank){
						msg +=  byRank();
				}
				if(byStatus){
						msg +=  byStatus();
				}				
				return msg;
		}
		void setTitle(){
				if(!day.equals("")){
						title +=" "+day;
				}
				else if(!year.equals("")){
						title +=" "+year;
				}
				else {
						if(!date_from.equals("")){
								title += " "+date_from;
						}
						if(!date_to.equals("")){
								if(!date_from.equals(date_to)){
										title += " - "+date_to;
								}
						}
				}
		}
		/**
		 * project classified by owner
		 */
		public String byOwner(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				which_date="p.date ";
				//
				qq = " select o.name name, count(*) amount from projects p left join owners o on p.owner_id=o.id ";
				qg = " group by name ";
		
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
				}
				qq += qg;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						title = "Projects Classified by Owners ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow();
						one.setRow("Owner","Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								one = new ReportRow(2);
								one.setRow(str,
													 rs.getString(2)
													 );
								total += rs.getInt(2);
								rows.add(one);
						}
						one = new ReportRow(2);
						one.setRow("Total",total);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}
		/**
		 * project classified by type
		 */
		public String byType(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				which_date="p.date ";
				//
				qq = " select t.name name, count(*) amount from projects p left join types t on p.type_id=t.id ";
				qg = " group by name ";
				//		
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
				}
				qq += qg;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}

						title = "Projects classified by Project Types ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow();
						one.setRow("Type","Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								one = new ReportRow(2);
								one.setRow(str,
													 rs.getString(2)
													 );
								total += rs.getInt(2);
								rows.add(one);
						}
						one = new ReportRow(2);
						one.setRow("Total",total);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}
		/**
		 * project classified by funding
		 */
		public String byFund(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				which_date="p.date ";
				//
				qq = " select f.name name, count(*) amount,sum(actual_cost) cost from projects p left join funding_sources f on p.funding_source_id=f.id ";
				qg = " group by name ";
		
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
				}
				qq += qg;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}

						title = "Projects Classified by Funding Resources ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow(3);
						one.setRow("Funding Type","Count","Actual Cost $");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;
						double total2 = 0.;
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								one = new ReportRow(3);
								one.setRow(str,
													 rs.getString(2),
													 decFormat.format(rs.getDouble(3))
													 );
								total += rs.getInt(2);
								total2 += rs.getDouble(3);
								rows.add(one);
						}
						one = new ReportRow(3);
						one.setRow("Total",""+total, decFormat.format(total2));
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}
		/**
		 * project classified by features
		 */
		public String byFeature(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				boolean types[] = {true, true, false};
				which_date="p.date ";
				//
				qq = " select f.name name, pf.type type, count(*) amount from projects p left join project_features pf on pf.project_id=p.id left join features f on f.id=pf.feature_id ";
				qg = " group by name,type order by name ";
				qq2 = " select count(*) from projects p ";
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
						qq2 += qw;
				}
				qq += qg;
				logger.debug(qq);
				logger.debug(qq2);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						qq = qq2;
						pstmt2 = con.prepareStatement(qq2);
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								pstmt2.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										pstmt2.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										pstmt2.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						title = "Projects Classified by Features ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow(3);
						one.setRow("Feature","Type", "Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								String str2 = rs.getString(2);
								if(str2 == null) str2 = "Unspecified";								
								one = new ReportRow(3, types);
								one.setRow(str,
													 str2,
													 rs.getString(3)
													 );
								rows.add(one);
						}
						rs = pstmt2.executeQuery();
						if(rs.next()){
								count = rs.getInt(1);
						}
						one = new ReportRow(3);
						one.setRow("Total","", count);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}
		/**
		 * project classified by leads
		 */
		public String byLead(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				boolean types[] = {true, true, false};

				which_date="p.date ";
				//
				qq = "select tt.name,tt.name2,sum(tt.amount) from ( ";
				qq += " select u.fullname name, u2.fullname name2, count(*) amount from projects p left join users u on p.lead_id=u.id left join users u2 on u2.id=p.eng_lead_id ";
				qg = " group by name,name2 order by name,name2) tt group by tt.name,tt.name2 ";
				qq2 = " select count(*) from projects p ";
		
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
						qq2 += qw;
				}
				qq += qg;
				logger.debug(qq);
				logger.debug(qq2);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						qq = qq2;
						pstmt2 = con.prepareStatement(qq2);
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								pstmt2.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										pstmt2.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										pstmt2.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						title = "Projects Classified by Leads ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow(3);
						one.setRow("Staff Lead","Eng Lead", "Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								String str2 = rs.getString(2);
								if(str2 == null) str2 = "Unspecified";								
								one = new ReportRow(3, types);
								one.setRow(str,
													 str2,
													 rs.getString(3)
													 );
								rows.add(one);
						}
						rs = pstmt2.executeQuery();
						if(rs.next()){
								count = rs.getInt(1);
						}
						one = new ReportRow(3, types);
						one.setRow("Total","", count);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}		
		/**
		 * project classified by phase rank
		 */
		public String byRank(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				which_date="p.date ";
				//
				qq =" select ph.name name, count(*) amount "+
						" from projects p left join project_updates pu on p.id=pu.project_id left join phase_ranks ph on pu.phase_rank_id=ph.id ";
				qw = " where pu.id in "+
						" (select max(id) from project_updates u2 where u2.project_id=p.id) ";
				qg = " group by name order by ph.id ";
				//		
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
				}
				qq += qg;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						title = "Projects Classified by Phase Rank ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow();
						one.setRow("Phase Rank","Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								one = new ReportRow(2);
								one.setRow(str,
													 rs.getString(2)
													 );
								total += rs.getInt(2);
								rows.add(one);
						}
						one = new ReportRow(2);
						one.setRow("Total", total);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}		
		/**
		 * project classified by status
		 */
		public String byStatus(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="", qq2="", qq3="";
				which_date="p.date ";
				//
				qq = " select p.status status, count(*) amount from projects p ";
				qg = " group by status ";
		
				if(!year.equals("")){
						if(!qw.equals("")){
								qw += " and ";
						}
						else{
								qw  = " where ";
						}
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")){
										qw += " and ";
								}
								else{
										qw = " where ";
								}
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += qw;
				}
				qq += qg;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						title = "Projects Classified by Status ";
						setTitle();
						rows = new ArrayList<ReportRow>();
						ReportRow one = new ReportRow();
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow();
						one.setRow("Status","Count");
						rows.add(one);						
						int total = 0, count = 0;

						total = 0; count = 0;						
						rs = pstmt.executeQuery();
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unspecified";
								one = new ReportRow(2);
								one.setRow(str,
													 rs.getString(2)
													 );
								total += rs.getInt(2);
								rows.add(one);
						}
						one = new ReportRow(2);
						one.setRow("Total",total);
						rows.add(one);
						all.add(rows);
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}

}























































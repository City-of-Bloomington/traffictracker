/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package traffic;
import java.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.log4j.Logger;


public class ReportRow implements java.io.Serializable{

    String row[]={"",""};
		boolean types[]={true, false}; // text or not (number)
		int size = 2;
		static final long serialVersionUID = 90L;		
		static Logger logger = Logger.getLogger(ReportRow.class);
		public ReportRow(){
		}
    public ReportRow(int size){
				setSize(size);
    }
    public ReportRow(int size, boolean vals[]){
				setSize(size);
				setTypes(vals);
    }
		
    //
    public String getRowItem(int jj){
				if(jj < 0 || jj >= size) return null;
				return row[jj];
    }
		public int getSize(){
				return size;
		}
    public String getFirst(){
				return row[0];
    }
    public String getSecond(){
				return row[1];
    }
    public String getThird(){
				if(2 < size)
						return row[2];
				return "";
    }
    public String getForth(){
				if(3 < size)
						return row[3];
				return "";
    }
		public boolean isTypeText(int jj){
				return types[jj];
		}
    //
    // setters
    //
		/**
		 * setting array size, can not be less than 2
		 * @param size int value
		 */
		public void setSize(int size){
				if(size > this.size){
						row = new String[size];
						types = new boolean[size];
						for(int jj=0;jj<size;jj++){
								row[jj] = "0"; // initialize;
								if(jj == 0)
										types[jj] = true; // text
								else
										types[jj] = false; // number
						}
				}
				this.size = size;
		}
		public void setTypes(boolean vals[]){
				types = vals;
		}
    public void setRow(int jj, String val){
				if(val != null && jj < size)
						row[jj] = val;
    }
		/**
		 * since many report will be two columns
		 */
		public void setRow(String val, String val2){
				if(val != null){
						row[0] = val;
				}
				if(val2 != null){
						row[1] = val2;
				}		
		}
		/**
		 * since many report will be two columns
		 */
		public void setRow(String val, int val2){
				if(val != null){
						row[0] = val;
				}
				row[1] = ""+val2;
		}		
	
		/**
		 * we may have some report with three columns
		 */
		public void setRow(String val, String val2, String val3){
				if(val != null){
						row[0] = val;
				}
				if(val2 != null){
						row[1] = val2;
				}
				if(val3 != null && size > 2){
						row[2] = val3;
				}		
		}
		/**
		 * we may have some report with three columns
		 */
		public void setRow(String val, String val2, int val3){
				if(val != null){
						row[0] = val;
				}
				if(val2 != null){
						row[1] = val2;
				}
				if(size > 2){
						row[2] = ""+val3;
				}		
		}		
		//
		public void setRow(String ... vals){
				if(vals != null && vals.length == size){
						row = vals;
				}
		}
		//
    public String toString(){
				String ret = "";
				for(int jj=0;jj<size;jj++){
						if(!ret.equals("")) ret += ", ";
						ret += row[jj];
				}
				return "["+ret+"]";
    }
	
}

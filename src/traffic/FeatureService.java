package traffic;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.log4j.Logger;


public class FeatureService extends HttpServlet{

    String url="";
		static final long serialVersionUID = 180L;	
		static Logger logger = Logger.getLogger(FeatureService.class);
    
    public void doGet(HttpServletRequest req, 
											HttpServletResponse res) 
				throws ServletException, IOException {
				doPost(req,res);
    }
    /**
     * @link #doGetost
     */

    public void doPost(HttpServletRequest req, 
											 HttpServletResponse res) 
				throws ServletException, IOException {
    
				String message="", action="";
				res.setContentType("application/json");
				PrintWriter out = res.getWriter();
				String name, value;
				String fullName="", term= "", id="", type="feature"; // default
				boolean success = true;
				HttpSession session = null;
				if(url.equals("")){
						url    = getServletContext().getInitParameter("url");
						//
				}
				Enumeration<String> values = req.getParameterNames();
				String [] vals = null;
				while (values.hasMoreElements()){
						name = values.nextElement().trim();
						vals = req.getParameterValues(name);
						value = vals[vals.length-1].trim();	
						if (name.equals("id")) { // this is what jquery sends
								id = value;
						}
						else if (name.equals("action")){ 
								action = value;  
						}
						else if (name.equals("type")){ 
								type = value;  
						}						
						else{
								// System.err.println(name+" "+value);
						}
				}
				List<SubType> subs = null;
				if(!id.equals("")){				
						if(type.equals("feature")){
								SubTypeList dl = new SubTypeList("sub_features");
								dl.setFeature_id(id);
								String back = dl.find();
								if(back.equals(""))
										subs = dl.getSubTypes();
						}
						else{ // sub_feature
								SubTypeList dl = new SubTypeList("sub_sub_features");
								dl.setSub_id(id);
								String back = dl.find();
								if(back.equals(""))
										subs = dl.getSubTypes();
						}
				}
				if(subs != null && subs.size() > 0){
						String json = writeJson(subs);
						out.println(json);
				}
				out.flush();
				out.close();
    }
		/**
		 * *************************
		 *
		 * json format as an array
		 [
		 {"value":"Walid Sibo",
		 "id":"sibow",
		 "dept":"ITS"
		 },
		 {"value":"Alan Schertz",		 
		 "id":"schertza",
		 "dept":"ITS"
		 }
	   ]
	   ***************************
		 */
		String writeJson(List<SubType> ones){
				String json="";
				if(ones.size() > 0){
						for(Type one:ones){
								if(!json.equals("")) json += ",";
								json += "{\"id\":\""+one.getId()+"\",\"value\":\""+one.getName()+"\"}";
						}
				}
				json = "["+json+"]";
				// System.err.println("json "+json);
				return json;
		}

}























































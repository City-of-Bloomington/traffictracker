<?xml version="1.0" encoding="UTF-8" ?>
<%@  include file="header.jsp" %>
<s:form action="report" method="post">    
  <h3> TrafficTracker Reports</h3>
  <s:if test="hasActionErrors()">
	<div class="errors">
      <s:actionerror/>
	</div>
  </s:if>
  <s:if test="hasActionMessages()">
	<div class="welcome">
      <s:actionmessage/>
	</div>
  </s:if>
  <p>Select one or more of report types.</p>
  <table border="1" width="100%">
	<tr>
	  <td>
		<table with="100%">
		  <tr>
				<td align="right"><label>Report type:</label></td>
				<td align="left"><s:checkbox name="report.byOwner" value="report.byOwner"  />Projects classified by owner</td>
		  </tr>
		  <tr>
				<td>&nbsp;</td>
				<td align="left"><s:checkbox name="report.byType" value="report.byType"  />Projects classified by type</td>
		  </tr>
		  <tr>
				<td>&nbsp;</td>
				<td align="left"><s:checkbox name="report.byFund" value="report.byFund"  />Projects classified by Funding Resource</td>
		  </tr>
		  <tr>
				<td>&nbsp;</td>
				<td align="left"><s:checkbox name="report.byFeature" value="report.byFeature"  />Projects classified by Features</td>
		  </tr>
		  <tr>
				<td>&nbsp;</td>
				<td align="left"><s:checkbox name="report.byLead" value="report.byLead"  />Projects classified by Leads</td>
		  </tr>
		  <tr>
				<td>&nbsp;</td>
				<td align="left"><s:checkbox name="report.byRank" value="report.byRank"  />Projects classified by Phase Rank</td>
		  </tr>			
		</table>
	  </td>
	</tr>
	<tr>
	  <td align="left">
		<table width="100%">
		  <tr>  
			<td align="right"><label>Day</label></td>
			<td align="left"><s:textfield name="report.day" value="%{report.day}" size="10" cssClass="date" />, </td>
		  </tr>		
		  <tr>  
			<td align="right"><label>Year</label></td>
			<td align="left"><s:select name="report.year" list="years" value="%{report.year}" /> or</td>
		  </tr>
		  <tr>
			<td align="right"><label>Date, from:</label></td>
			<td align="left"><s:textfield name="report.date_from" maxlength="10" size="10" value="%{report.date_from}" cssClass="date" /><label> To </label><s:textfield name="report.date_to" maxlength="10" size="10" value="%{report.date_to}" cssClass="date" /></td>
		  </tr>
		</table>
	  </td>
	</tr>	
	<tr>
	  <td colspan="2" valign="top" align="right">
		<s:submit name="action" type="button" value="Submit" />
	  </td>
	</tr>
  </table>
</s:form>  
<s:if test="action != ''">
  <%@  include file="reportResult.jsp" %>	
</s:if>
<%@  include file="footer.jsp" %>	







































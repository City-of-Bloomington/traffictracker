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
			<dl class="fn1-output-field">
				<dt>Report type: </dt>
				<dd><s:checkbox name="report.byOwner" value="report.byOwner"  />Projects classified by owner</dd>
				<dd><s:checkbox name="report.byType" value="report.byType"  />Projects classified by type</dd>
				<dd><s:checkbox name="report.byFund" value="report.byFund"  />Projects classified by Funding Resource</dd>
				<dd><s:checkbox name="report.byFeature" value="report.byFeature"  />Projects classified by Features</dd>
				<dd><s:checkbox name="report.byLead" value="report.byLead"  />Projects classified by Leads</dd>
				<dd><s:checkbox name="report.byRank" value="report.byRank"  />Projects classified by Phase Rank</dd>						
			</dl>
			<dl class="fn1-output-field">
				<dt>Day: </label></dt>
				<dd><s:textfield name="report.day" value="%{report.day}" size="10" cssClass="date" />, </dd>
			</dl>
			<dl class="fn1-output-field">			
				<dt>Year: </label></dt>
				<dd><s:select name="report.year" list="years" value="%{report.year}" /> or</dd>
			</dl>
			<dl class="fn1-output-field">			
				<dt>Date, from: </dt>
				<dd><s:textfield name="report.date_from" maxlength="10" size="10" value="%{report.date_from}" cssClass="date" /><label> To </label><s:textfield name="report.date_to" maxlength="10" size="10" value="%{report.date_to}" cssClass="date" /></dd>
			</dl>
			<dl class="fn1-output-field">			
				<dt></dt>			
				<dd><s:submit name="action" type="button" value="Submit" cssClass="fn1-btn" /></dd>
			</dl>
</s:form>  
<%@  include file="footer.jsp" %>	







































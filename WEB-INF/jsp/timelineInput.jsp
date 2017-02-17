<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="timeline" id="form_id" method="post">
	<h1>Timeline Report</h1>
  <s:if test="hasActionErrors()">
		<div class="errors">
      <s:actionerror/>
		</div>
  </s:if>
  <s:elseif test="hasActionMessages()">
		<div class="welcome">
      <s:actionmessage/>
		</div>
  </s:elseif>
	
	<div class="tt-row-container">
		<div class="tt-split-container">			
			<dl class="fn1-output-field">
				<dt>Project ID</dt>
				<dd><s:textfield name="projectList.id" size="10" value="%{projectList.id}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Type</dt>
				<dd><s:select name="projectList.type_id" value="%{projectList.type_id}" list="types" listKey="id" headerKey="-1" headerValue="All" listValue="name" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">					
			<dl class="fn1-output-field">
				<dt>Funding Source</dt>
				<dd><s:select name="projectList.funding_source_id" value="%{projectList.funding_source_id}" list="sources" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
			</dl>
			<dl class="fn1-output-field">
			<dt>Status</dt>
			<dd><s:radio name="projectList.status" value="%{projectList.status}" list="#{'-1':'All','Active':'Active','On hold':'On hold','Closed':'Closed'}" /> </dd>
			</dl>
		</div>
	</div>
	<div>
	<dl class="fn1-output-field">
			<dt><label title="after you enter the date range, pick the date type from the next row">Date **</label></dt>
			<dd>From: <s:textfield name="projectList.date_from" value="%{projectList.date_from}" size="10" cssClass="date" /> To:<s:textfield name="projectList.date_to" value="%{projectList.date_to}" size="10" cssClass="date" /></dd>
		</dl>
	</div>
	<dl class="fn1-input-field">
		<dt></dt>
		<dd><s:submit name="action" type="button" value="Submit" cssClass="fn1-btn" /></dd>
	</dl>
</s:form>

<%@  include file="footer.jsp" %>

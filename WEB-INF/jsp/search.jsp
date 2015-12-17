<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="search" id="form_id" method="post">
	<h1>Search Projects</h1>
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
				<dt>Name </dt>
				<dd><s:textfield name="projectList.name" value="%{projectList.name}" size="30" maxlength="70" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Owner</dt>
				<dd><s:select name="projectList.owner_id" value="%{projectList.owner_id}" list="owners" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Type</dt>
				<dd><s:select name="projectList.type_id" value="%{projectList.type_id}" list="types" listKey="id" headerKey="-1" headerValue="All" listValue="name" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>DES No.</dt>
				<dd><s:textfield name="projectList.des_no" value="%{projectList.des_no}" size="20" maxlength="30" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Features</dt>
				<dd><s:select name="projectList.feature_id" value="%{projectList.feature_id}" list="features" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Funding Source</dt>
				<dd><s:select name="projectList.funding_source_id" value="%{projectList.funding_source_id}" list="sources" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Staff Lead</dt>
				<dd><s:select name="projectList.lead_id" value="%{projectList.lead_id}" list="leads" listKey="id" listValue="fullname" headerKey="-1" headerValue="All" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Eng Lead</dt>
				<dd><s:select name="projectList.eng_lead_id" value="%{projectList.eng_lead_id}" list="eng_leads" listKey="id" listValue="fullname" headerKey="-1" headerValue="All" /></dd>
			</dl>			
		</div>
	</div>
	<dl class="fn1-output-field">
		<dt>Length </dt>
		<dd> From:<s:textfield name="projectList.length_from" value="%{projectList.length_from}" size="10" maxlength="10" /> To:<s:textfield name="projectList.length_to" value="%{projectList.length_to}" size="10" maxlength="10" />(feet)</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Date</dt>
		<dd>From: <s:textfield name="projectList.date_from" value="%{projectList.date_from}" size="10" cssClass="date" /> To:<s:textfield name="projectList.date_to" value="%{projectList.date_to}" size="10" cssClass="date" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Pick Date Type</dt>
		<dd><s:radio name="projectList.which_date" value="%{projectList.which_date}" list="#{'r.date':'Created Date','r.est_end_date':'Est End Date','r.actual_end_date':'Actual End Date'}" /> </dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Est. Cost</dt>
		<dd>From:<s:textfield name="projectList.est_cost_from" value="%{projectList.est_cost_from}" size="12" maxlength="12" /> To:<s:textfield name="projectList.est_cost_to" value="%{projectList.est_cost_to}" size="12" maxlength="12" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Actual Cost</dt>
		<dd>From:<s:textfield name="projectList.actual_cost_from" value="%{projectList.actual_cost_from}" size="12" maxlength="12" /> To:<s:textfield name="projectList.actual_cost_to" value="%{projectList.actual_cost_to}" size="12" maxlength="12" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Status</dt>
		<dd><s:radio name="projectList.status" value="%{projectList.status}" list="#{'-1':'All','Active':'Active','On hold':'On hold','Closed':'Closed'}" /> </dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Phase Rank</dt>
		<dd><s:select name="projectList.phase_rank_id" value="%{projectList.phase_rank_id}" list="ranks" listKey="id" listValue="name" headerKey="-1" headerValue="All" /> (latest)</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Phase Rank</dt>
		<dd>From: <s:select name="projectList.phase_rank_from" value="%{projectList.phase_rank_from}" list="ranks" listKey="id" listValue="name" headerKey="-1" headerValue="All" />
			To: <s:select name="projectList.phase_rank_to" value="%{projectList.phase_rank_to}" list="ranks" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
	</dl>
	<dl class="fn1-input-field">
		<dt></dt>
		<dd><s:submit name="action" type="button" value="Submit" cssClass="fn1-btn" /></dd>
	</dl>
</s:form>
<s:if test="projects != null && projects.size() > 0">
	<s:set var="projects" value="projects" />
	<s:set var="projectsTitle" value="projectsTitle" />
	<%@  include file="projects.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>

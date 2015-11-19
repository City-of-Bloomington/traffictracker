<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<h4>View Project Update</h4>
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
			<dt>Project </dt>
			<dd><a href="<s:property value='#application.url' />project.action?id=<s:property value='projectUpdate.project_id' />"> <s:property value="projectUpdate.project" /> </a></dd>
		</dl>
		<dl class="fn1-output-field">		
			<dt>Phase Rank</dt>
			<dd><s:property value="%{projectUpdate.phase_rank}" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Date</dt>
			<dd><s:property value="%{projectUpdate.date}" /></dd>
		</dl>
		<dl class="fn1-output-field">		
			<dt>Notes</dt>
			<dd><s:property value="%{projectUpdate.notes}" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Update By</dt>
			<dd><s:property value="%{projectUpdate.user}" /></dd>
		</dl>
		<s:if test="projectUpdate.project.canHaveMoreUpdates()">
			<dl class="fn1-output-field">
				<dt></dt>
				<dd><a href="<s:property value='#application.url' />projectUpdate.action?project_id=<s:property value='projectUpdate.project_id' />">Add New Project Update </a></dd>
			</dl>
	  </s:if>
	</div>
</div>
<s:if test="updates != null && updates.size() > 0">
	<s:set var="updates" value="updates" />
	<s:set var="showProject" value="false" />		
	<s:set var="updatesTitle" value="updatesTitle" />
	<%@  include file="updates.jsp" %>
</s:if>


<%@  include file="footer.jsp" %>	







































<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="projectUpdate" method="post">
	<s:if test="id == ''">
		<h4>New Project Update</h4>
	</s:if>
	<s:else>
		<h4>View Project Update</h4>
		<s:hidden name="id" value="%{id}" />
	</s:else>
	<s:hidden name="projectUpdate.project_id" value="%{projectUpdate.project_id}" />	
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
  <p>*indicate a required field </p>
	<dl>
		<dt>Project </dt>
		<dd><a href="<s:property value='#application.url' />project.action?id=<s:property value='projectUpdate.project_id' />"> <s:property value="projectUpdate.project" /> </a></dd>
		
		<dt>Phase Rank</dt>
		<dd><s:select name="projectUpdate.phase_rank_id" value="%{projectUpdate.phase_rank_id}" list="ranks" listKey="id" listValue="name" requiredLabel="true" headerKey="-1" headerValue="Pick Rank" /></dd>
		<dt>Date</dt>
		<dd><s:textfield name="projectUpdate.date" value="%{projectUpdate.date}" size="10" maxlength="10" cssClass="date" /></dd>
		
		<dt>Notes</dt>
		<dd><s:textarea name="projectUpdate.notes" value="%{projectUpdate.notes}" rows="5" cols="70" /></dd>
		
		<dt>Update By</dt>
		<dd><s:select name="projectUpdate.user_id" value="%{projectUpdate.user_id}" list="users" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick User" /></dd>
		<s:if test="projectUpdate.id == ''">
			<dt></dt>
			<dd><s:submit name="action" type="button" value="Save" /></dd>
	  </s:if>
	  <s:elseif test="projectUpdate.project.canHaveMoreUpdates()">
			<dt></dt>
			<dd><a href="<s:property value='#application.url' />projectUpdate.action?project_id=<s:property value='projectUpdate.project_id' />">Add New Project Update </a></dd>
	  </s:elseif>
	</dl>
</s:form>	
<s:if test="updates != null && updates.size() > 0">
	<s:set var="updates" value="updates" />
	<s:set var="showProject" value="false" />	
	<s:set var="updatesTitle" value="updatesTitle" />
	<%@  include file="updates.jsp" %>
</s:if>


<%@  include file="footer.jsp" %>	







































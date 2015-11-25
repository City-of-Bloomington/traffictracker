<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="projectUpdate" method="post" onsubmit="return projectValidate()">
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
  <p>* indicate a required field </p>
	<div class="tt-row-container">
		<div class="tt-split-container">
			<dl class="fn1-output-field">		
				<dt>Project </dt>
				<dd><a href="<s:property value='#application.url' />project.action?id=<s:property value='projectUpdate.project_id' />"> <s:property value="projectUpdate.project" /> </a></dd>
			</dl>
		</div>
		<div class="tt-split-container">		
			<dl class="fn1-input-field--select">		
				<dt>Phase Rank</dt>
				<dd><s:select name="projectUpdate.phase_rank_id" value="%{projectUpdate.phase_rank_id}" list="ranks" listKey="id" listValue="name" requiredLabel="true" headerKey="-1" headerValue="Pick Rank" required="true" id="phase_rank_id" />*</dd>
			</dl>
		</div>
		<div class="tt-split-container">		
			<dl class="fn1-input-field">				
				<dt>Date</dt>
				<dd><s:textfield name="projectUpdate.date" value="%{projectUpdate.date}" size="10" maxlength="10" cssClass="date" requiredLabel="true" required="true" />*</dd>
			</dl>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-input-field--select">				
				<dt>Update By</dt>
				<dd><s:select name="projectUpdate.user_id" value="%{projectUpdate.user_id}" list="users" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick User" /></dd>
			</dl>
		</div>
	</div>
	<dl class="fn1-input-field">						
		<dt>Update Notes</dt>
		<dd><s:textarea name="projectUpdate.notes" value="%{projectUpdate.notes}" rows="5" cols="70" /></dd>
	</dl>
	<s:if test="projectUpdate.id == ''">
		<dl class="fn1-input-field">			
			<dt></dt>
			<dd><s:submit name="action" type="button" value="Save" cssClass="fn1-btn"/></dd>
			</dl>
	</s:if>
	<s:elseif test="projectUpdate.project.canHaveMoreUpdates()">
		<dl class="fn1-input-field">
			<dt></dt>
			<dd><a href="<s:property value='#application.url' />projectUpdate.action?project_id=<s:property value='projectUpdate.project_id' />" class="fn1-btn">Add New Project Update </a></dd>
		</dl>
	</s:elseif>

</s:form>	
<s:if test="updates != null && updates.size() > 0">
	<s:set var="updates" value="updates" />
	<s:set var="showProject" value="false" />	
	<s:set var="updatesTitle" value="updatesTitle" />
	<%@  include file="updates.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>	







































<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="project" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Project</h1>
	</s:if>
	<s:else>
		<h1>Edit Project</h1>
		<s:hidden name="id" value="%{id}" />
	</s:else>
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
  	<s:if test="projet.id != ''">
		<dl class="fn1-input-field">
			  <dt>Project ID</dt>
				<dd><s:property value="project.id" /></dd>
		</dl>
	</s:if>
<div class="tt-row-container">
	<div class="tt-split-container">
		<dl class="fn1-input-field">
			<dt>Name </dt>
			<dd><s:textfield name="project.name" value="%{project.name}" size="30" maxlength="70" requiredLabel="true" /> </dd>
		</dl>
		<dl class="fn1-input-field">
			<dt>Description</dt>
			<dd><s:textarea name="project.description" value="%{project.description}" row="5" cols="80" requiredLabel="true" /></dd>
		</dl>
		<dl class="fn1-input-field--select">
			<dt>Project Owner</dt>
			<dd><s:select name="project.owner_id" value="%{project.owner_id}" list="owners" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Owner" requiredLabel="true" /></dd>
		</dl>
		<dl class="fn1-input-field--select">
			<dt>Project Type</dt>
			<dd><s:select name="project.type_id" value="%{project.type_id}" list="types" listKey="id" headerKey="-1" headerValue="Pick Type" listValue="name" requiredLabel="true" /></dd>
		</dl>
		<dl class="fn1-input-field--select">
			<dt>New/Improved Features</dt>
			<dd><s:select name="project.category_id" value="%{project.category_id}" list="categories" listKey="id" listValue="name" onchange="doRefresh()" headerKey="-1" headerValue="Pick Feature" /><s:if test="project.hasSubCategory()"><s:select name="project.sub_category_id" value="%{project.sub_category_id}" list="sub_categories" listKey="id" listValue="name" onchange="doRefresh()" /><s:if test="project.hasSubSubCategory()"><s:select name="project.sub_sub_category_id" value="%{project.sub_sub_category_id}" list="sub_sub_categories" listKey="id" listValue="name" /></s:if></s:if></dd>
		</dl>
		<dl class="fn1-input-field--select">
			<dt>Funding Source</dt>
			<dd><s:select name="project.funding_source_id" value="%{project.funding_source_id}" list="sources" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Funding" /></dd>
		</dl>
		<dl class="fn1-input-field--select">
			<dt>PM Lead</dt>
			<dd><s:select name="project.lead_id" value="%{project.lead_id}" list="leads" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick Lead" /></dd>
		</dl>
		<dl class="fn1-input-field--select">
			<dt>Eng Lead</dt>
			<dd><s:select name="project.eng_lead_id" value="%{project.eng_lead_id}" list="leads" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick Eng Lead" /></dd>
		</dl>
		<dl class="fn1-input-field">
			<dt>Length </dt>
			<dd><s:textfield name="project.length" value="%{project.length}" size="10" maxlength="10" /> </dd>
		</dl>
		<dl class="fn1-input-field">
			<dt>File Folder Path </dt>
			<dd><s:textfield name="project.file_path" value="%{project.file_path}" size="70" maxlength="150" /> </dd>
		</dl>
	</div>
	<div class="tt-split-container">
		<dl class="fn1-input-field">
			<dt>DES No.</dt>
			<dd><s:textfield name="project.des_no" value="%{project.des_no}" size="20" maxlength="30" /> </dd>
		</dl>
		<dl class="fn1-input-field">
			<dt>Request Date</dt>
			<dd><s:textfield name="project.request_date" value="%{project.request_date}" size="10" cssClass="date" /> </dd>
		</dl>
		<dl class="fn1-input-field">
			<dt>Est. End Date</dt>
			<dd><s:textfield name="project.est_end_date" value="%{project.est_end_date}" size="10" maxlength="10" cssClass="date" /> </dd>
		</dl>
		<dl class="fn1-input-field">
			<dt>Actual End Date</dt>
			<dd><s:textfield name="project.actual_end_date" value="%{project.actual_end_date}" size="10" maxlength="10" cssClass="date" /> </dd>
		</dl>
		<dl class="fn1-input-field">
			<dt>Est. Cost</dt>
			<dd>$<s:textfield name="project.est_cost" value="%{project.est_cost}" size="22" maxlength="12" /> </dd>
		</dl>
		<dl class="fn1-input-field">
			<dt>Actual Cost</dt>
			<dd>$<s:textfield name="project.actual_cost" value="%{project.actual_cost}" size="12" maxlength="12" /> </dd>
		</dl>

		<s:if test="project.id == ''">
			<dl class="fn1-input-field">
				<dt></dt>
				<dd><s:submit name="action" type="button" value="Save" /></dd>
			</dl>
		  </s:if>
		  <s:elseif test="project.canHaveMoreUpdates()">
			  <dl class="fn1-input-field">
				<dt><s:submit name="action" type="button" value="Save Changes" /></dt>
				<dd><a href="<s:property value='#application.url' />projectUpdate.action?project_id=<s:property value='project.id' />">Add Project Updates </a></dd>
			</dl>
		  </s:elseif>
		</dl>
	</div>
</div>
</s:form>
<s:if test="id != '' && updates.size() > 0">
	<s:set var="updates" value="updates" />
	<s:set var="showProject" value="false" />
	<s:set var="updatesTitle" value="updatesTitle" />
	<%@  include file="updates.jsp" %>
</s:if>
<s:else>
	<s:set var="projects" value="projects" />
	<s:set var="projectsTitle" value="projectsTitle" />
	<%@  include file="projects.jsp" %>
</s:else>



<%@  include file="footer.jsp" %>

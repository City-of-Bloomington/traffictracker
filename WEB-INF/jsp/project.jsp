<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="project" id="form_id" method="post" onsubmit="return projectValidate()">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Project</h1>
	</s:if>
	<s:else>
		<h1>Edit Project</h1>
		<s:hidden name="id" value="%{id}" />
		<s:hidden name="project.geometry" id="geometery" value="%{project.geometry}" />		
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
  <p>* Required field <br />
			<s:if test="id != ''">
				** Check box to delete feature <br />
				If you make any change, please hit the 'Save Changes' button
			</s:if>
			<s:else>
				You must hit 'Save' button to save data
			</s:else>
	</p>

<div class="tt-row-container">
	<div class="tt-split-container">
  	<s:if test="project.id != ''">
		<dl class="fn1-output-field">
			  <dt>ID </dt>
				<dd><s:property value="project.id" /> 
		</dl>
	</s:if>		
		<dl class="fn1-input-field">
			<dt>Name </dt>
			<dd><s:textfield name="project.name" value="%{project.name}" size="30" maxlength="70" requiredLabel="true" id="project_name" required="true" />* </dd>
		</dl>
		<dl class="fn1-input-field">
			<dt>Description</dt>
			<dd><s:textarea name="project.description" value="%{project.description}" row="5" cols="80" /></dd>
		</dl>
		<dl class="fn1-input-field--select">
			<dt>Owner</dt>
			<dd><s:select name="project.owner_id" value="%{project.owner_id}" list="owners" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Owner" /></dd>
		</dl>
		<dl class="fn1-input-field--select">
			<dt>Type</dt>
			<dd><s:select name="project.type_id" value="%{project.type_id}" list="types" listKey="id" headerKey="-1" headerValue="Pick Type" listValue="name" requiredLabel="true" id="project_type_id" required="true" />*</dd>
		</dl>
		<dl class="fn1-input-field--select">
			<dt>Funding Source</dt>
			<dd><s:select name="project.funding_source_id" value="%{project.funding_source_id}" list="sources" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Funding" /></dd>
		</dl>
		<s:if test="project.id != ''">
	</div>
	<div class="tt-split-container">
		</s:if>
		<dl class="fn1-output-field">
			<dt>Proj. Manager</dt>
			<dd><s:select name="project.lead_id" value="%{project.lead_id}" list="leads" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick Lead" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Eng. Lead</dt>
			<dd><s:select name="project.eng_lead_id" value="%{project.eng_lead_id}" list="eng_leads" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick Eng Lead" /></dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Length </dt>
			<dd><s:textfield name="project.length" value="%{project.length}" size="10" maxlength="10" /> (feet)</dd>
		</dl>
		<s:if test="project.id == ''">
	</div>
	<div class="tt-split-container">
		</s:if>
		<dl class="fn1-output-field">
			<dt>DES No.</dt>
			<dd><s:textfield name="project.des_no" value="%{project.des_no}" size="12" maxlength="12" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Created Date</dt>
			<dd><s:textfield name="project.date" value="%{project.date}" size="10" cssClass="date" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Est. End Date</dt>
			<dd><s:textfield name="project.est_end_date" value="%{project.est_end_date}" size="10" maxlength="10" cssClass="date" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Actual End Date</dt>
			<dd><s:textfield name="project.actual_end_date" value="%{project.actual_end_date}" size="10" maxlength="10" cssClass="date" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Est. Cost</dt>
			<dd>$<s:textfield name="project.est_cost" value="%{project.est_cost}" size="12" maxlength="12" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Actual Cost</dt>
			<dd>$<s:textfield name="project.actual_cost" value="%{project.actual_cost}" size="12" maxlength="12" /> </dd>
		</dl>
		<s:if test="project.id == ''">
			<dl class="fn1-input-field--select">		
				<dt>Phase Rank</dt>
				<dd><s:select name="projectUpdate.phase_rank_id" value="%{projectUpdate.phase_rank_id}" list="ranks" listKey="id" listValue="name" requiredLabel="true" headerKey="-1" headerValue="Pick Rank" id="phase_rank_id" required="true" />*</dd>
			</dl>
			<dl class="fn1-input-field">						
				<dt>Phase Rank Notes</dt>
				<dd><s:textarea name="projectUpdate.notes" value="%{projectUpdate.notes}" rows="5" cols="70" /></dd>
			</dl>
		</s:if>			
	</div>
</div>
<s:if test="project.id != ''">
	<dl class="fn1-output-field">
		<dt></dt>
				<dd>Current Feature(s)</dd>
			</dl>
			<s:iterator var="one" value="%{project.features}">
				<dl class="fn1-input-field--select">							
					<dt><input type="checkbox" name="project.del_feature" value="<s:property value='id' />" />**</dt><dd><s:property />
					</dd>
				</dl>
			</s:iterator>
		</s:if>
		<dl class="fn1-output-field">
			<dt>Feature</dt>
			<dd><s:select name="feature.feature_id" value="%{feature.feature_id}" list="features" listKey="id" listValue="name" onchange="getSubFeatures()" headerKey="-1" headerValue="Pick Feature" id="feature_id" /><select name="feature.sub_id" value="%{feature.sub_id}" onchange="getSubSubFeatures()" id="sub_feature_id" style="display:none"><option value="-1">Pick Sub Feature</option></select><select name="feature.sub_sub_id" value="%{feature.sub_sub_id}" id="sub_sub_feature_id" style="display:none"><option value="-1">Pick Sub Sub Feature</option></select>
				Type: <s:radio name="feature.type" list="{'New','Improved'}" value="%{feature.type}" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Status</dt>
			<dd><s:radio name="project.status" value="%{project.status}" list="#{'Active':'Active','On hold':'On hold','Closed':'Closed','Pending Delete':'Pending Delete'}" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>File Folder Path </dt>
			<dd><s:textfield name="project.file_path" value="%{project.file_path}" size="70" maxlength="150" /> </dd>
		</dl>
		<s:if test="project.id == ''">
			<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
			<a href="<s:property value='#application.url'/>projectUpdate.action?project_id=<s:property value='project.id' />" class="fn1-btn">Add Project Updates </a>
			<s:if test="#session.user != null && #session.user.canDelete() && project.canDelete()">
			<s:submit type="button" value="Delete" class="fn1-btn" onclick="confirmDelete()"/>
			</s:if>
			<a href="<s:property value='#application.url'/>map.action?id=<s:property value='project.id' />" class="fn1-btn"> Add/Edit Map Features</a>
		</s:else>
</s:form>
<s:if test="id != '' && updates.size() > 0">
	<s:set var="updates" value="updates" />
	<s:set var="showProject" value="false" />
	<s:set var="updatesTitle" value="updatesTitle" />
	<%@ include file="updates.jsp" %>
</s:if>
<s:else>
	<s:set var="projects" value="projects" />
	<s:set var="projectsTitle" value="projectsTitle" />
	<%@  include file="projects.jsp" %>
</s:else>
<%@  include file="footer.jsp" %>

<script type="text/javascript">
 $(function() {
  $('#form_id').areYouSure();
});
/**
 * populate sub features select list
 * using jquery
 */
function getSubFeatures(){

	var selected_id = $("#feature_id").val();
	//
	// we are interested in the features that have sub_features only
	//
	if(selected_id && (selected_id == "1" ||
											selected_id == "5" ||
											selected_id == "6")){
					$.getJSON("<s:property value='#application.url' />FeatureService?id="+selected_id+"&type=feature",function(data){
					//
					// reomve the old ones if the user selected another item
					//
					var len = $('#sub_feature_id option').length;
					if(len > 1){
						for(var jj=len-1;jj>0;jj--){
							$('#sub_feature_id option').eq(jj).remove();
						}
					}
					$.each( data, function(key, item) {
						$('#sub_feature_id').append($('<option>', { 
							value: item.id,
							text : item.value 
						}));
					});
					$('#sub_feature_id').css({'display':'inline'});
				}); 
	}
	else{
		var len = $('#sub_feature_id option').length;
		if(len > 1){
			for(var jj=len-1;jj>0;jj--){
				$('#sub_feature_id option').eq(jj).remove();
			}
		}		
		$('#sub_feature_id').css({'display':'none'});			
		len = $('#sub_sub_feature_id option').length;		
		if(len > 1){
			for(var jj=len-1;jj>0;jj--){
				$('#sub_sub_feature_id option').eq(jj).remove();				
			}
		}
		$('#sub_sub_feature_id').css({'display':'none'});			
	}
}
/**
 * poplute sub sub feature select list
 * using jquery and DOM
 */
function getSubSubFeatures(){
	//
	var select_id = document.getElementById("sub_sub_feature_id");
	var selected_id = $("#sub_feature_id").val();
	//
	// we are interested in the sub features that have sub sub features only
	//
	if(selected_id && (selected_id == "4" ||
										selected_id == "5")){
			$.getJSON("<s:property value='#application.url' />FeatureService?id="+selected_id+"&type=sub_feature",function(data){
				//
				// reomve the old ones if the user selected  another item
				//
				var len = select_id.length;
				if(len > 1){
					for(var jj=len-1;jj>0;jj--){
						select_id.remove(jj);
					}
				}
				$.each( data, function(key, item) {
					$('#sub_sub_feature_id').append($('<option>', { 
						value: item.id,
						text : item.value 
					}));
				});					
				select_id.style.display="inline";
			}); 
	}
	else{
		var len = select_id.length;
		if(len > 1){
			for(var jj=len-1;jj>0;jj--){
				select_id.remove(jj);
			}
		}
		select_id.style.display="none";				
	}
}
function confirmDelete(){
	var x = confirm("Are you sure you want to delete this record");
	if(x){
		document.getElementById("action2").value="Delete";						
		document.getElementById("form_id").submit();
		return true;
	}
	return false;
}		
</script>

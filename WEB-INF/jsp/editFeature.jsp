<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="feature" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:hidden name="feature.id" id="id" value="%{feature.id}" />
	<s:hidden name="feature.project_id" id="project_id" value="%{feature.project_id}" />	
	<h1>Edit Project Feature</h1>

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
  <p>
		If you make any change, please hit the 'Save Changes' button
	</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Project ID </dt>
			<dd><a href="<s:property value='#application.url'/>project.action?id=<s:property value='feature.project_id' />"> <s:property value="%{feature.project_id}" /></a>	</dd>	
		</dl>
		<dl class="fn1-output-field">
			<dt>Feature</dt>
			<dd><s:select name="feature.feature_id" value="%{feature.feature_id}" list="features" listKey="id" listValue="name" onchange="getSubFeatures()" headerKey="-1" headerValue="Pick Feature" id="feature_id" />
				<s:select name="feature.sub_id" value="%{feature.sub_id}" onchange="getSubSubFeatures()" id="sub_feature_id" list="feature.sub_features" listKey="id" listValue="name" cssStyle="display:inline" />
				<s:select name="feature.sub_sub_id" value="%{feature.sub_sub_id}" id="sub_sub_feature_id" list="feature.sub_sub_features" listKey="id" listValue="name" cssStyle="display:inline" />
			</dd>
		</dl>
		<dl class="fn1-output-field">	
			<dt>Type</dt>
			<dd> <s:radio name="feature.type" list="{'New','Improved'}" value="%{feature.type}" /></dd>
		</dl>
		<dl class="fn1-output-field">	
			<dt><s:property value="%{feature.lengthOrCount}" /></dt>
			<dd><s:textfield name="feature.length" value="%{feature.length}" size="6" maxlength="6" /><s:property value="%{feature.lengthOrCountUnit}" /></dd>
		</dl>
		<dl>
			<dt></dt>
			<dd>
				<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
				<s:submit name="action" type="button" value="Delete" class="fn1-btn" onclick="return confirmDelete()" />				
			</dd>
		</dl>
	</div>
</s:form>
<s:if test="feature.hasOtherFeatures()">
	<s:set var="projectFeatures" value="feature.features" />
	<s:set var="canEdit" value="'true'" />
	<s:set var="featuresTitle" value="featuresTitle" />
	<%@ include file="features.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>

<script type="text/javascript">
 $(function() {
  $('#form_id').areYouSure();
});
$(document).ready(function(){
  if($("#sub_feature_id").val() == -1){ 
    $("#sub_feature_id").hide();
  }
  if($("#sub_sub_feature_id").val() == -1){ 
    $("#sub_sub_feature_id").hide();
  }
});

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
</script>

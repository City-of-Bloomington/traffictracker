<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#projectsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Proj. Manager</b></th>
			<th align="center"><b>Eng. Lead</b></th>			
			<th align="center"><b>Features</b></th>
			<th align="center"><b>Actual End Date</b></th>
			<th align="center"><b>Most Recent Phase Rank</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#projects">
			<tr>
				<td><a href="<s:property value='#application.url' />project.action?id=<s:property value='id' />"><s:property value="id" /> Details</a></td>
				<td><s:property value="name" /></td>
				<td><s:property value="lead" /></td>
				<td><s:property value="eng_lead" /></td>
				<td>
					<s:if test="hasFeatures()">
						<s:property value="allFeaturesText" />
					</s:if>
				</td>
				<td><s:property value="actual_end_date" /></td>				
				<s:if test="hasProjectUpdates()">
					<td><s:property value="lastProjectUpdate.phase_rank" /></td>
				</s:if>
				<s:else>
					<td>&nbsp;</td>
				</s:else>
			</tr>
		</s:iterator>
	</tbody>
</table>

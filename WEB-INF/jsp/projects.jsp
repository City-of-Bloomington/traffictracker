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
			<th align="center"><b>Date</b></th>
			<th align="center"><b>Type</b></th>
			<th align="center"><b>Owner</b></th>
			<th align="center"><b>Category</b></th>
			<th align="center"><b>Funding</b></th>
			<th align="center"><b>Lead</b></th>
			<th align="center"><b>Est End Date</b></th>
			<th align="center"><b>Est Cost</b></th>
			<th align="center"><b>Latest Phase Rank</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#projects">
			<tr>
				<td><a href="<s:property value='#application.url' />project.action?id=<s:property value='id' />">Details <s:property value="id" /></a></td>
				<td><s:property value="name" /></td>
				<td><s:property value="request_date" /></td>
				<td><s:property value="type" /></td>
				<td><s:property value="owner" /></td>
				<td><s:property value="category" /></td>
				<td><s:property value="funding_source" /></td>
				<td><s:property value="lead" /></td>
				<td><s:property value="est_end_date" /></td>
				<td align="right">$<s:property value="est_cost" /></td>
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

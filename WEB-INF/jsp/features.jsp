<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#featuresTitle" /></caption>
	<thead>
		<tr>
			<s:if test="#canEdit == 'true'">
				<th align="center"><b>Action</b></th>
			</s:if>
			<th align="center"><b>Feature</b></th>
			<th align="center"><b>Sub Feature(s)</b></th>
			<th align="center"><b>Type</b></th>			
			<th align="center"><b>Length/Count</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#projectFeatures">
			<tr>
				<s:if test="#canEdit == 'true'">
					<td><a href="<s:property value='#application.url' />feature.action?id=<s:property value='id' />"> Edit Feature</a></td>
				</s:if>
				<td><s:property value="name" /></td>
				<td>&nbsp;
					<s:if test="sub_id != ''">
						<s:property value="sub_name" />
					</s:if>
					<s:if test="sub_sub_id != ''">
						/<s:property value="sub_sub_name" />
					</s:if>					
				</td>
				<td><s:property value="type" /></td>
				<td>&nbsp;
					<s:if test="length != ''">
						<s:property value="length" />
						<s:property value="lengthOrCountUnit" />
					</s:if>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>

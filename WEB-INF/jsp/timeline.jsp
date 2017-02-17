<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
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
Note:hoover your mouse over the timeline bars to get more info.<br />

<h3 style="text-align:center"> Projects Timeline (<s:property value="min_date" /> - <s:property value="max_date" />) </h3>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
      google.charts.load('current', {'packages':['timeline']});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
        var container = document.getElementById('timeline');
        var chart = new google.visualization.Timeline(container);
        var dataTable = new google.visualization.DataTable();

        dataTable.addColumn({ type: 'string', id: 'Project' });
        dataTable.addColumn({ type: 'string', id: 'Phase' });	
        dataTable.addColumn({ type: 'date', id: 'Start' });
        dataTable.addColumn({ type: 'date', id: 'End' });
        dataTable.addRows([
					<s:iterator var="one" value="projects" status="projStatus">
						<s:iterator var="one2" value="updates" status="status">
							[ 'Project <s:property value="project_id" />','<s:property value="phase_rank" /> (<s:property value="update_length" /> days)', new Date('<s:property value="date" />'),new Date('<s:property value="end_date" />') ]
							<s:if test="!#status.last">,</s:if>
						</s:iterator>
						<s:if test="!#projStatus.last">,</s:if>
					</s:iterator>
				]);
					var options = {
					timeline: { groupByRowLabel: false }
					};
					chart.draw(dataTable, options);
      }
    </script>
  </head>
  <body>
		<div id="timeline" style="height: <s:property value='pxsHeight' />px;"></div>
		<table width="90%" border="1">
			<caption>Projects Current Phases</caption>
			<tr>
				<td>Project ID</td>
				<td>Project</td>
				<td>Last Phase</td>				
				<td>Phase Date</td>
				<td>Phase Notes</td>
			</tr>
			<s:iterator var="one" value="projects">
				<tr>
					<td><s:property value="id" /></td>
					<td><s:property value="name"/></td>
					<td><s:property value="lastProjectUpdate.phase_rank"/></td>					
					<td><s:property value="lastProjectUpdate.date" /></td>
					<td><s:property value="lastProjectUpdate.notes" /></td>
				</tr>
			</s:iterator>
		</table>
  </body>
</html>




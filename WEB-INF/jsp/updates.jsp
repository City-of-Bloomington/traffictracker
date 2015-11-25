<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h2><s:property value="#updatesTitle" /></h2>
<s:iterator var="one" value="#updates">
	<article class="tt-update">
		<s:if test="#showProject == true">
			<dl class="fn1-output-field">
				<dt></dt>
				<dd>
					<a href="<s:property value='#application.url' />project.action?id=<s:property value='project_id' />">Project: <s:property value="project.name" /></a>
				</dd>
			</dl>
		</s:if>
		<h1 class="tt-update-user"><s:property value="user" /><time class="tt-update-date"><s:property value="date" /></time></h1>
		<dl class="fn1-output-field">
			<dt>Phase Rank</dt>
			<dd><s:property value="phase_rank" /></dd>
		</dl>
		<s:if test="hasNotes()">
			<s:property value="notes" />
		</s:if>
	</article>
</s:iterator>

<?xml version="1.0" encoding="UTF-8" ?>
<%@  include file="header.jsp" %>
<s:iterator value="report.all" status="allStatus">
  <table border="1" width="60%">
	<s:iterator status="rowStatus">
	  <s:if test="#rowStatus.index == 0">
	  <caption><s:property value="second" /></caption>
	  </s:if>
	  <s:elseif test="#rowStatus.index == 1">
		<tr>
		  <td align="center"><label><s:property value="first" /></label></td>
		  <td align="center"><label><s:property value="second" /></label></td>
		  <s:if test="size == 3">
			<td align="center"><label><s:property value="third" /></label></td>
		  </s:if>
		</tr>
	  </s:elseif>
	  <s:else>
		<tr>
		  <td><s:property value="first" /></td>
		  <s:if test="size == 2">		  
			<td align="right"><s:property value="second" /></td>
		  </s:if>
		  <s:elseif test="isTypeText(1)">
			<td align="left"><s:property value="second" /></td>
		  </s:elseif>
			<s:else>
				<td align="right"><s:property value="second" /></td>
			</s:else>
		  <s:if test="size == 3">
			<td align="right"><s:property value="third" /></td>
		  </s:if>		
		</tr>
	  </s:else>
	</s:iterator>
  </table>
</s:iterator>
<%@  include file="footer.jsp" %>
























































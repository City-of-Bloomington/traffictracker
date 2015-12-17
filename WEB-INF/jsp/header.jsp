<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@ taglib uri="/struts-tags" prefix="s" %>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <s:head />
  <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
  <link rel="SHORTCUT ICON" href="https://apps.bloomington.in.gov/favicon.ico" />
  <link rel="stylesheet" href="<s:property value='#application.url' />js/jquery-ui2.css" type="text/css" media="all" />
  <link rel="stylesheet" href="<s:property value='#application.url' />js/jquery.ui.theme.css" type="text/css" media="all" />
  <link rel="stylesheet" href="<s:property value='#application.url' />css/open-sans/open-sans.css" type="text/css" />
  <link rel="stylesheet" href="//bloomington.in.gov/static/fn1-releases/dev/css/default.css" type="text/css" />
  <link rel="stylesheet" href="//bloomington.in.gov/static/fn1-releases/dev/css/kirkwood.css" type="text/css" />
  <link rel="stylesheet" href="<s:property value='#application.url' />css/screen.css" type="text/css" />
  <link rel="stylesheet" href="<s:property value='#application.url' />css/ol.css" type="text/css" />
  <link rel="stylesheet" href="<s:property value='#application.url' />css/ol3-popup.css" type="text/css" />	
  <title>Traffic Tracker</title>
  <script type="text/javascript">
    var APPLICATION_URL = '<s:property value='#application.url' />';
  </script>
</head>
<body class="fn1-body">
  <header class="fn1-siteHeader">
    <div class="fn1-siteHeader-container">
      <div class="fn1-site-title">
        <h1 id="application_name"><a href="<s:property value='#application.url'/>">TrafficTracker</a></h1>
        <div class="fn1-site-location" id="location_name"><a href="<s:property value='#application.url'/>">City of Bloomington, IN</a></div>
      </div>
      <s:if test="#session != null && #session.user != null">
        <div class="fn1-site-utilityBar">
          <nav id="user_menu">
            <div class="menuLauncher"><s:property value='#session.user.fullname' /></div>
            <div class="menuLinks closed">
              <a href="<s:property value='#application.url'/>logout.action">Logout</a>
            </div>
          </nav>
          <nav id="admin_menu">
            <div class="menuLauncher">Admin</div>
            <div class="menuLinks closed">
              <s:if test="#session.user.isAdmin()">
								<a href="<s:property value='#application.url'/>report.action">Reports</a>
              </s:if>
            </div>
          </nav>
        </div>
	  </s:if>
	</div>

	<div class="fn1-nav1">
      <nav class="fn1-nav1-container">
				<s:if test="#session != null && #session.user != null && #session.user.canEdit()">				
					<a href="<s:property value='#application.url'/>project.action">New Project</a>
					<a href="<s:property value='#application.url'/>projectUpdate.action">Project Updates</a>
				</s:if>
        <a href="<s:property value='#application.url'/>search.action">Search</a>
				<a href="<s:property value='#application.url'/>report.action">Reports</a>				
      </nav>
    </div>
  </header>
  <main>
    <div class="fn1-main-container">

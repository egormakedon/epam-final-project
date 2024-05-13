<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<html lang="en">
	<head>
		<fmt:setLocale value="${sessionScope.lang}"/>
		<fmt:setBundle basename="property.text" var="locale" scope="session"/>

		<fmt:message bundle="${locale}" key="text.welcome.title" var="title"/>
		<fmt:message bundle="${locale}" key="text.main.title" var="main_title"/>
		<fmt:message bundle="${locale}" key="text.main.profile" var="profile"/>
		<fmt:message bundle="${locale}" key="text.main.registration" var="registration"/>
		<fmt:message bundle="${locale}" key="text.main.local.en" var="en_button"/>
		<fmt:message bundle="${locale}" key="text.main.local.ru" var="ru_button"/>
		<fmt:message bundle="${locale}" key="text.main.created.by" var="created_by"/>
		<fmt:message bundle="${locale}" key="text.main.all.rights" var="all_rights"/>

		<title>${title}</title>

		<link rel="shortcut icon" href="../images/pageLogo.png" type="image/png">
		<link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
		<link href="../css/default.css" rel="stylesheet" type="text/css" media="all" />
		<link href="../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
	</head>
	<body>
		<div id="header-wrapper">
			<div id="header" class="container">
				<div id="logo">
					<h1><a href="../jsp/welcome.jsp">${main_title}</a></h1>
				</div>
				<div id="menu">
					<ul>
						<li>
							<a>
								<form action="/Controller" method="get">
								<input type="hidden" name="command" value="profile">
								<input type="submit" style="background:none!important;
    														 color:inherit;
     														 border:none;
    														 padding:0!important;
  														   	 font: inherit;
    														 cursor: pointer;"
									   accesskey="1" value="${profile}">
								</form>
							</a>
						</li>
						<li><a href="../jsp/registration.jsp" accesskey="2" title="">${registration}</a></li>
						<li>
							<a>
								<form action="/Controller" method="get">
									<input type="hidden" name="command" value="changelang">
									<input type="hidden" name="lang" value="en">
									<input type="submit" style="background:none!important;
    														 color:inherit;
     														 border:none;
    														 padding:0!important;
  														   	 font: inherit;
    														 cursor: pointer;"
										   accesskey="3" value="${en_button}">
								</form>
							</a>
						</li>
						<li>
							<a>
								<form action="/Controller" method="get">
									<input type="hidden" name="command" value="changelang">
									<input type="hidden" name="lang" value="ru">
									<input type="submit" style="background:none!important;
    														 color:inherit;
     														 border:none;
    														 padding:0!important;
  														   	 font: inherit;
    														 cursor: pointer;"
										   accesskey="4" value="${ru_button}">
								</form>
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<img src="../images/banner.png" style="width: 100%; height: 100%">
		<div id="copyright" class="container">
			<p>${created_by} <a href="http://epam.by" style="color: white">epam.by</a> ${all_rights}</p>
		</div>
	</body>
</html>

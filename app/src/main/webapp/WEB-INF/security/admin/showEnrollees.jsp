<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<html lang="en">
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="property.text" var="locale" scope="session"/>

    <fmt:message bundle="${locale}" key="text.showenrollees.title" var="title"/>
    <fmt:message bundle="${locale}" key="text.main.title" var="main_title"/>
    <fmt:message bundle="${locale}" key="text.main.local.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="text.main.local.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="text.main.created.by" var="created_by"/>
    <fmt:message bundle="${locale}" key="text.main.all.rights" var="all_rights"/>
    <fmt:message bundle="${locale}" key="text.main.profile" var="profile"/>

    <fmt:message bundle="${locale}" key="text.username.label" var="username"/>
    <fmt:message bundle="${locale}" key="text.speciality.label" var="speciality"/>
    <fmt:message bundle="${locale}" key="text.countrydomen.label" var="country_domen"/>
    <fmt:message bundle="${locale}" key="text.name.label" var="name"/>
    <fmt:message bundle="${locale}" key="text.surname.label" var="surname"/>
    <fmt:message bundle="${locale}" key="text.secondname.label" var="secondname"/>
    <fmt:message bundle="${locale}" key="text.passport.label" var="passport"/>
    <fmt:message bundle="${locale}" key="text.phone.label" var="phone"/>
    <fmt:message bundle="${locale}" key="text.score.label" var="score"/>

    <title>${title}</title>

    <link rel="shortcut icon" href="../../../images/pageLogo.png" type="image/png">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
    <link href="../../../css/default.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
    <link rel="stylesheet" href="../../../css/login.css">

    <style type="text/css">
        table {
            border: 1px solid #6fac1b;
            color: #6fac1b;
            alignment: center;
        }
        th,td {
            padding: 25px 4px 2px 4px !important;
            text-align: center;
        }
    </style>
</head>
<body>
<div id="header-wrapper">
    <div id="header" class="container">
        <div id="logo">
            <h1><a href="../../../jsp/welcome.jsp">${main_title}</a></h1>
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
                                   accesskey="2" value="${en_button}">
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
                                   accesskey="3" value="${ru_button}">
                        </form>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="tittle">
    <p>${title}</p>
</div>
<div id="header-featured" style="background-image: inherit; color: #6fac1b">
    <div id="banner-wrapper">
        <div id="banner" class="container">
            <display:table name="${requestScope.enrolleeList}" id="enrolleeList" pagesize="50"
            requestURI="Controller">
                <display:column property="username" title="${username}" />
                <display:column property="passportId" title="${passport}:" />
                <display:column property="countryDomen" title="${country_domen}:" />
                <display:column property="surname" title="${surname}:" />
                <display:column property="name" title="${name}:" />
                <display:column property="secondName" title="${secondname}:" />
                <display:column property="phone" title="${phone}:" />
                <display:column property="speciality" title="${speciality}:" />
                <display:column property="score" title="${score}:" />
            </display:table>
        </div>
    </div>
</div>
<div id="copyright" class="container">
    <p>${created_by} <a href="http://epam.by" style="color: white">epam.by</a> ${all_rights}</p>
</div>
</body>
</html>

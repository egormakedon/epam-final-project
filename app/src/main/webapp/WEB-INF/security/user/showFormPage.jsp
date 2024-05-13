<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<html lang="en">
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="property.text" var="locale" scope="session"/>

    <fmt:message bundle="${locale}" key="text.showform.title" var="title"/>
    <fmt:message bundle="${locale}" key="text.main.title" var="main_title"/>
    <fmt:message bundle="${locale}" key="text.main.local.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="text.main.local.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="text.main.created.by" var="created_by"/>
    <fmt:message bundle="${locale}" key="text.main.all.rights" var="all_rights"/>
    <fmt:message bundle="${locale}" key="text.main.profile" var="profile"/>

    <fmt:message bundle="${locale}" key="text.university.label" var="university"/>
    <fmt:message bundle="${locale}" key="text.speciality.label" var="speciality"/>
    <fmt:message bundle="${locale}" key="text.faculty.label" var="faculty"/>
    <fmt:message bundle="${locale}" key="text.countrydomen.label" var="country_domen"/>
    <fmt:message bundle="${locale}" key="text.name.label" var="name"/>
    <fmt:message bundle="${locale}" key="text.surname.label" var="surname"/>
    <fmt:message bundle="${locale}" key="text.secondname.label" var="secondname"/>
    <fmt:message bundle="${locale}" key="text.passport.label" var="passport"/>
    <fmt:message bundle="${locale}" key="text.phone.label" var="phone"/>
    <fmt:message bundle="${locale}" key="text.date.label" var="date"/>
    <fmt:message bundle="${locale}" key="text.score.label" var="score"/>

    <title>${title}</title>

    <link rel="shortcut icon" href="../../../images/pageLogo.png" type="image/png">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
    <link href="../../../css/default.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../../css/fonts.css" rel="stylesheet" type="text/css" media="all" />

    <style type="text/css">
        h1 {
            z-index: 100;
            color: #86bd3b;
            text-align: center;
            text-shadow: 0 0 5px rgba(0,0,0,255);
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
    <h1> ${university}: ${requestScope.UNIVERSITY}</h1>
    <h1> ${faculty}: ${requestScope.FACULTY}</h1>
    <h1> ${speciality}: ${requestScope.SPECIALITY}</h1>
    <br>
    <h1> ${name}: ${requestScope.NAME}</h1>
    <h1> ${secondname}: ${requestScope.SECONDNAME}</h1>
    <h1> ${surname}: ${requestScope.SURNAME}</h1>
    <br>
    <h1> ${passport}: ${requestScope.PASSPORTID}</h1>
    <h1> ${country_domen}: ${requestScope.COUNTRYDOMEN}</h1>
    <h1> ${phone}: ${requestScope.PHONE}</h1>
    <br>
    <h1> ${date}: ${requestScope.DATE}</h1>
    <h1> ${score}: ${requestScope.SCORE}</h1>
<div id="copyright" class="container">
    <p>${created_by} <a href="http://epam.by" style="color: white">epam.by</a> ${all_rights}</p>
</div>
</body>
</html>

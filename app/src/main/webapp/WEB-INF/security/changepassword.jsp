<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<html lang="en">
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="property.text" var="locale" scope="session"/>

    <fmt:message bundle="${locale}" key="text.changepassword.title" var="title"/>
    <fmt:message bundle="${locale}" key="text.main.title" var="main_title"/>
    <fmt:message bundle="${locale}" key="text.main.local.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="text.main.local.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="text.password.label" var="password"/>
    <fmt:message bundle="${locale}" key="text.password.label.error" var="password_error"/>
    <fmt:message bundle="${locale}" key="text.password.label.again" var="password_again"/>
    <fmt:message bundle="${locale}" key="text.change.submit" var="change"/>
    <fmt:message bundle="${locale}" key="text.main.created.by" var="created_by"/>
    <fmt:message bundle="${locale}" key="text.main.all.rights" var="all_rights"/>

    <title>${title}</title>

    <link rel="shortcut icon" href="../../images/pageLogo.png" type="image/png">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
    <link href="../../css/default.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
    <link rel="stylesheet" href="../../css/login.css">
    <script src="../../js/js.js"></script>
</head>
<body>
<div id="header-wrapper">
    <div id="header" class="container">
        <div id="logo">
            <h1><a href="../../jsp/welcome.jsp">${main_title}</a></h1>
        </div>
        <div id="menu">
            <ul>
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
                                   accesskey="1" value="${en_button}">
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
                                   accesskey="2" value="${ru_button}">
                        </form>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div id="banner-wrapper">
    <div id="content">
        <div class="tittle">
            <p>${title}</p>
        </div>
        <form class="login" action="/Controller" method="post" onsubmit="return checkPassword()">
            <input type="hidden" name="command" value="changepassword">
            <input type="hidden" name="username" value=${param.username}>
            <p>
                <label for="password1">${password}</label>
                <input type="password" name="password1" id="password1" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!]{6,}" minlength="6" required>
                <span class="form_error">${password_error}</span>
            </p>
            <p>
                <label for="password2">${password_again}</label>
                <input type="password" name="password2" id="password2" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!]{6,}" minlength="6" required>
                <span class="form_error">${password_error}</span>
            </p>
            <p class="login-submit">
                <button type="submit" class="login-button">${change}</button>
            </p>
        </form>
    </div>
</div>
<div id="copyright" class="container">
    <p>${created_by} <a href="http://epam.by" style="color: white">epam.by</a> ${all_rights}</p>
</div>
</body>
</html>

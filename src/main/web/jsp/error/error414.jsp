<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<html lang="en">
    <head>
        <title>Error 414</title>

        <link rel="shortcut icon" href="../../images/pageLogo.png" type="image/png">
        <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
        <link href="../../css/default.css" rel="stylesheet" type="text/css" media="all" />
        <link href="../../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
        <link rel="stylesheet" href="../../css/login.css">

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
                <h1><a href="../../jsp/welcome.jsp">Selection Committee</a></h1>
            </div>
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
                                   accesskey="1" value="ENG">
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
                                   accesskey="2" value="RU">
                        </form>
                    </a>
                </li>
            </ul>
        </div>
    </div>
        <h1>Request from: ${pageContext.errorData.requestURI}</h1>
        <br/>
        <h1>Servlet name: ${pageContext.errorData.servletName}</h1>
        <br/>
        <h1>Status code: ${pageContext.errorData.statusCode}</h1>
        <br/>
        <h1>Exception: ${pageContext.exception.message}</h1>
    <div id="copyright" class="container">
        <p>&copy; 2018. CREATED BY EGOR MAKEDON FOR EPAM SYSTEMS. <a href="http://epam.by" style="color: white">epam.by</a> All rights reserved.</p>
    </div>
    </body>
</html>
<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    </div>
        <h1>Request from ${pageContext.errorData.requestURI} is failed</h1>
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
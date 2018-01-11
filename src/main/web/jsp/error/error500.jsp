<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title>Error 500</title>

        <link rel="shortcut icon" href="../../images/pageLogo.png" type="image/png">
        <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
        <link href="../../css/default.css" rel="stylesheet" type="text/css" media="all" />
        <link href="../../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
        <link rel="stylesheet" href="../../css/login.css">
    </head>
    <body>
        <h1>Request from ${pageContext.errorData.requestURI} is failed</h1>
        <br/>
        <h1>Servlet name: ${pageContext.errorData.servletName}</h1>
        <br/>
        <h1>Status code: ${pageContext.errorData.statusCode}</h1>
        <br/>
        <h1>Exception: ${pageContext.exception.message}</h1>
        <br/>
        <h1><a href="../../jsp/welcome.jsp">back to welcome page</a></h1>
    </body>
</html>
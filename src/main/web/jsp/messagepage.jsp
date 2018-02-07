<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<html lang="en">
    <head>
        <title>Message page</title>

        <link rel="shortcut icon" href="../images/pageLogo.png" type="image/png">
        <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
        <link href="../css/default.css" rel="stylesheet" type="text/css" media="all" />
        <link href="../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
        <link rel="stylesheet" href="../css/login.css">
    </head>
    <body>
        <h1>${wrongdata}</h1>
        <h1>${userExist}</h1>
        <h1>${error}</h1>
        <h1>${message}</h1>
        <h1>${param.userRegistered}</h1>
        <h1>${param.message}</h1>
        <h1><a href="../jsp/welcome.jsp">back to welcome page</a></h1>
    </body>
</html>
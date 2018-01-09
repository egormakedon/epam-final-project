<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Error 414</title>
        <meta charset="UTF-8">
    </head>
    <body>
        Request from ${pageContext.errorData.requestURI} is failed
        <br/>
        Servlet name: ${pageContext.errorData.servletName}
        <br/>
        Status code: ${pageContext.errorData.statusCode}
        <br/>
        Exception: ${pageContext.exception.message}
        <br/>
        <a href="../../jsp/welcome.jsp">back to welcome page</a>
    </body>
</html>
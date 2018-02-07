<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Show form</title>

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
                <h1><a href="../../../jsp/welcome.jsp">Selection Committee</a></h1>
            </div>
            <form id="menu" action="/Controller" method="get">
                <ul>
                    <li>
                        <a>
                            <input type="hidden" name="command" value="profile">
                            <input type="submit" style="background:none!important;
                                                                 color:inherit;
                                                                 border:none;
                                                                 padding:0!important;
                                                                 font: inherit;
                                                                 cursor: pointer;"
                                   accesskey="1" value="PROFILE">
                        </a>
                    </li>
                </ul>
            </form>
        </div>
    </div>
    <h1> UNIVERSITY: ${requestScope.UNIVERSITY}</h1>
    <h1> FACULTY: ${requestScope.FACULTY}</h1>
    <h1> SPECIALITY: ${requestScope.SPECIALITY}</h1>
    <br>
    <h1> NAME: ${requestScope.NAME}</h1>
    <h1> SECOND NAME: ${requestScope.SECONDNAME}</h1>
    <h1> SURNAME: ${requestScope.SURNAME}</h1>
    <br>
    <h1> PASSPORT: ${requestScope.PASSPORTID}</h1>
    <h1> COUNTRY: ${requestScope.COUNTRYDOMEN}</h1>
    <h1> PHONE: ${requestScope.PHONE}</h1>
    <br>
    <h1> DATE OF REGISTERING: ${requestScope.DATE}</h1>
    <h1> SCORE: ${requestScope.SCORE}</h1>
<div id="copyright" class="container">
    <p>&copy; 2018. CREATED BY EGOR MAKEDON FOR EPAM SYSTEMS. <a href="http://epam.by" style="color: white">epam.by</a> All rights reserved.</p>
</div>
</body>
</html>

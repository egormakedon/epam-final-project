<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Admin page</title>

    <link rel="shortcut icon" href="../../../images/pageLogo.png" type="image/png">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
    <link href="../../../css/default.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
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
                        <input type="hidden" name="command" value="logout">
                        <input type="submit" style="background:none!important;
    														 color:inherit;
     														 border:none;
    														 padding:0!important;
  														   	 font: inherit;
    														 cursor: pointer;"
                               accesskey="1" value="LOGOUT">
                    </a>
                </li>
            </ul>
        </form>
    </div>
</div>
<div id="header-featured">
    <div id="banner-wrapper">
        <div id="banner" class="container">
            <form action="/Controller" method="post">
                <input type="hidden" name="command" value="showenrollees">
                <input class="button" value="show enrollees" type="submit">
            </form>
            <form action="/Controller" method="post">
                <input type="hidden" name="command" value="setstatement">
                <input class="button" value="set statement" type="submit">
            </form>
            <form action="/Controller" method="post">
                <input type="hidden" name="command" value="resetstatement">
                <input class="button" value="reset statement" type="submit">
            </form>
            <form action="/Controller" method="post">
                <input type="hidden" name="command" value="forwardchangenumberofseats">
                <input class="button" value="change number of seats" type="submit">
            </form>
            <form action="/Controller" method="post">
                <input type="hidden" name="command" value="forwarddeleteuser">
                <input class="button" value="delete user" type="submit">
            </form>
        </div>
    </div>
</div>
<div id="copyright" class="container">
    <p>&copy; 2018. CREATED BY EGOR MAKEDON FOR EPAM SYSTEMS. <a href="http://epam.by" style="color: white">epam.by</a> All rights reserved.</p>
</div>
</body>
</html>

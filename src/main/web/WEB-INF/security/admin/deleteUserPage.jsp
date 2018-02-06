<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Delete user</title>

    <link rel="shortcut icon" href="../../../images/pageLogo.png" type="image/png">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
    <link href="../../../css/default.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
    <link rel="stylesheet" href="../../../css/login.css">
</head>
<body>
<div id="header-wrapper">
    <div id="header" class="container">
        <div id="logo">
            <h1><a href="../../../jsp/welcome.jsp">Selection Committee</a></h1>
        </div>
        <form id="menu" action="/Controller" method="post">
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
<div id="banner-wrapper">
    <div id="content">
        <div class="tittle">
            <p>DELETE USER</p>
        </div>
        <form class="login" action="/Controller" method="post">
            <input type="hidden" name="command" value="deleteuser">
            <p>
                <label for="login">username:</label>
                <input type="text" name="username" id="login" pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{3,15}$" required>
                <span class="form_error">min:3, max:16, first:letter, without special symbols </span>
            </p>
            <p class="login-submit">
                <button type="submit" class="login-button">delete user</button>
            </p>
        </form>
    </div>
</div>
<div id="copyright" class="container">
    <p>&copy; 2018. CREATED BY EGOR MAKEDON FOR EPAM SYSTEMS. <a href="http://epam.by" style="color: white">epam.by</a> All rights reserved.</p>
</div>
</body>
</html>

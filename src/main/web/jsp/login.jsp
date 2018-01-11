<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
      <title>Login</title>

      <link rel="shortcut icon" href="../images/pageLogo.png" type="image/png">
      <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
      <link href="../css/default.css" rel="stylesheet" type="text/css" media="all" />
      <link href="../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
      <link rel="stylesheet" href="../css/login.css">
    </head>
    <body>
        <div id="header-wrapper">
          <div id="header" class="container">
            <div id="logo">
              <h1><a href="welcome.jsp">Selection Committee</a></h1>
            </div>
          </div>
        </div>
        <div id="banner-wrapper">
          <div id="content">
            <div class="tittle">
              <p>LOGIN</p>
            </div>
            <form class="login" name="" action="welcome.jsp" method="post">
              <p>
                <label for="login">username:</label>
                <input type="text" name="login" id="login" pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{3,15}$" required>
                <span class="form_error">min:3, max:16, first:letter, without special symbols </span>
              </p>
              <p>
                <label for="password">password:</label>
                <input type="password" name="password" id="password" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}" required>
                <span class="form_error">min:6, must has letter in each register, must has min one number</span>
              </p>
              <p class="login-submit">
                <button type="submit" class="login-button">login</button>
              </p>
              <p class="forgot-password"><a href="forgotpassword.jsp"> forget password?</a></p>
            </form>
          </div>
        </div>
        <form id="copyright" class="container" action="/Controller" method="get">
            <input type="hidden" name="command" value="epamlink">
            <p>&copy; 2018. CREATED BY EGOR MAKEDON FOR EPAM SYSTEMS. <input class="ignore-css" type="submit" value="epam.by" style="color: white; background: #2b2b2b"> All rights reserved.</p>
        </form>
    </body>
</html>
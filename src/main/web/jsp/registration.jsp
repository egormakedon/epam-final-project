<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title>Registration</title>

        <link rel="shortcut icon" href="../images/pageLogo.png" type="image/png">
        <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
        <link href="../css/default.css" rel="stylesheet" type="text/css" media="all" />
        <link href="../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
        <link rel="stylesheet" href="../css/login.css">
        <script src="../js/js.js"></script>
    </head>
    <body>
        <div id="header-wrapper">
            <div id="header" class="container">
                <div id="logo">
                    <h1><a href="../jsp/welcome.jsp">Selection Committee</a></h1>
                </div>
            </div>
        </div>
        <div id="banner-wrapper">
            <div id="content">
                <div class="tittle">
                    <p>REGISTRATION</p>
                </div>
                <form class="login" action="/Controller" method="post" onsubmit="return checkPassword()">
                    <input type="hidden" name="command" value="registration">
                    <p>
                        <label for="email">email:</label>
                        <input type="text" name="email" id="email" maxlength="64" pattern="^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)$" required>
                        <span class="form_error">example@site.com</span>
                    </p>
                    <p>
                        <label for="login">username:</label>
                        <input type="text" name="username" id="login" pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{3,15}$" minlength="3" maxlength="16" required>
                        <span class="form_error">min:3, max:16, first:letter, without special symbols </span>
                    </p>
                    <p>
                        <label for="password1">password:</label>
                        <input type="password" name="password1" id="password1" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}" minlength="6" required>
                        <span class="form_error">min:6, must has letter in each register, must has min one number</span>
                    </p>
                    <p>
                        <label for="password2">password again:</label>
                        <input type="password" name="password2" id="password2" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}" minlength="6" required>
                        <span class="form_error">min:6, must has letter in each register, must has min one number</span>
                    </p>
                    <p class="login-submit">
                        <button type="submit" class="login-button">register</button>
                    </p>
                </form>
            </div>
        </div>
        <div id="copyright" class="container">
            <p>&copy; 2018. CREATED BY EGOR MAKEDON FOR EPAM SYSTEMS. <a href="http://epam.by" style="color: white">epam.by</a> All rights reserved.</p>
        </div>
    </body>
</html>
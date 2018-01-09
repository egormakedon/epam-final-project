<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Forgot password</title>
        <meta charset="UTF-8">
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
                    <p>FORGOT PASSWORD?</p>
                </div>
                <form class="login" name="" action="welcome.jsp" method="post">
                    <p>
                        <label for="login">email:</label>
                        <input type="email" name="email" id="login" required>
                        <span class="form_error">example@site.com</span>
                    </p>
                    <p class="login-submit">
                        <button type="submit" class="login-button">send</button>
                    </p>
                </form>
            </div>
        </div>
        <div id="copyright" class="container">
            <p>&copy; 2018. CREATED BY EGOR MAKEDON FOR <a href="epam.by">epam.by</a>. All rights reserved.</p>
        </div>
    </body>
</html>
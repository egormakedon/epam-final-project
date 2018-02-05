<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
	<head>
		<title>Welcome</title>

		<link rel="shortcut icon" href="../images/pageLogo.png" type="image/png">
		<link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
		<link href="../css/default.css" rel="stylesheet" type="text/css" media="all" />
		<link href="../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
	</head>
	<body>
		<div id="header-wrapper">
			<div id="header" class="container">
				<div id="logo">
					<h1><a href="../jsp/welcome.jsp">Selection Committee</a></h1>
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
						<li><a href="../jsp/registration.jsp" accesskey="2" title="">Registration</a></li>
					</ul>
				</form>
			</div>
		</div>
		<img src="../images/banner.png" style="width: 100%; height: 100%">
		<div id="copyright" class="container">
			<p>&copy; 2018. CREATED BY EGOR MAKEDON FOR EPAM SYSTEMS. <a href="http://epam.by" style="color: white">epam.by</a> All rights reserved.</p>
		</div>
	</body>
</html>

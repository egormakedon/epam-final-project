<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Change number of seats</title>

    <link rel="shortcut icon" href="../../../images/pageLogo.png" type="image/png">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
    <link href="../../../css/default.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
    <link rel="stylesheet" href="../../../css/login.css">

    <style type="text/css">
        h1 {
            z-index: 100; /* текст не размыт */
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
<div id="banner-wrapper">
    <div id="content">
        <div class="tittle">
            <p>CHANGE NUMBER OF SEATS</p>
        </div>
        <form class="login" action="/Controller" method="post">
            <input type="hidden" name="command" value="changenumberofseats">
            <h1>
                <select name="speciality">
                    <option>Проектирование и производство программно-управляемых электронных средств</option>
                    <option>Моделирование и компьютерное проектирование радиоэлектронных средств</option>
                    <option>Программируемые мобильные системы</option>
                    <option>Программно-управляемые электронно-оптические системы</option>
                    <option>Медицинская электроника</option>
                    <option>Инженерно-психологическое обеспечение информационных технологий</option>
                    <option>Электронные системы безопасности</option>
                    <option>Информационные системы и технологии (в обеспечении промышленной безопасности)</option>
                    <option>Информационные системы и технологии (в бизнес-менеджменте)</option>
                    <option>Автоматизированные системы обработки информации</option>
                    <option>Искусственный интеллект</option>
                    <option>Информационные технологии и управление в технических системах</option>
                    <option>Промышленная электроника</option>
                    <option>Информационные системы и технологии (в игровой индустрии)</option>
                    <option>Микро- и наноэлектронные технологии и системы</option>
                    <option>Квантовые информационные системы</option>
                    <option>Нанотехнологии и наноматериалы в электронике</option>
                    <option>Радиотехника (программируемые радиоэлектронные средства)</option>
                    <option>Радиоэлектронные системы</option>
                    <option>Радиоинформатика</option>
                    <option>Радиоэлектронная защита информации</option>
                    <option>Электронные и информационно-управляющие системы физических установок</option>
                    <option>Профессиональное образование (информатика)</option>
                    <option>Вычислительные мышины, системы и сети</option>
                    <option>Программное обеспечение информационных технологий</option>
                    <option>Информатика и технологии программирования</option>
                    <option>Электронные вычислительные средства</option>
                    <option>Инфокоммуникационные технологии (системы телекоммуникаций)</option>
                    <option>Инфокоммуникационные технологии (сети инфокоммуникаций)</option>
                    <option>Инфокоммуникационные технологии (системы распределения мультимедийной информации)</option>
                    <option>Инфокоммуникационные системы (стандартизация, сертификация и контроль параметров)</option>
                    <option>Защита информации в телекоммуникациях</option>
                    <option>Информационные системы и технологии (в экономике)</option>
                    <option>Информационные системы и технологии (в логистике)</option>
                    <option>Электронный маркетинг</option>
                    <option>Экономика электронного бизнеса</option>
                    <option>Инфокуммуникационные технологии</option>
                    <option>Радиотехника</option>
                    <option>Вычислительные машины, системы и сети (специального назначения)</option>
                </select>
            </h1>
            <br>
            <p>
                <label for="login">number of seats:</label>
                <input type="text" name="numberOfSeats" id="login" pattern="^([0-9]+)$" required>
                <span class="form_error">number</span>
            </p>
            <br>
            <p class="login-submit">
            <button type="submit" class="login-button">change</button>
            </p>
        </form>
    </div>
</div>
<div id="copyright" class="container">
    <p>&copy; 2018. CREATED BY EGOR MAKEDON FOR EPAM SYSTEMS. <a href="http://epam.by" style="color: white">epam.by</a> All rights reserved.</p>
</div>
</body>
</html>

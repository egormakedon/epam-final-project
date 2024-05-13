<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<html lang="en">
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="property.text" var="locale" scope="session"/>

    <fmt:message bundle="${locale}" key="text.sendform.title" var="title"/>
    <fmt:message bundle="${locale}" key="text.main.title" var="main_title"/>
    <fmt:message bundle="${locale}" key="text.main.local.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="text.main.local.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="text.main.created.by" var="created_by"/>
    <fmt:message bundle="${locale}" key="text.main.all.rights" var="all_rights"/>
    <fmt:message bundle="${locale}" key="text.main.profile" var="profile"/>

    <fmt:message bundle="${locale}" key="text.university.label" var="university"/>
    <fmt:message bundle="${locale}" key="text.speciality.label" var="speciality"/>
    <fmt:message bundle="${locale}" key="text.faculty.label" var="faculty"/>
    <fmt:message bundle="${locale}" key="text.countrydomen.label" var="country_domen"/>
    <fmt:message bundle="${locale}" key="text.name.label" var="name"/>
    <fmt:message bundle="${locale}" key="text.surname.label" var="surname"/>
    <fmt:message bundle="${locale}" key="text.secondname.label" var="secondname"/>
    <fmt:message bundle="${locale}" key="text.passport.label" var="passport"/>
    <fmt:message bundle="${locale}" key="text.phone.label" var="phone"/>
    <fmt:message bundle="${locale}" key="text.certificate.label" var="certificate"/>
    <fmt:message bundle="${locale}" key="text.send.sumbit" var="send"/>
    <fmt:message bundle="${locale}" key="text.subject.label" var="subject"/>
    <fmt:message bundle="${locale}" key="text.mark.label" var="mark"/>

    <title>${title}</title>

    <link rel="shortcut icon" href="../../../images/pageLogo.png" type="image/png">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
    <link href="../../../css/default.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
    <link rel="stylesheet" href="../../../css/login.css">

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
            <h1><a href="../../../jsp/welcome.jsp">${main_title}</a></h1>
        </div>
        <div id="menu">
            <ul>
                <li>
                    <a>
                        <form action="/Controller" method="get">
                            <input type="hidden" name="command" value="profile">
                            <input type="submit" style="background:none!important;
    														 color:inherit;
     														 border:none;
    														 padding:0!important;
  														   	 font: inherit;
    														 cursor: pointer;"
                                   accesskey="1" value="${profile}">
                        </form>
                    </a>
                </li>
                <li>
                    <a>
                        <form action="/Controller" method="get">
                            <input type="hidden" name="command" value="changelang">
                            <input type="hidden" name="lang" value="en">
                            <input type="submit" style="background:none!important;
    														 color:inherit;
     														 border:none;
    														 padding:0!important;
  														   	 font: inherit;
    														 cursor: pointer;"
                                   accesskey="2" value="${en_button}">
                        </form>
                    </a>
                </li>
                <li>
                    <a>
                        <form action="/Controller" method="get">
                            <input type="hidden" name="command" value="changelang">
                            <input type="hidden" name="lang" value="ru">
                            <input type="submit" style="background:none!important;
    														 color:inherit;
     														 border:none;
    														 padding:0!important;
  														   	 font: inherit;
    														 cursor: pointer;"
                                   accesskey="3" value="${ru_button}">
                        </form>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div id="banner-wrapper">
    <div id="content">
        <div class="tittle">
            <p>${title}</p>
        </div>
        <form class="login" action="/Controller" method="post">
            <input type="hidden" name="command" value="sendform">
            <h1>
                <select name="UNIVERSITY">
                    <option disabled>${university}</option>
                    <option>БГУИР</option>
                </select>

                <br>

                <select name="FACULTY">
                    <option disabled>${faculty}</option>
                    <option>Факультет компьютерного проектирования</option>
                    <option>Факультет информационных технологий и управления</option>
                    <option>Факультет радиотехники и электроники</option>
                    <option>Факультет компьютерных систем и сетей</option>
                    <option>Факультет инфокоммуникаций</option>
                    <option>Инженерно-экономический факультет</option>
                    <option>Военный факультет</option>
                </select>

                <br>

                <select name="SPECIALITY">
                    <option disabled>${speciality}</option>
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

            <h1>
                <select name="COUNTRYDOMEN">
                    <option disabled>${country_domen}</option>
                    <option>BY</option>
                    <option>RU</option>
                    <option>US</option>
                    <option>PL</option>
                    <option>UA</option>
                </select>
            </h1>

            <br>

            <p>
                <label for="name">${name}</label>
                <input type="text" name="NAME" id="name" pattern="^(([А-Я][а-я]{2,50})|([A-Z][a-z]{2,50}))$" required>
                <span class="form_error"></span>
            </p>

            <br>

            <p>
                <label for="surname">${surname}</label>
                <input type="text" name="SURNAME" id="surname" pattern="^(([А-Я][а-я]{2,50})|([A-Z][a-z]{2,50}))$" required>
                <span class="form_error"></span>
            </p>

            <br>

            <p>
                <label for="secondname">${secondname}</label>
                <input type="text" name="SECONDNAME" id="secondname" pattern="^(([А-Я][а-я]{2,50})|([A-Z][a-z]{2,50}))$">
                <span class="form_error"></span>
            </p>

            <br>

            <p>
                <label for="passportid">${passport}</label>
                <input type="text" name="PASSPORTID" id="passportid" pattern="^([A-Z0-9]{7,10})$" required>
                <span class="form_error"></span>
            </p>

            <br>

            <p>
                <label for="phone">${phone}</label>
                <input type="text" name="PHONE" id="phone" pattern="^(375(29|33|25)[0-9]{7})$" required>
                <span class="form_error"></span>
            </p>

            <br>

            <p>
                <label for="certificate">${certificate}</label>
                <input type="text" name="CERTIFICATE" id="certificate" pattern="^([0-9]+)$" required>
                <span class="form_error"></span>
            </p>

            <br>

            <h1>
                <select name="subjectId1">
                    <option disabled>${subject} 1</option>
                    <option VALUE="0">RUSSIANLANG</option>
                    <option VALUE="1">BELORUSSIANLANG</option>
                    <option VALUE="2">PHYSICS</option>
                    <option VALUE="3">MATH</option>
                    <option VALUE="4">CHEMISTRY</option>
                    <option VALUE="5">BIOLOGY</option>
                    <option VALUE="6">FOREIGNLANG</option>
                    <option VALUE="7">HISTORYOFBELARUS</option>
                    <option VALUE="8">SOCIALSTUDIES</option>
                    <option VALUE="9">GEOGRAPHY</option>
                    <option VALUE="10">HISTORY</option>
                </select>
            </h1>

            <br>

            <p>
                <label for="subjectValue1">${mark}</label>
                <input type="text" name="subjectValue1" id="subjectValue1" pattern="^([0-9]+)$" required>
                <span class="form_error"></span>
            </p>

            <h1>
                <select name="subjectId2">
                    <option disabled>${subject} 2</option>
                    <option VALUE="0">RUSSIANLANG</option>
                    <option VALUE="1">BELORUSSIANLANG</option>
                    <option VALUE="2">PHYSICS</option>
                    <option VALUE="3">MATH</option>
                    <option VALUE="4">CHEMISTRY</option>
                    <option VALUE="5">BIOLOGY</option>
                    <option VALUE="6">FOREIGNLANG</option>
                    <option VALUE="7">HISTORYOFBELARUS</option>
                    <option VALUE="8">SOCIALSTUDIES</option>
                    <option VALUE="9">GEOGRAPHY</option>
                    <option VALUE="10">HISTORY</option>
                </select>
            </h1>

            <br>

            <p>
                <label for="subjectValue2">${mark}</label>
                <input type="text" name="subjectValue2" id="subjectValue2" pattern="^([0-9]+)$" required>
                <span class="form_error"></span>
            </p>

            <h1>
                <select name="subjectId3">
                    <option disabled>${subject} 3</option>
                    <option VALUE="0">RUSSIANLANG</option>
                    <option VALUE="1">BELORUSSIANLANG</option>
                    <option VALUE="2">PHYSICS</option>
                    <option VALUE="3">MATH</option>
                    <option VALUE="4">CHEMISTRY</option>
                    <option VALUE="5">BIOLOGY</option>
                    <option VALUE="6">FOREIGNLANG</option>
                    <option VALUE="7">HISTORYOFBELARUS</option>
                    <option VALUE="8">SOCIALSTUDIES</option>
                    <option VALUE="9">GEOGRAPHY</option>
                    <option VALUE="10">HISTORY</option>
                </select>
            </h1>

            <br>

            <p>
                <label for="subjectValue3">${mark}</label>
                <input type="text" name="subjectValue3" id="subjectValue3" pattern="^([0-9]+)$" required>
                <span class="form_error"></span>
            </p>

            <h1>
                <p>
                    <button type="submit" class="login-button">${send}</button>
                </p>
            </h1>
        </form>
    </div>
</div>
<div id="copyright" class="container">
    <p>${created_by} <a href="http://epam.by" style="color: white">epam.by</a> ${all_rights}</p>
</div>
</body>
</html>

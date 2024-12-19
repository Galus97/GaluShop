<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Login form</title>
</head>
<body>


    <form:form method="post" action="/processLogin">

    <label class="label">
        Podaj adres E-mail:
        <input type="text" name="username"/>
    </label>
    <br>
    <label>
        Podaj hasło:
        <input type="password" name="password"/>
    </label>
    <br>
    <label class="label">
        <input type="submit" value="Zaloguj się">
    </label>
    <br>
    <label class="label">
        <input type="reset" value="Usuń wartości">
    </label>

    </form:form>

</body>
</html>
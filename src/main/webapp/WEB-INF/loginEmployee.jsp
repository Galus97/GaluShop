<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Employee form</title>
</head>
<body class="body">

<div class="login-container">

    <form:form method="post" action="/loginEmployee" class="form">

    <label class="label">
        Podaj nazwę użytkownika:
        <input name="username" class="input-text" />
    </label>
    <br>
    <label class="label">
        Podaj hasło:
        <input type="password" name="password" class="input-password"/>
    </label>
    <br>
    <label class="label">
        <input type="submit" value="Zaloguj się" class="button-submit">
    </label>
    <br>
    <label class="label">
        <input type="reset" value="Usuń wartości" class="button-reset">
    </label>

    </form:form>

</body>
</html>
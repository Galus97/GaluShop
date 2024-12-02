<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registration user</title>
</head>
<body>

<form:form action="/registerUser" method="post" modelAttribute="user">
    <label>
        Imię: <form:input path="firstName" type="text"/>
    </label>
    <label>
        Nazwisko: <form:input path="lastName" type="text"/>
    </label>
    <label>
        E-mail: <form:input path="email" type="text"/>
    </label>
    <label>
        Hasło: <form:input path="password" type="password"/>
    </label>
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
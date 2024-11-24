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
        <form:input path="firstName" type="text"/>
    </label>
    <label>
        <form:input path="lastName" type="text"/>
    </label>
    <label>
        <form:input path="email" type="text"/>
    </label>
    <label>
        <form:input path="password" type="password"/>
    </label>
    <label>
        <form:input path="repeatPassword" type="password"/>
    </label>

</form:form>


</body>
</html>
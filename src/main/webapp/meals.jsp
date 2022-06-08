<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %><%--
  Created by IntelliJ IDEA.
  User: Artur May
  Date: 03.06.2022
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
...
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table border="1" cellpadding="2" cellspacing="2">
        <tr>
            <th>Description</th>
            <th>Date</th>
            <th>Calories</th>
            <th>Excess</th>
        </tr>
        <c:forEach items="${mealsTo}" var="meal">
            <tr>
                <td>${meal.description}</td>
                <td>${meal.dateTime}</td>
                <td>${meal.calories}</td>
                <td>${meal.excess}</td>
            </tr>
        </c:forEach>

    </table>


</body>
</html>

<%--
<tr>
    <th>Description</th>
    <th>Date</th>
    <th>Calories</th>
</tr>
<c:forEach items="${mealsTo}" var="meal">
    <tr>
        <td>${meal.description}</td>
        <td>${meal.dateTime}</td>
        <td>${meal.calories}</td>
    </tr>
</c:forEach>--%>

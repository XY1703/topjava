<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table>
    <thead>
    <tr>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td>Excess</td>
    </tr>
    </thead>

    <c:forEach var="meal" items="${mealsTo}">
        <tr style="background-color: ${meal.excess ? 'green' : 'red'}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <td><c:out value="${meal.dateTime}"/></td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td>${meal.excess}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
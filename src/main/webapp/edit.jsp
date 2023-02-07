<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://localhost/functions" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="theme" content="ru">
    <link rel="stylesheet" href="style.css" type="text/css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<p><a href="addmeal.html">Add Meal</a></p>
<form method="POST" action='meals' name="addMeal">
    <input hidden="hidden" type="number" readonly="readonly" name="id"
           value="<c:out value="${meal.getId()}" />"/> <br/>
    DateTime : <input
        type="datetime-local" name="date"
        value="${fmt:formatLocalDateTime(meal.getDateTime(), 'yyyy-MM-dd HH:mm')}"/> <br/>
    Description : <input
        type="text" name="desc"
        value="<c:out value="${meal.getDescription()}" />"/> <br/>
    Calories : <input
        type="number" min="0" max="10000" name="cal"
        value="<c:out value="${meal.getCalories()}" />"/> <br/>
    <input type="submit" value="Submit"/>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://localhost/functions" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="theme" content="ru">
    <title>Insert or edit</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2><a href="meals">Meals</a></h2>
<h2>${param.action == 'edit' ? 'Edit' : 'Add'} meal</h2>
<form method="POST" action='meals' name="editMeal">
    <input hidden="hidden" type="number" readonly="readonly" name="id"
           value="${not empty meal.id ? meal.id: null}"/> <br/>
    DateTime : <input
        type="datetime-local" name="date"
        value="${fmt:formatLocalDateTime(meal.dateTime)}"/> <br/>
    Description : <input
        type="text" name="description"
        value="${meal.description}"/> <br/>
    Calories : <input
        type="number" min="0" max="10000" name="calorie"
        value="${meal.calories}"/> <br/>
    <input type="submit" value="Submit"/>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
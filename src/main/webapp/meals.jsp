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
<h2>Meals</h2>
<p><a href="meals?action=create">Add Meal</a></p>
    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th colspan="2">Actions</th>
        </tr>
        </thead>

        <tbody class="сl">
        <c:forEach var="mealTo" items="${mealTOList}">
            <tr class="${mealTo.excess?'red':'green'}">
                <td>${fmt:formatLocalDateTime(mealTo.dateTime)}</td>
                <td>${mealTo.description}</td>
                <td>${mealTo.calories}</td>
                <td><a href="meals?action=edit&id=${mealTo.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${mealTo.id}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
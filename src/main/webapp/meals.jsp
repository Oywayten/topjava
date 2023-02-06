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
<p><a href="addmeal.html">Add Meal</a></p>
    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        </thead>

        <tbody class="сl">
        <c:forEach var="num" items="${list}">
            <tr class="${num.isExcess()?'red':'green'}">
                <td>${fmt:formatLocalDateTime(num.getDateTime(), 'yyyy-MM-dd HH:mm')}</td>
                <td>${num.getDescription()}</td>
                <td>${num.getCalories()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
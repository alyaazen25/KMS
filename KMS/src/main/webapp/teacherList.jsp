<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Teacher List</title>
    <link rel="stylesheet" href="teacherList.css"> <!-- Optional: your custom CSS -->
</head>
<body>

<h2>List of Teachers</h2>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Role</th>
            <th>Type</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="teacher" items="${teacherList}">
            <tr>
                <td>${teacher.teacherId}</td>
                <td>${teacher.teacherName}</td>
                <td>${teacher.teacherEmail}</td>
                <td>${teacher.teacherPhone}</td>
                <td>${teacher.teacherRole}</td>
                <td>${teacher.teacherType}</td>
            </tr>
        </c:forEach>

        <c:if test="${empty teacherList}">
            <tr>
                <td colspan="6" style="text-align:center;">No teachers found.</td>
            </tr>
        </c:if>
    </tbody>
</table>

<br/>
<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>

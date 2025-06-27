<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Subject</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/updateSubject.css">
</head>
<body>

<div class="form-container">
    <h2>Update Subject</h2>

    <form action="${pageContext.request.contextPath}/UpdateSubjectController" method="post">
        <!-- Hidden field to send subjectId -->
        <input type="hidden" name="subjectId" value="${subject.subjectId}" />

        <div class="form-group">
            <label for="subjectName">Subject Name</label>
            <input type="text" id="subjectName" name="subjectName" value="${subject.subjectName}" required>
        </div>

        <div class="form-group">
            <label for="teacherId">Assign Teacher</label>
            <select id="teacherId" name="teacherId" required>
                <option value="">-- Select Teacher --</option>
                <c:forEach var="teacher" items="${teacherList}">
                    <option value="${teacher.teacherId}" 
                        <c:if test="${teacher.teacherId == subject.teacherId}">selected</c:if>>
                        ${teacher.teacherName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-submit">Update</button>
            <a href="${pageContext.request.contextPath}/ListSubjectController" class="btn-cancel">Cancel</a>
        </div>
    </form>
</div>

</body>
</html>

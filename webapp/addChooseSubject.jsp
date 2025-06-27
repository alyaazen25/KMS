<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register Subject</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/listChooseSubject.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<div class="container">
    <h2>Register New Subject</h2>

    <div class="subject-grid">
        <c:choose>
            <c:when test="${empty subjectList}">
                <div class="placeholder">
                    All available subjects are already registered.
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="subject" items="${subjectList}">
                    <div class="subject-card">
                        <p><strong>${subject.subjectName}</strong></p>
                        <p>Teacher ID: ${subject.teacherId}</p>

                        <form action="RegisterSubjectController" method="post">
                            <input type="hidden" name="studId" value="${selectedStudId}" />
                            <input type="hidden" name="subjectId" value="${subject.subjectId}" />
                            <button type="submit">Add</button>
                        </form>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

    <div style="margin-top: 20px;">
        <form action="ListChooseSubjectController" method="get">
            <input type="hidden" name="studId" value="${selectedStudId}" />
            <button type="submit" class="btn-back">← Back to Subject List</button>
        </form>
    </div>
</div>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Choose Subject</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/listChooseSubject.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

<div class="container">
    <h2>Choose Subject</h2>

    <!-- Student Dropdown -->
    <form action="ListChooseSubjectController" method="get">
        <div class="form-row">
            <label>Student</label>
            <select name="studId" onchange="this.form.submit()" required>
                <option value="">Select student</option>
                <c:forEach var="student" items="${students}">
                    <option value="${student.studId}" 
                        <c:if test="${student.studId == selectedStudId}">selected</c:if>>
                        ${student.studName}
                    </option>
                </c:forEach>
            </select>
        </div>
    </form>

    <!-- Register New Subject Button -->
    <div class="align-right">
        <form action="AddChooseSubjectController" method="get">
            <input type="hidden" name="studId" value="${selectedStudId}" />
            <button type="submit" class="btn-add">+ Register New Subject</button>
        </form>
    </div>

    <!-- Subject List -->
    <div class="subject-grid">
        <c:choose>
            <c:when test="${empty subjectList}">
                <div class="placeholder">
                    No subjects registered or available for this student.
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="subject" items="${subjectList}">
                    <div class="subject-card">
                        <p><strong>${subject.subjectName}</strong></p>
                        <p>Teacher ID: ${subject.teacherId}</p>

                        <c:choose>
                            <c:when test="${registeredSubjectIds.contains(subject.subjectId)}">
                                <span class="status registered">Registered</span>
                                <form action="DeleteChooseSubjectController" method="post">
   									 <input type="hidden" name="studId" value="${selectedStudId}" />
    								 <input type="hidden" name="subjectId" value="${subject.subjectId}" />
    									<button type="submit">Delete</button>
								</form>

                            </c:when>
                            <c:otherwise>
                                <form action="RegisterSubjectController" method="post">
                                    <input type="hidden" name="studId" value="${selectedStudId}" />
                                    <input type="hidden" name="subjectId" value="${subject.subjectId}" />
                                    <button type="submit" class="btn-register">Register</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>

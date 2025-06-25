<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Subject List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- External CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/listSubject.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

    <script>
        function toggleSidebar() {
            document.getElementById("sidebar").classList.toggle("active");
        }

        function toggleDropdown(id) {
            document.getElementById(id).classList.toggle("show");
        }

        window.addEventListener('click', function(e) {
            document.querySelectorAll(".dropdown-content").forEach(dc => {
                if (!dc.contains(e.target) && !dc.previousElementSibling.contains(e.target)) {
                    dc.classList.remove("show");
                }
            });
        });
    </script>
</head>
<body>

    <!-- Header -->
    <header>
        <button class="toggle-btn" onclick="toggleSidebar()">
            <i class="fa-solid fa-bars"></i>
        </button>
        <div class="logo">
            <img src="${pageContext.request.contextPath}/image/logo.png" alt="ALKAUTHAR EDUQIDS">
        </div>
    </header>

    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
        <div class="profile">
            <img src="${pageContext.request.contextPath}/image/admin.jpg" alt="Admin Profile">
            <h3>Nurul Najah Binti Wahab</h3>
            <p>Administrator</p>
        </div>

        <a href="#">Dashboard</a>
        <a href="CreateSubjectController">Create Subject</a>

        <!-- Dropdown for Students -->
        <div class="dropdown">
            <a href="javascript:void(0)" onclick="toggleDropdown('studentDropdown')">
                Students <i class="fa fa-caret-down"></i>
            </a>
            <div class="dropdown-content" id="studentDropdown">
                <a href="#">View Students</a>
                <a href="#">Edit Students</a>
            </div>
        </div>

        <a href="#">Teachers</a>
        <a href="#">Accounts</a>
        <a href="#">Logout</a>
    </div>

    <!-- Page Content -->
    <div class="container">
        <h2>Subject</h2>
        <button class="btn-add" onclick="location.href='createSubject.jsp'">+ Record</button>

        <div class="subject-grid">
    <c:choose>
        <c:when test="${empty subjectList}">
            <div class="placeholder">
                No subjects available yet. Please add subjects using the "+ Record" button.
            </div>
        </c:when>
        <c:otherwise>
<c:forEach var="subject" items="${subjectList}">
    <div class="subject-card">
        <i class="fa-solid fa-book"></i>
        <p>${subject.subjectName}</p>

        <!-- Action Buttons -->
        <div class="card-actions">
            <a href="EditSubjectController?subjectId=${subject.subjectId}" class="btn-edit">
                <i class="fa-solid fa-pen-to-square"></i> Edit
            </a>
            <a href="DeleteSubjectController?subjectId=${subject.subjectId}" class="btn-delete" onclick="return confirm('Are you sure you want to delete this subject?');">
                <i class="fa-solid fa-trash"></i> Delete
            </a>
        </div>
    </div>
</c:forEach>
        </c:otherwise>
    </c:choose>
</div>

    </div>

</body>
</html>

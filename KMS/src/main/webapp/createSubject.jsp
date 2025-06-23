<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Create Subject - Teacher</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- External CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/createSubject.css">
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

  <!-- Page content -->
  <div class="container">
    <form action="CreateSubjectController" method="post" class="create-subject-form">
      <div class="form-group-row">
        <div>
          <label for="subjectName">Subject Name:</label>
          <input type="text" name="subjectName" id="subjectName" required />
        </div>
      </div>

      <div class="form-group-row">
        <div>
          <label for="teacherId">Assign Teacher:</label>
          <select name="teacherId" id="teacherId" required>
            <option value="">-- Select Teacher --</option>
            <c:forEach var="teacher" items="${teacherList}">
              <option value="${teacher.teacherId}">${teacher.teacherName}</option>
            </c:forEach>
          </select>
        </div>
      </div>

      <div class="submit-container">
        <button type="submit" class="submit-btn">Create Subject</button>
      </div>
    </form>
  </div>

</body>
</html>

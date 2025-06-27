<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
   
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Parent Dashboard</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <link rel="stylesheet" href="adminDashboard.css">
</head>
<body>
  <header>
    <button class="navSidebar" onclick="toggleSidebar()"><i class="fa-solid fa-bars"></i></button>
    <div class="logo">
      <img src="images/LOGO-AL-KAUTHAR-EDUQIDS.png" alt="ALKAUTHAR EDUQIDS Logo">
    </div>
  </header>

  <nav class="sidebar" id="sidebar">
    <div class="profile">
      <img src="images/admin.jpg" alt="Admin Profile Photo">
      <h3><%= session.getAttribute("parentName") %></h3>

      <p>Parent</p>
    </div>
    <a href="#">Home</a>
    <a href="ListStudentController">Student<a>
    <a href="ListChooseSubjectController">Subject</a>
    <a href="#">Progress</a>
    <a href="#">Logout</a>
  </nav>

  <div class="banner"></div>

  <main class="dashboard" id="dashboard">
    <section class="welcome">
      <h2>Welcome back, <%= session.getAttribute("parentName") %></h2>

    <div class="cards">
    <a href= "#">
      <div class="card1">
        <h3>40</h3>
        <p>Students</p>
      </div>
      </a>
      
      <a href="#"> 
      <div class="card2">
        <h3>4</h3>
        <p>Teachers</p>
      </div>
      </a>
      
      <a href="#">
      <div class="card1">
        <h3>12</h3>
        <p>Registration</p>
      </div>
      </a>
      
      <a href="#">
      <div class="card2">
        <h3>3</h3>
        <p>Accounts</p>
      </div>
      </a>
      
   </div>

    <div class="recent-activity">
      <h2>Recent Activity</h2>
      <div class="activity-box">
        <ul class="activity-list">
          <li>
            <div class="activity-item">
              <span><a href="#">@username</a> xxxxx xxxxx  xxxxx  xxxxxx  xxx</span>
            </div>
            
          </li>
        </ul>
      </div>
    </div>
  </main>

  <script>
    function toggleSidebar() {
      const sidebar = document.getElementById("sidebar");
      const dashboard = document.getElementById("dashboard");
      sidebar.classList.toggle("show");
      dashboard.classList.toggle("shifted");
    }
  </script>
</body>
</html>
package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import kms.dao.teacherDAO;
import kms.model.teacher;

@WebServlet("/CreateAccTController")
public class CreateAccTController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateAccTController() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Create teacher object
		teacher teach = new teacher();

		// Retrieve input from HTML form
		teach.setTeacherName(request.getParameter("name"));
		teach.setTeacherEmail(request.getParameter("email"));
		teach.setTeacherPass(request.getParameter("password"));
		teach.setTeacherPhone(request.getParameter("phone"));
		teach.setTeacherRole(request.getParameter("role"));
		teach.setTeacherType(request.getParameter("teacherType"));

		// TEMP FIX: Set adminId = 1 (must exist in DB)
		teach.setAdminId(1);

		// Insert teacher into DB
		teacherDAO.addTeacher(teach);

		// Redirect to login page
		request.setAttribute("message", "Account successfully created");
		RequestDispatcher req = request.getRequestDispatcher("login.jsp");
		req.forward(request, response);
	}
}

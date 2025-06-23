package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kms.dao.subjectDAO;
import kms.model.subject;

import java.io.IOException;

/**
 * Servlet implementation class CreateSubjectController
 */
@WebServlet("/CreateSubjectController")
public class CreateSubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSubjectController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//create Shawl object
				subject sub = new subject();
				
				//retrieve input from HTML and set the values to the Subject object
				sub.setSubjectName(request.getParameter("subjectName"));
				//subject.setTeacherId(Integer.parseInt(request.getParameter("teacherId")));
				String teacherIdStr = request.getParameter("teacherId");
				if (teacherIdStr != null && !teacherIdStr.isEmpty()) {
				    sub.setTeacherId(Integer.parseInt(teacherIdStr));
				} else {
				    sub.setTeacherId(0); // or skip setting if allowed to be null
				}

				
				//call addSubject() from SubjectDao class
				subjectDAO.addSubject(sub);

				
				//set attribute to a servlet request. Set the attribute name to shawls and call getAllSubjects() from SubjectDao class
				request.setAttribute("subs", subjectDAO.getAllSubjects());
				
				//Obtain the RequestDispatcher from the request object. The pathname to the resource is listShawl.jsp
				RequestDispatcher req = request.getRequestDispatcher("listSubject.jsp");
				
				//Dispatch the request to another resource using forward() methods of the RequestDispatcher
				req.forward(request, response);
				
			}
}

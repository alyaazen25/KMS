package kms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;

import kms.dao.studentDAO;
import kms.model.student;

@WebServlet("/createStudentController")
@MultipartConfig(maxFileSize = 16177215)
public class createStudentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public createStudentController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            // Create student object
            student stud = new student();

            stud.setStudName(request.getParameter("name"));
            stud.setStudGender(request.getParameter("gender"));
            stud.setStudAddress(request.getParameter("address"));

            // Parse age safely
            String ageStr = request.getParameter("age");
            int age = 0;
            if (ageStr != null && !ageStr.isEmpty()) {
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    System.out.println("DEBUG - Invalid age: " + ageStr);
                }
            }
            stud.setStudAge(age);

            // Parse DOB safely
            String dobStr = request.getParameter("dob");
            if (dobStr != null && !dobStr.isEmpty()) {
                try {
                    Date dob = Date.valueOf(dobStr);
                    stud.setStudBirthDate(dob);
                } catch (IllegalArgumentException e) {
                    System.out.println("DEBUG - Invalid DOB: " + dobStr);
                }
            }

            // Get parentId from session
            HttpSession session = request.getSession();
            Integer parentId = (Integer) session.getAttribute("parentId");
            stud.setParentId(parentId);

            // File upload
            Part photoPart = request.getPart("photo");
            if (photoPart != null && photoPart.getSize() > 0) {
                stud.setStudPhoto(readPartAsBytes(photoPart));
            }

            Part certPart = request.getPart("birthCert");
            if (certPart != null && certPart.getSize() > 0) {
                stud.setStudBirthCert(readPartAsBytes(certPart));
            }

            // Save to DB
            studentDAO.addStudent(stud);

            // Redirect to student list
            response.sendRedirect("ListStudentController");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addStudent.jsp?msg=fail");
        }
    }

    private byte[] readPartAsBytes(Part part) throws IOException {
        InputStream is = part.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
}

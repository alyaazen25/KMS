package kms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kms.connection.ConnectionManager;
import kms.model.teacher;

public class teacherDAO {

    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static String sql;

    // Add a new teacher
    public static void addTeacher(teacher teach) {
        try {
            con = ConnectionManager.getConnection();
            sql = "INSERT INTO teacher(teacherId, teacherName, teacherEmail, teacherPass, teacherPhone, teacherRole, teacherType, adminId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, teach.getTeacherId());
            ps.setString(2, teach.getTeacherName());
            ps.setString(3, teach.getTeacherEmail());
            ps.setString(4, teach.getTeacherPass());
            ps.setString(5, teach.getTeacherPhone());
            ps.setString(6, teach.getTeacherRole());
            ps.setString(7, teach.getTeacherType());
            ps.setInt(8, teach.getAdminId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL ERROR: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // Get all teachers
    public static List<teacher> getAllTeachers() {
        List<teacher> teachers = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM teacher ORDER BY teacherName";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                teacher t = new teacher();
                t.setTeacherId(rs.getInt("teacherId"));
                t.setTeacherName(rs.getString("teacherName"));
                t.setAdminId(rs.getInt("adminId"));

                teachers.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return teachers;
    }
}

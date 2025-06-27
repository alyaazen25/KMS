package kms.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kms.connection.ConnectionManager;
import kms.model.*;

public class teacherDAO {

    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static String sql;

    // Insert teacher (includes FullTime/PartTime subclass handling)
    public static void addTeacher(teacher teach) {
        try {
            con = ConnectionManager.getConnection();
            sql = "INSERT INTO teacher(teacherName, teacherEmail, teacherPass, teacherPhone, teacherRole, teacherType, adminId) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, teach.getTeacherName());
            ps.setString(2, teach.getTeacherEmail());
            ps.setString(3, teach.getTeacherPass());
            ps.setString(4, teach.getTeacherPhone());
            ps.setString(5, teach.getTeacherRole());
            ps.setString(6, teach.getTeacherType());
            ps.setInt(7, teach.getAdminId());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                teach.setTeacherId(generatedId);
            }

            if ("FullTime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof fullTime) {
                fullTime ft = (fullTime) teach;
                sql = "INSERT INTO fullTime(teacherId, salary) VALUES (?, ?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, ft.getTeacherId());
                ps.setNull(2, Types.DOUBLE);
                ps.executeUpdate();

            } else if ("PartTime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof partTime) {
                partTime pt = (partTime) teach;
                sql = "INSERT INTO partTime(teacherId, contract) VALUES (?, ?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, pt.getTeacherId());
                ps.setNull(2, Types.INTEGER);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // Update teacher by ID
    public static void updateTeacher(teacher teach) {
        try {
            con = ConnectionManager.getConnection();
            sql = "UPDATE teacher SET teacherName=?, teacherEmail=?, teacherPass=?, teacherPhone=?, teacherRole=?, teacherType=?, adminId=? WHERE teacherId=?";
            ps = con.prepareStatement(sql);

            ps.setString(1, teach.getTeacherName());
            ps.setString(2, teach.getTeacherEmail());
            ps.setString(3, teach.getTeacherPass());
            ps.setString(4, teach.getTeacherPhone());
            ps.setString(5, teach.getTeacherRole());
            ps.setString(6, teach.getTeacherType());
            ps.setInt(7, teach.getAdminId());
            ps.setInt(8, teach.getTeacherId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // Delete teacher by ID
    public static void deleteTeacher(int teacherId) {
        try {
            con = ConnectionManager.getConnection();

            sql = "DELETE FROM fullTime WHERE teacherId=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, teacherId);
            ps.executeUpdate();

            sql = "DELETE FROM partTime WHERE teacherId=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, teacherId);
            ps.executeUpdate();

            sql = "DELETE FROM teacher WHERE teacherId=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, teacherId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // Get teacher by ID
    public static teacher getTeacher(int teacherId) {
        teacher teach = null;
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM teacher WHERE teacherId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, teacherId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String teacherType = rs.getString("teacherType");

                if ("FullTime".equalsIgnoreCase(teacherType)) {
                    teach = new fullTime();
                    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM fullTime WHERE teacherId=?");
                    ps2.setInt(1, teacherId);
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        ((fullTime) teach).setSalary(rs2.getDouble("salary"));
                    }
                } else if ("PartTime".equalsIgnoreCase(teacherType)) {
                    teach = new partTime();
                    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM partTime WHERE teacherId=?");
                    ps2.setInt(1, teacherId);
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        ((partTime) teach).setContract(rs2.getInt("contract"));
                    }
                } else {
                    teach = new teacher();
                }

                teach.setTeacherId(rs.getInt("teacherId"));
                teach.setTeacherName(rs.getString("teacherName"));
                teach.setTeacherEmail(rs.getString("teacherEmail"));
                teach.setTeacherPass(rs.getString("teacherPass"));
                teach.setTeacherPhone(rs.getString("teacherPhone"));
                teach.setTeacherRole(rs.getString("teacherRole"));
                teach.setTeacherType(rs.getString("teacherType"));
                teach.setAdminId(rs.getInt("adminId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return teach;
    }

    // Get all teachers
    public static List<teacher> getAllTeacher() {
        List<teacher> teachers = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM teacher ORDER BY teacherId";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String teacherType = rs.getString("teacherType");
                teacher teach;

                if ("FullTime".equalsIgnoreCase(teacherType)) {
                    teach = new fullTime();
                    PreparedStatement ps2 = con.prepareStatement("SELECT salary FROM fullTime WHERE teacherId=?");
                    ps2.setInt(1, rs.getInt("teacherId"));
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        ((fullTime) teach).setSalary(rs2.getDouble("salary"));
                    }
                } else if ("PartTime".equalsIgnoreCase(teacherType)) {
                    teach = new partTime();
                    PreparedStatement ps2 = con.prepareStatement("SELECT contract FROM partTime WHERE teacherId=?");
                    ps2.setInt(1, rs.getInt("teacherId"));
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        ((partTime) teach).setContract(rs2.getInt("contract"));
                    }
                } else {
                    teach = new teacher();
                }

                teach.setTeacherId(rs.getInt("teacherId"));
                teach.setTeacherName(rs.getString("teacherName"));
                teach.setTeacherEmail(rs.getString("teacherEmail"));
                teach.setTeacherPass(rs.getString("teacherPass"));
                teach.setTeacherPhone(rs.getString("teacherPhone"));
                teach.setTeacherRole(rs.getString("teacherRole"));
                teach.setTeacherType(rs.getString("teacherType"));
                teach.setAdminId(rs.getInt("adminId"));

                teachers.add(teach);
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

    // Validate teacher login
    public static teacher validate(String email, String password) {
        teacher teach = null;
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM teacher WHERE teacherEmail = ? AND teacherPass = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                teach = new teacher();
                teach.setTeacherId(rs.getInt("teacherId"));
                teach.setTeacherName(rs.getString("teacherName"));
                teach.setTeacherEmail(rs.getString("teacherEmail"));
                teach.setTeacherPass(rs.getString("teacherPass"));
                teach.setTeacherPhone(rs.getString("teacherPhone"));
                teach.setTeacherRole(rs.getString("teacherRole"));
                teach.setTeacherType(rs.getString("teacherType"));
                teach.setAdminId(rs.getInt("adminId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return teach;
    }
    
    public static Map<Integer, String> getAllTeacherNames() {
        Map<Integer, String> map = new HashMap<>();
        String sql = "SELECT teacherId, teacherName FROM Teacher";

        try {
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                map.put(rs.getInt("teacherId"), rs.getString("teacherName"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return map;
    }


}
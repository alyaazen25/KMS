package kms.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import kms.connection.ConnectionManager;
import kms.model.subject;

public class subjectDAO {

    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static String sql;

    // ✅ Add subject to database
    public static void addSubject(subject sub) {
        try {
            con = ConnectionManager.getConnection();
            sql = "INSERT INTO subject (subjectName, teacherId) VALUES (?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, sub.getSubjectName());
            ps.setInt(2, sub.getTeacherId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL ERROR: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // ✅ Retrieve all subjects with their teacher IDs (optional: join to get teacher names)
    public static List<subject> getAllSubjects() {
        List<subject> subjectList = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM subject ORDER BY subjectName";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                subject sub = new subject();
                sub.setSubjectId(rs.getInt("subjectId"));
                sub.setSubjectName(rs.getString("subjectName"));
                sub.setTeacherId(rs.getInt("teacherId"));

                subjectList.add(sub);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return subjectList;
    }
}

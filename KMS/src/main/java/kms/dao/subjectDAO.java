package kms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kms.connection.ConnectionManager;
import kms.model.subject;

public class subjectDAO {
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	// Add a new subject
	public static void addSubject(subject sub) {
		try {
			// Get DB connection
			con = ConnectionManager.getConnection();

			// SQL insert
			sql = "INSERT INTO subject(subjectName, teacherId) VALUES (?, ?)";
			ps = con.prepareStatement(sql);

			// Set parameters
			ps.setString(1, sub.getSubjectName());
			ps.setInt(2, sub.getTeacherId());

			// Execute
			ps.executeUpdate();

			// Close connection
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Get all subjects
	public static List<subject> getAllSubjects() {
		List<subject> subs = new ArrayList<subject>();
		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT * FROM subject";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				subject sub = new subject();
				sub.setSubjectId(rs.getInt("subjectId"));
				sub.setSubjectName(rs.getString("subjectName"));
				sub.setTeacherId(rs.getInt("teacherId"));
				subs.add(sub);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subs;
	}

	// Get subject by ID
	public static subject getSubject(int subjectId) {
		subject sub = new subject();
		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT * FROM subject WHERE subjectId = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, subjectId);
			rs = ps.executeQuery();

			if (rs.next()) {
				sub.setSubjectId(rs.getInt("subjectId"));
				sub.setSubjectName(rs.getString("subjectName"));
				sub.setTeacherId(rs.getInt("teacherId"));
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sub;
	}

	// Delete subject by ID
	public static void deleteSubject(int subjectId) {
		try {
			con = ConnectionManager.getConnection();
			sql = "DELETE FROM subject WHERE subjectId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, subjectId);
			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

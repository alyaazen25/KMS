package kms.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kms.connection.ConnectionManager;
import kms.model.student;
import kms.model.subject;

public class studentDAO {

    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static String sql;

    // Insert student
    public static void addStudent(student stud) {
        try {
            con = ConnectionManager.getConnection();
            sql = "INSERT INTO student(studName, studAge, studGender, studAddress, studBirthDate, parentId, studPhoto, studBirthCert) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, stud.getStudName());
            ps.setInt(2, stud.getStudAge());
            ps.setString(3, stud.getStudGender());
            ps.setString(4, stud.getStudAddress());
            ps.setDate(5, stud.getStudBirthDate());
            ps.setInt(6, stud.getParentId());
            ps.setBytes(7, stud.getStudPhoto());
            ps.setBytes(8, stud.getStudBirthCert());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update student
    public static void updateStudent(student stud) {
        try {
            con = ConnectionManager.getConnection();
            if (stud.getStudPhoto() != null && stud.getStudBirthCert() != null) {
                sql = "UPDATE student SET studName=?, studAge=?, studGender=?, studAddress=?, studBirthDate=?, parentId=?, studPhoto=?, studBirthCert=? WHERE studId=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, stud.getStudName());
                ps.setInt(2, stud.getStudAge());
                ps.setString(3, stud.getStudGender());
                ps.setString(4, stud.getStudAddress());
                ps.setDate(5, stud.getStudBirthDate());
                ps.setInt(6, stud.getParentId());
                ps.setBytes(7, stud.getStudPhoto());
                ps.setBytes(8, stud.getStudBirthCert());
                ps.setInt(9, stud.getStudId());
            } else {
                sql = "UPDATE student SET studName=?, studAge=?, studGender=?, studAddress=?, studBirthDate=?, parentId=? WHERE studId=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, stud.getStudName());
                ps.setInt(2, stud.getStudAge());
                ps.setString(3, stud.getStudGender());
                ps.setString(4, stud.getStudAddress());
                ps.setDate(5, stud.getStudBirthDate());
                ps.setInt(6, stud.getParentId());
                ps.setInt(7, stud.getStudId());
            }
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete student
    public static void deleteStudent(int id) {
        try {
            con = ConnectionManager.getConnection();
            sql = "DELETE FROM student WHERE studId=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get student by ID
    public static student getStudent(int studId) {
        student stud = new student();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM student WHERE studId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, studId);
            rs = ps.executeQuery();
            if (rs.next()) {
                stud.setStudId(rs.getInt("studId"));
                stud.setStudName(rs.getString("studName"));
                stud.setStudAge(rs.getInt("studAge"));
                stud.setStudGender(rs.getString("studGender"));
                stud.setStudAddress(rs.getString("studAddress"));
                stud.setStudBirthDate(rs.getDate("studBirthDate"));
                stud.setParentId(rs.getInt("parentId"));
                Blob blobPhoto = rs.getBlob("studPhoto");
                if (blobPhoto != null) {
                    stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
                }
                Blob blobBirthCert = rs.getBlob("studBirthCert");
                if (blobBirthCert != null) {
                    stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stud;
    }

    // Get all students
    public static List<student> getAllStudents() {
        List<student> students = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM student";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                student stud = new student();
                stud.setStudId(rs.getInt("studId"));
                stud.setStudName(rs.getString("studName"));
                stud.setStudAge(rs.getInt("studAge"));
                stud.setStudGender(rs.getString("studGender"));
                stud.setStudAddress(rs.getString("studAddress"));
                stud.setStudBirthDate(rs.getDate("studBirthDate"));
                stud.setParentId(rs.getInt("parentId"));
                Blob blobPhoto = rs.getBlob("studPhoto");
                if (blobPhoto != null) {
                    stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
                }
                Blob blobBirthCert = rs.getBlob("studBirthCert");
                if (blobBirthCert != null) {
                    stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
                }
                students.add(stud);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Get students by parentId
    public static List<student> getStudentsByParentId(int parentId) {
        List<student> students = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM student WHERE parentId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, parentId);
            rs = ps.executeQuery();
            while (rs.next()) {
                student stud = new student();
                stud.setStudId(rs.getInt("studId"));
                stud.setStudName(rs.getString("studName"));
                stud.setStudAge(rs.getInt("studAge"));
                stud.setStudGender(rs.getString("studGender"));
                stud.setStudAddress(rs.getString("studAddress"));
                stud.setStudBirthDate(rs.getDate("studBirthDate"));
                stud.setParentId(rs.getInt("parentId"));
                Blob blobPhoto = rs.getBlob("studPhoto");
                if (blobPhoto != null) {
                    stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
                }
                Blob blobBirthCert = rs.getBlob("studBirthCert");
                if (blobBirthCert != null) {
                    stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
                }
                students.add(stud);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static student getStudentById(int studId) {
        student stud = null;
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM student WHERE studId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, studId);
            rs = ps.executeQuery();

            if (rs.next()) {
                stud = new student();
                stud.setStudId(rs.getInt("studId"));
                stud.setStudName(rs.getString("studName"));
                stud.setStudAge(rs.getInt("studAge"));
                stud.setStudGender(rs.getString("studGender"));
                stud.setStudAddress(rs.getString("studAddress"));
                stud.setStudBirthDate(rs.getDate("studBirthDate"));
                stud.setParentId(rs.getInt("parentId"));

                Blob blobPhoto = rs.getBlob("studPhoto");
                if (blobPhoto != null) {
                    stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
                }

                Blob blobBirthCert = rs.getBlob("studBirthCert");
                if (blobBirthCert != null) {
                    stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stud;
    }

} 

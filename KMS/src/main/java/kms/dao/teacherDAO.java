package kms.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kms.connection.ConnectionManager;
import kms.model.*;

public class teacherDAO {
	
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;
	
	//insert student
	public static void addTeacher(teacher teach){
		try {
			//call getConnection() method
			con = ConnectionManager.getConnection();
			
			//3. create statement
			sql = "INSERT INTO Teacher( teacherName, teacherEmail, teacherPass, teacherPhone, teacherRole, teacherType, adminId) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			
			//get values from student object and set parameter values
			ps.setString(1, teach.getTeacherName());
			ps.setString(2, teach.getTeacherEmail());
			ps.setString(3, teach.getTeacherPass());
			ps.setString(4, teach.getTeacherPhone());
			ps.setString(5, teach.getTeacherRole());
			ps.setString(6, teach.getTeacherType());
			ps.setInt(7, teach.getAdminId());


			
			//4. execute query
			ps.executeUpdate();
			
			// Also insert into FullTime or PartTime table
			if ("fullTime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof fullTime) {
				fullTime ft = (fullTime) teach;
				sql = "INSERT INTO fullTime(teacherId, salary) VALUES (?, ?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1, ft.getTeacherId());
				ps.setDouble(2, ft.getSalary());
				
				ps.executeUpdate();
				
			} else if ("parttime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof partTime) {
				partTime pt = (partTime) teach;
				sql = "INSERT INTO partTime(teacherId, contract) VALUES (?, ?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1, pt.getTeacherId());
				ps.setInt(2, pt.getContract());
				ps.executeUpdate();
			}
			//5. close connection
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
		//update student by id
		public static void updateTeacher(teacher teach){
			//complete the code here
			try {			
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement
				sql = "UPDATE teacher SET teacherName=?,teacherEmail=?,teacherPass=?,teacherPhone=?,  teacherRole=?, teacherType=?, adminId=?  WHERE teacherId=?";
				ps = con.prepareStatement(sql);

				//get values from teacher object and set parameter values
				ps.setString(1, teach.getTeacherName());
				ps.setString(2, teach.getTeacherEmail());
				ps.setString(3, teach.getTeacherPass());
				ps.setString(4, teach.getTeacherPhone());
				ps.setString(5, teach.getTeacherRole());
				ps.setString(6, teach.getTeacherType());
				ps.setInt(7, teach.getAdminId());

				//4. execute query
				ps.executeUpdate();

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}
		}


		//delete shawl
		public static void deleteTeacher(int teacherId){
			//complete the code here
			try {			
				//call getConnection() method
				con = ConnectionManager.getConnection();
				
				// Delete from subclass first
				String deleteFullTime = "DELETE FROM fullTime WHERE teacherId=?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, teacherId);
				ps.executeUpdate();

				String deletePartTime = "DELETE FROM partTime WHERE teacherId=?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, teacherId);
				ps.executeUpdate();

				//3. create statement
				String sql = "DELETE FROM teacher WHERE teacherId=?";
				ps = con.prepareStatement(sql);

				//set parameter value
				ps.setInt(1, teacherId);

				//4. execute query
				ps.executeUpdate();

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}	

	   }
		
		//get student by id
		public static teacher getTeacher(int teacherId) {
			teacher teach = new teacher();
			try {
				con = ConnectionManager.getConnection();
				sql = "SELECT * FROM teacher WHERE teacherId = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, teacherId);
				rs = ps.executeQuery();

				if (rs.next()) {
					
					String teacherType = rs.getString("type");

					if ("fulltime".equalsIgnoreCase(teacherType)) {
						teach = new fullTime();
						sql = "SELECT * FROM fullTime WHERE teacherId=?";
						PreparedStatement ps2 = con.prepareStatement(sql);
						ps2.setInt(1, teacherId);
						ResultSet rs2 = ps2.executeQuery();
						if (rs2.next()) {
							((fullTime) teach).setSalary(rs2.getDouble("salary"));
						}
					} else if ("parttime".equalsIgnoreCase(teacherType)) {
						teach = new partTime();
						sql = "SELECT * FROM partTime WHERE teacherId=?";
						PreparedStatement ps2 = con.prepareStatement(sql);
						ps2.setInt(1, teacherId);
						ResultSet rs2 = ps2.executeQuery();
						if (rs2.next()) {
							((partTime) teach).setContract(rs2.getInt("contract"));
						}
					} else {
						teach = new teacher();
					}
					
					ps.setString(1, teach.getTeacherName());
					ps.setString(2, teach.getTeacherEmail());
					ps.setString(3, teach.getTeacherPass());
					ps.setString(4, teach.getTeacherPhone());
					ps.setString(5, teach.getTeacherRole());
					ps.setString(6, teach.getTeacherType());
					ps.setInt(7, teach.getAdminId());
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return teach;
		}
		
		//get all student
		public static List<teacher> getAllTeacher(){
			
			List<teacher> teachers = new ArrayList<teacher>();
			//complete the code here
			try {
				//call getConnection() method
				con = ConnectionManager.getConnection();

				//3. create statement 
				sql = "SELECT * FROM teacher ORDER BY teacherId";
				ps = con.prepareStatement(sql);

				//4. execute query
				rs = ps.executeQuery();

				//process ResultSet
				while(rs.next()) {	
					
					String teacherType = rs.getString("teacherType");
					teacher teach = new teacher();
					
					if ("fulltime".equalsIgnoreCase(teacherType)) {
						
						teach = new fullTime();
						
						PreparedStatement ps2 = con.prepareStatement("SELECT salary FROM fullTime WHERE teacherId=?");
						ps2.setInt(1, rs.getInt("teacherId"));
						ResultSet rs2 = ps2.executeQuery();
						
						if (rs2.next()) {
							((fullTime) teach).setSalary(rs2.getDouble("salary"));
						}
						
					    }
					    else if ("parttime".equalsIgnoreCase(teacherType)) {
					    	
						teach = new partTime();
						
						PreparedStatement ps2 = con.prepareStatement("SELECT contract FROM partTime WHERE teacherId=?");
						ps2.setInt(1, rs.getInt("teacherId"));
						ResultSet rs2 = ps2.executeQuery();
						
						if (rs2.next()) {
							((partTime) teach).setContract(rs2.getInt("contract"));
						}
					    }
					    else {
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

				//5. close connection
				con.close();

			}catch(SQLException e) {
				e.printStackTrace();
			}

			return teachers;
		}	
}
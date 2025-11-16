package src.dao;

import src.DatabaseConnection;
import src.models.CriminalRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriminalDAO {
    
    // Add new criminal record
    public boolean addCriminalRecord(CriminalRecord record) {
        String sql = "INSERT INTO criminal_records (name, age, crime_type, crime_date, address, phone, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, record.getName());
            pstmt.setInt(2, record.getAge());
            pstmt.setString(3, record.getCrimeType());
            pstmt.setDate(4, new java.sql.Date(record.getCrimeDate().getTime()));
            pstmt.setString(5, record.getAddress());
            pstmt.setString(6, record.getPhone());
            pstmt.setString(7, record.getStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
            return false;
        }
    }
    
    // Get all criminal records
    public List<CriminalRecord> getAllRecords() {
        List<CriminalRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM criminal_records ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                CriminalRecord record = new CriminalRecord();
                record.setId(rs.getInt("id"));
                record.setName(rs.getString("name"));
                record.setAge(rs.getInt("age"));
                record.setCrimeType(rs.getString("crime_type"));
                record.setCrimeDate(rs.getDate("crime_date"));
                record.setAddress(rs.getString("address"));
                record.setPhone(rs.getString("phone"));
                record.setStatus(rs.getString("status"));
                
                records.add(record);
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
        
        return records;
    }
    
    // Search records by name
    public List<CriminalRecord> searchByName(String name) {
        List<CriminalRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM criminal_records WHERE name LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                CriminalRecord record = new CriminalRecord();
                record.setId(rs.getInt("id"));
                record.setName(rs.getString("name"));
                record.setAge(rs.getInt("age"));
                record.setCrimeType(rs.getString("crime_type"));
                record.setCrimeDate(rs.getDate("crime_date"));
                record.setAddress(rs.getString("address"));
                record.setPhone(rs.getString("phone"));
                record.setStatus(rs.getString("status"));
                
                records.add(record);
            }
            
        } catch (SQLException e) {
            System.out.println("Error searching records: " + e.getMessage());
        }
        
        return records;
    }
    
    // Update criminal record
    public boolean updateRecord(CriminalRecord record) {
        String sql = "UPDATE criminal_records SET name=?, age=?, crime_type=?, crime_date=?, address=?, phone=?, status=? WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, record.getName());
            pstmt.setInt(2, record.getAge());
            pstmt.setString(3, record.getCrimeType());
            pstmt.setDate(4, new java.sql.Date(record.getCrimeDate().getTime()));
            pstmt.setString(5, record.getAddress());
            pstmt.setString(6, record.getPhone());
            pstmt.setString(7, record.getStatus());
            pstmt.setInt(8, record.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
            return false;
        }
    }
    
    // Delete criminal record
    public boolean deleteRecord(int id) {
        String sql = "DELETE FROM criminal_records WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
            return false;
        }
    }
}
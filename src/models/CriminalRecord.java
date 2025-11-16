package src.models;

import java.util.Date;

public class CriminalRecord {
    private int id;
    private String name;
    private int age;
    private String crimeType;
    private Date crimeDate;
    private String address;
    private String phone;
    private String status;
    
    public CriminalRecord() {}
    
    public CriminalRecord(String name, int age, String crimeType, Date crimeDate, 
                         String address, String phone, String status) {
        this.name = name;
        this.age = age;
        this.crimeType = crimeType;
        this.crimeDate = crimeDate;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getCrimeType() { return crimeType; }
    public void setCrimeType(String crimeType) { this.crimeType = crimeType; }
    
    public Date getCrimeDate() { return crimeDate; }
    public void setCrimeDate(Date crimeDate) { this.crimeDate = crimeDate; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
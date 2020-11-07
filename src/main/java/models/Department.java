package models;

import dao.Sql2oUserDao;

import java.util.Objects;

public class Department {

    Sql2oUserDao userDao;
    Department department;

    private int id;
    private String departmentName;
    private String description;
    private int totalUsers;

    public Department(String departmentName, String description){
        this.departmentName = departmentName;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return getId() == that.getId() &&
                totalUsers == that.totalUsers &&
                Objects.equals(getDepartmentName(), that.getDepartmentName()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDepartmentName(), getDescription(), totalUsers);
    }

    //    public int getTotalEmployees() { return totalUsers; }
//    public void setTotalEmployees(int totalEmployees) { this.totalUsers = totalEmployees; }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Department that = (Department) o;
//        return getId() == that.getId() &&
//                totalUsers == that.totalUsers &&
//                Objects.equals(getDepartmentName(), that.getDepartmentName()) &&
//                Objects.equals(getDescription(), that.getDescription());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getDepartmentName(), getDescription(), totalUsers);
//    }
}

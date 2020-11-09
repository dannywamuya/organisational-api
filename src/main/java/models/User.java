package models;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;

public class User {

    private int id;
    private String fullName;
    private String position;
    private String role;
    private int departmentId;

    public User(String fullName, String position,  String role, int departmentId) {
        this.fullName = fullName;
        this.position = position;
        this.role = role;
        this.departmentId = departmentId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                getDepartmentId() == user.getDepartmentId() &&
                Objects.equals(getFullName(), user.getFullName()) &&
                Objects.equals(getPosition(), user.getPosition()) &&
                Objects.equals(getRole(), user.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullName(), getPosition(), getRole(), getDepartmentId());
    }
}

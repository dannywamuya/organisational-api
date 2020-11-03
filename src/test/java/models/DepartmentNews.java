package models;

import java.util.Objects;

public class DepartmentNews extends News {
    private static final String TYPE = "Department";
    public int departmentId;

    public DepartmentNews(String title, String content, int userId, int departmentId){
        this.title = title;
        this.content = content;
        this.newsType = TYPE;
        this.userId = userId;
        this.departmentId = departmentId;
    }

    public static String getTYPE() { return TYPE; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DepartmentNews that = (DepartmentNews) o;
        return getDepartmentId() == that.getDepartmentId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDepartmentId());
    }
}

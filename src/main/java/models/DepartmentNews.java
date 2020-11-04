package models;

public class DepartmentNews extends News {
    private static final String TYPE = "Department";

    public DepartmentNews(String title, String content, int userId, int departmentId){
        this.title = title;
        this.content = content;
        this.newsType = TYPE;
        this.userId = userId;
        this.departmentId = departmentId;
    }

    public static String getTYPE() { return TYPE; }

}

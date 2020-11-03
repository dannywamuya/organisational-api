package models;

public class GeneralNews extends News{
    private static final String TYPE = "General";

    public GeneralNews(String title, String content, int userId){
        this.title = title;
        this.content = content;
        this.newsType = TYPE;
        this.userId = userId;
    }

    public static String getTYPE() { return TYPE; }
}

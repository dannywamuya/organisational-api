package models;

import java.util.Objects;

public class News {

    public int id;
    public String title;
    public String content;
    public String newsType;
    public int userId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getNewsType() { return newsType; }
    public void setNewsType(String newsType) { this.newsType = newsType; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return getId() == news.getId() &&
                getUserId() == news.getUserId() &&
                Objects.equals(getTitle(), news.getTitle()) &&
                Objects.equals(getContent(), news.getContent()) &&
                Objects.equals(getNewsType(), news.getNewsType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent(), getNewsType(), getUserId());
    }
}

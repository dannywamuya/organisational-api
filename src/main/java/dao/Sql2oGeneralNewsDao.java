package dao;

import models.GeneralNews;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oGeneralNewsDao implements GeneralNewsDao{

    private final Sql2o sql2o;
    public Sql2oGeneralNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(GeneralNews generalNews) {
        String sql = "INSERT INTO news (title, content, newsType, userId, departmentId) VALUES (:title, :content, 'General', :userId, null)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(generalNews)
                    .executeUpdate()
                    .getKey();
            generalNews.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<GeneralNews> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE newsType = 'General'")
                    .executeAndFetch(GeneralNews.class);
        }
    }

    @Override
    public GeneralNews findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE id = :id AND newsType = 'General'")
                    .addParameter("id", id)
                    .executeAndFetchFirst(GeneralNews.class);
        }
    }

    @Override
    public List<GeneralNews> findByUser(int userId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE userId = :userId AND newsType = 'General'")
                    .addParameter("userId", userId)
                    .executeAndFetch(GeneralNews.class);
        }
    }

    @Override
    public void update(int id, String title, String content, int userId) {
        String sql = "UPDATE news SET (title, content) = (:title, :content) WHERE id = :id AND id = :userId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("userId", userId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id, int userId) {
        String sql = "DELETE from news WHERE id = :id AND userId = :userId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("userId", userId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE from news";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}

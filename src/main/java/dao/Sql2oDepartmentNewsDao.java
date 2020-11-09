package dao;

import models.Department;
import models.DepartmentNews;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDepartmentNewsDao implements DepartmentNewsDao {

    private final Sql2o sql2o;
    public Sql2oDepartmentNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(DepartmentNews departmentNews) {
        String sql = "INSERT INTO news (title, content, newsType, userId, departmentId) VALUES (:title, :content, 'Department', :userId, :departmentId)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(departmentNews)
                    .executeUpdate()
                    .getKey();
            departmentNews.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<DepartmentNews> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE newsType = 'Department'")
                    .executeAndFetch(DepartmentNews.class);
        }
    }

    @Override
    public DepartmentNews findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(DepartmentNews.class);
        }
    }

    @Override
    public List<DepartmentNews> findByUser(int userId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE userId = :userId AND newsType = 'Department'")
                    .addParameter("userId", userId)
                    .executeAndFetch(DepartmentNews.class);
        }
    }

    @Override
    public List<DepartmentNews> findByDepartment(int departmentId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE departmentId = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(DepartmentNews.class);
        }
    }

    @Override
    public void update(int id, String title, String content, int userId, int departmentId) {
        String sql = "UPDATE news SET (title, content) = (:title, :content) WHERE id = :id AND id = :userId AND departmentId = :departmentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("userId", userId)
                    .addParameter("departmentId", departmentId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id, int userId, int departmentId) {
        String sql = "DELETE from news WHERE id = :id AND userId = :userId AND departmentId = :departmentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("userId", userId)
                    .addParameter("departmentId", departmentId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAllDepartmentNews(int departmentId) {
        String sql = "DELETE from news WHERE departmentId = :departmentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", departmentId)
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

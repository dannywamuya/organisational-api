package dao;

import models.Department;
import models.DepartmentNews;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDepartmentNewsDaoTest {

    private static Connection conn;
    private static Sql2oDepartmentNewsDao departmentNewsDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "danny", "password");
        departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingNewsSetsId() throws Exception {
        DepartmentNews news = createNews();
        int originalNewsId = news.getId();
        departmentNewsDao.add(news);

        assertNotEquals(originalNewsId, news.getId());
        assertEquals(1, departmentNewsDao.getAll().get(0).getId());
    }

    @Test
    public void addedNewsIsReturnedFromGetAll() throws Exception {
        DepartmentNews news = createNews();
        departmentNewsDao.add(news);
        DepartmentNews otherNews = createOtherNews();

        assertEquals(1, departmentNewsDao.getAll().size());
        assertEquals("Have you Heard?", departmentNewsDao.getAll().get(0).getTitle());
        assertEquals(news, departmentNewsDao.getAll().get(0));
    }

    @Test
    public void noNewsReturnsEmptyList() throws Exception {
        DepartmentNews news = createNews();
        int departmentId = news.getDepartmentId();

        assertEquals(0, departmentNewsDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectNews() throws Exception {
        DepartmentNews news = createNews();
        departmentNewsDao.add(news);
        DepartmentNews otherNews = createOtherNews();
        departmentNewsDao.add(otherNews);

        assertEquals(news, departmentNewsDao.findById(news.getId()));
        assertEquals("New Coffee Machines", departmentNewsDao.findById(otherNews.getId()).getTitle());
    }

    @Test
    public void findByUserReturnsCorrectNews() throws Exception {
        DepartmentNews news = createNews();
        departmentNewsDao.add(news);
        DepartmentNews otherNews = createOtherNews();
        departmentNewsDao.add(otherNews);

        assertEquals(news, departmentNewsDao.findByUser(1).get(0));
        assertEquals("New Coffee Machines", departmentNewsDao.findByUser(2).get(0).getTitle());
    }

    @Test
    public void findByDepartmentReturnsCorrectNews() throws Exception {
        DepartmentNews news = createNews();
        departmentNewsDao.add(news);
        DepartmentNews otherNews = createOtherNews();
        departmentNewsDao.add(otherNews);

        assertEquals(news, departmentNewsDao.findByDepartment(1).get(0));
        assertEquals("New Coffee Machines", departmentNewsDao.findByDepartment(2).get(0).getTitle());
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        DepartmentNews news = createNews();
        departmentNewsDao.add(news);
        DepartmentNews otherNews = createOtherNews();
        departmentNewsDao.add(otherNews);

        int newsId = news.getId();
        int departmentId = news.getDepartmentId();
        int userId = news.getUserId();

        departmentNewsDao.update(newsId,"New Coffee Machines", "It is rumored that the company is buying new coffee machines. Hurrah!", userId, departmentId);

        DepartmentNews foundNews = departmentNewsDao.findById(newsId);

        assertEquals("New Coffee Machines", foundNews.getTitle());
        assertEquals(1, news.getUserId());
        assertEquals(newsId, foundNews.getId());
        assertEquals(departmentId, foundNews.getDepartmentId());
    }

    @Test
    public void deleteByIdDeletesCorrectNews() throws Exception {
        DepartmentNews news = createNews();
        departmentNewsDao.add(news);
        DepartmentNews otherNews = createOtherNews();
        departmentNewsDao.add(otherNews);
        departmentNewsDao.deleteById(news.getId(), news.getUserId(), news.getDepartmentId());

        assertEquals(1, departmentNewsDao.findByDepartment(otherNews.getDepartmentId()).size());
        assertEquals(0, departmentNewsDao.findByDepartment(news.getDepartmentId()).size());
    }

    @Test
    public void deleteAllDeletesAllNews() throws Exception {
        DepartmentNews news = createNews();
        departmentNewsDao.add(news);
        int departmentId = news.getDepartmentId();
        DepartmentNews otherNews = createOtherNews();
        departmentNewsDao.add(otherNews);
        DepartmentNews thirdNews = new DepartmentNews("New Manager", "John Doe has been promoted to new Manager", 1, 1);
        departmentNewsDao.add(thirdNews);
        departmentNewsDao.deleteAllDepartmentNews(departmentId);

        assertEquals(0, departmentNewsDao.findByDepartment(departmentId).size());
        assertEquals(1, departmentNewsDao.findByDepartment(otherNews.getDepartmentId()).size());

    }



    public DepartmentNews createNews(){
        return new DepartmentNews("Have you Heard?", "The company is transitioning towards a digital model!",1, 1);
    }

    public DepartmentNews createOtherNews(){
        return new DepartmentNews("New Coffee Machines", "It is rumored that the company is buying new coffee machines. Hurrah!",2, 2);
    }

}
package dao;

import models.GeneralNews;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oGeneralNewsDaoTest {

    private static Connection conn;
    private static Sql2oGeneralNewsDao generalNewsDao;
    private static Sql2oDepartmentNewsDao departmentNewsDao;


    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/orgapitest";
        Sql2o sql2o = new Sql2o(connectionString, "danny", "password");
        generalNewsDao = new Sql2oGeneralNewsDao(sql2o);
        departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentNewsDao.deleteAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingNewsSetsId() throws Exception {
        GeneralNews news = createNews();
        int originalNewsId = news.getId();
        generalNewsDao.add(news);

        assertNotEquals(originalNewsId, news.getId());
    }

    @Test
    public void addedNewsIsReturnedFromGetAll() throws Exception {
        GeneralNews news = createNews();
        generalNewsDao.add(news);

        assertEquals(1, generalNewsDao.getAll().size());
        assertEquals(news, generalNewsDao.getAll().get(0));
    }

    @Test
    public void noNewsReturnsEmptyList() throws Exception {
        assertEquals(0, generalNewsDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectNews() throws Exception {
        GeneralNews news = createNews();
        generalNewsDao.add(news);
        GeneralNews otherNews = createOtherNews();
        generalNewsDao.add(otherNews);

        assertEquals(news, generalNewsDao.findById(news.getId()));
        assertEquals("New Coffee Machines", generalNewsDao.findById(otherNews.getId()).getTitle());
    }

    @Test
    public void findByUserReturnsCorrectNews() throws Exception {
        GeneralNews news = createNews();
        generalNewsDao.add(news);
        GeneralNews otherNews = createOtherNews();
        generalNewsDao.add(otherNews);

        assertEquals(news, generalNewsDao.findByUser(1).get(0));
        assertEquals("New Coffee Machines", generalNewsDao.findByUser(2).get(0).getTitle());
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        GeneralNews news = createNews();
        generalNewsDao.add(news);
        int newsId = news.getId();
        GeneralNews otherNews = createOtherNews();
        generalNewsDao.add(otherNews);
        generalNewsDao.update(newsId,"New Coffee Machines", "It is rumored that the company is buying new coffee machines. Hurrah!", news.getUserId());

        GeneralNews foundNews = generalNewsDao.findById(news.getId());

        assertEquals(1, news.getUserId());
        assertEquals(newsId, foundNews.getId());
    }

    @Test
    public void deleteByIdDeletesCorrectNews() throws Exception {
        GeneralNews news = createNews();
        generalNewsDao.add(news);
        GeneralNews otherNews = createOtherNews();
        generalNewsDao.add(otherNews);
        generalNewsDao.deleteById(news.getId(), news.getUserId());

        assertEquals(1, generalNewsDao.getAll().size());
    }

    @Test
    public void deleteAllDeletesAllNews() throws Exception {
        GeneralNews news = createNews();
        generalNewsDao.add(news);
        GeneralNews otherNews = createOtherNews();
        generalNewsDao.add(otherNews);
        generalNewsDao.deleteAll();

        assertEquals(0, generalNewsDao.getAll().size());
    }



    public GeneralNews createNews(){
        return new GeneralNews("Have you Heard?", "The company is transitioning towards a digital model!",1);
    }

    public GeneralNews createOtherNews(){
        return new GeneralNews("New Coffee Machines", "It is rumored that the company is buying new coffee machines. Hurrah!",2);
    }

}
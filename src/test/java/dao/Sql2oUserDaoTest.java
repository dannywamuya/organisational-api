package dao;

import models.User;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUserDaoTest {

    private static Connection conn;
    private static Sql2oUserDao userDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/orgapitest";
        Sql2o sql2o = new Sql2o(connectionString, "danny", "password");
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        userDao.deleteAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingUserSetsId() throws Exception {
        User user = createNewUser();
        int originalUserId = user.getId();
        userDao.add(user);

        assertNotEquals(originalUserId, user.getId());
    }

    @Test
    public void addedUsersAreReturnedFromGetAll() throws Exception {
        User user = createNewUser();
        userDao.add(user);

        assertEquals(1, userDao.getAll().size());
    }

    @Test
    public void noUsersReturnsEmptyList() throws Exception {
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectUser() throws Exception {
        User user = createNewUser();
        userDao.add(user);
        User otherUser = createOtherUser();
        userDao.add(otherUser);

        assertEquals(user, userDao.findById(user.getId()));
        assertEquals("Account for company expenditure", userDao.findById(otherUser.getId()).getRole());
    }

    @Test
    public void findByDepartmentReturnsCorrectUser() throws Exception {
        User user = createNewUser();
        userDao.add(user);
        User otherUser = createOtherUser();
        userDao.add(otherUser);

        assertEquals(user, userDao.findByDepartment(user.getDepartmentId()));
    }

    @Test
    public void findAllByDepartmentReturnsAllInDepartment() throws Exception {
        User user = new User("John Doe", "Systems Manager","Configure Network Systems", 1);
        userDao.add(user);
        User otherUser = new User("Mary Jane", "Accountant", "Account for company expenditure", 1);
        userDao.add(otherUser);
        User thirdUser = new User("James May", "Software Developer", "Develop computer software", 2);
        userDao.add(thirdUser);

        assertEquals(2, userDao.findAllByDepartment(user.getDepartmentId()).size());
        assertEquals("James May", userDao.findAllByDepartment(thirdUser.getDepartmentId()).get(0).getFullName());
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        User user = createNewUser();
        userDao.add(user);
        int userId = user.getId();
        userDao.update(userId, "James May", "Software Developer", "Develop computer software", 2);
        User foundUser = userDao.findById(user.getId());

        assertEquals("James May", foundUser.getFullName());
        assertEquals("Software Developer", foundUser.getPosition());
        assertEquals(userId, foundUser.getId());
    }

    @Test
    public void deleteByIdDeletesCorrectUser() throws Exception {
        User user = createNewUser();
        userDao.add(user);
        User otherUser = createOtherUser();
        userDao.add(otherUser);
        userDao.deleteById(user.getId());

        assertEquals(1, userDao.getAll().size());
    }

    @Test
    public void deleteAllDeletesAllUsers() throws Exception {
        User user = createNewUser();
        userDao.add(user);
        User otherUser = createOtherUser();
        userDao.add(otherUser);
        userDao.deleteAll();

        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void deleteByDepartmentDeletesAllUsersInADepartment() throws Exception{
        User user = new User("John Doe", "Systems Manager","Configure Network Systems", 1);
        userDao.add(user);
        User otherUser = new User("Mary Jane", "Accountant", "Account for company expenditure", 1);
        userDao.add(otherUser);
        User thirdUser = new User("James May", "Software Developer", "Develop computer software", 2);
        userDao.add(thirdUser);
        userDao.deleteAllByDepartment(1);

        assertEquals(1, userDao.getAll().size());
    }



    public User createNewUser(){
        return new User("John Doe", "Systems Manager","Configure Network Systems",1);
    }
    public User createOtherUser() {
        return new User("Mary Jane", "Accountant", "Account for company expenditure", 2);
    }
}
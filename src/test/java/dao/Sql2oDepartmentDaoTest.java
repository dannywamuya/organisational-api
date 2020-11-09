package dao;

import models.Department;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "danny", "password");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingDepartmentSetsId() throws Exception {
        Department department = createNewDepartment();
        int originalDepartmentId = department.getId();
        departmentDao.add(department);

        assertNotEquals(originalDepartmentId, department.getId());
        assertEquals(1, departmentDao.getAll().get(0).getId());
    }

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
        Department department = createNewDepartment();
        departmentDao.add(department);

        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception {
        Department department = createNewDepartment();
        departmentDao.add(department);
        Department otherDepartment = createOtherDepartment();
        departmentDao.add(otherDepartment);

        assertEquals(department, departmentDao.findById(department.getId()));
        assertEquals("Keeps financial records", departmentDao.findById(otherDepartment.getId()).getDescription());
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        Department department = createNewDepartment();
        departmentDao.add(department);
        int departmentId = department.getId();
        departmentDao.update(department.getId(), "HR", "Hiring and Firing!");
        Department foundDepartment = departmentDao.findById(department.getId());

        assertEquals("HR", foundDepartment.getDepartmentName());
        assertEquals("Hiring and Firing!", foundDepartment.getDescription());
        assertEquals(departmentId, foundDepartment.getId());
    }

    @Test
    public void deleteByIdDeletesCorrectDepartment() throws Exception {
        Department department = createNewDepartment();
        departmentDao.add(department);
        Department otherDepartment = createOtherDepartment();
        departmentDao.add(otherDepartment);
        departmentDao.deleteById(department.getId());

        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void deleteAllDeletesAllDepartment() throws Exception {
        Department department = createNewDepartment();
        departmentDao.add(department);
        Department otherDepartment = createOtherDepartment();
        departmentDao.add(otherDepartment);
        departmentDao.deleteAll();

        assertEquals(0, departmentDao.getAll().size());
    }

    public Department createNewDepartment(){
        return new Department("IT", "Sets up and configures the networks");
    }
    public Department createOtherDepartment(){
        return new Department("Finance", "Keeps financial records");
    }

}
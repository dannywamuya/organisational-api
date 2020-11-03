package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Test
    public void NewDepartmentObjectIsCreatedCorrectly_true(){
        Department department = createNewDepartment();
        assertTrue(department instanceof Department);
    }

    @Test
    public void NewDepartmentObjectIsCreatedWithName_true(){
        Department department = createNewDepartment();
        assertEquals("IT", department.getDepartmentName());
    }

    @Test
    public void NewDepartmentObjectIsCreatedWithDescription_true(){
        Department department = createNewDepartment();
        assertEquals("Sets up and configures the networks", department.getDescription());
    }

    public Department createNewDepartment(){
        return new Department("IT", "Sets up and configures the networks");
    }

}
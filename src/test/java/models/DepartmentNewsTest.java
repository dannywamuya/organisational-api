package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentNewsTest {

    @Test
    public void NewNewsObjectIsCreatedCorrectly_true(){
        DepartmentNews departmentNews = createNews();
        assertTrue(departmentNews instanceof DepartmentNews);
    }

    @Test
    public void NewsObjectIsCreatedWithRightParameters_true(){
        DepartmentNews departmentNews = createNews();
        assertEquals("Have you Heard?", departmentNews.getTitle());
        assertEquals("The department is get reinstated with new tech!", departmentNews.getContent());
        assertEquals(1, departmentNews.getUserId());
        assertEquals(1, departmentNews.getDepartmentId());
    }

    public DepartmentNews createNews(){
        return new DepartmentNews("Have you Heard?", "The department is get reinstated with new tech!",1, 1 );
    }

}
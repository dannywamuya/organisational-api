package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void NewUserObjectIsCreatedCorrectly_true(){
        User user = createNewUser();
        assertTrue(user instanceof User);
    }

    @Test
    public void NewUserObjectIsCreatedWithRightParameters_true(){
        User user = createNewUser();
        assertEquals("John Doe", user.getFullName());
        assertEquals("Systems Manager", user.getPosition());
        assertEquals("Configure Network Systems", user.getRole());
        assertEquals(1, user.getDepartmentId());
    }

    public User createNewUser(){
        return new User("John Doe", "Systems Manager","Configure Network Systems",1);
    }

}
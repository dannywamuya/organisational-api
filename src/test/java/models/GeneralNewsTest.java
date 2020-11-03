package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeneralNewsTest {

    @Test
    public void NewNewsObjectIsCreatedCorrectly_true(){
        GeneralNews generalNews = createNews();
        assertTrue(generalNews instanceof GeneralNews);
    }

    @Test
    public void NewsObjectIsCreatedWithRightParameters_true(){
        GeneralNews generalNews = createNews();
        assertEquals("Have you Heard?", generalNews.getTitle());
        assertEquals("The company is transitioning towards a digital model!", generalNews.getContent());
        assertEquals(1, generalNews.getUserId());
    }

    public GeneralNews createNews(){
        return new GeneralNews("Have you Heard?", "The company is transitioning towards a digital model!",1);
    }

}
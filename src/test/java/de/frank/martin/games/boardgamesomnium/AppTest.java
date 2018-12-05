package de.frank.martin.games.boardgamesomnium;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class AppTest {

    @Test
    public void appTest(){
        try {
            App app = new App();
        }catch (Exception e){
            fail();
        }
        Assert.assertTrue(true);
    }
}

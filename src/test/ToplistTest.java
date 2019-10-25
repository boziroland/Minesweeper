package test;

import aknakereso.Toplist;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ToplistTest {

    Toplist player;

    @Before
    public void setUp() throws Exception {
        player = new Toplist("Roland", 24);
    }

    @Test
    public void testGetName() {
        assertEquals("Roland",player.getName());
    }

    @Test
    public void testGetTime() {
        assertEquals(24,player.getTime());
    }
}
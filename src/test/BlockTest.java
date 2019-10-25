package test;

import aknakereso.Block;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlockTest {

    Block block;

    @Before
    public void setUp() throws Exception {
        block = new Block(1,3,true);
    }

    @Test
    public void testGetfX() {
        Assert.assertEquals(1,block.getfX());
    }

    @Test
    public void testGetfY() {
        Assert.assertEquals(3,block.getfY());

    }

    @Test
    public void testHasMine() {
        Assert.assertEquals(true,block.hasMine());
    }

    @Test
    public void testIsClicked() {
        Assert.assertEquals(false,block.isClicked());
    }

    @Test
    public void testSetClicked() {
        Assert.assertEquals(false,block.isClicked());
        block.setClicked(true);
        Assert.assertEquals(true,block.isClicked());

    }

    @Test
    public void testIsRightClicked() {
        Assert.assertEquals(false,block.isRightClicked());

    }

    @Test
    public void testSetRightClicked() {
        Assert.assertEquals(false,block.isRightClicked());
        block.setRightClicked(true);
        Assert.assertEquals(true,block.isRightClicked());

    }

    @Test
    public void testGetAdjacentMineCount() {
        Assert.assertEquals(0,block.getAdjacentMineCount());

    }
}
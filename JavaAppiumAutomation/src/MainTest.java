import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainTest extends CoreTestCase{
    @Before
    public void textStartTes(){
        System.out.println("start test");
    }

    @After
    public void textFinishTest() {
        System.out.println("finish test");
    }

    private int class_number = 20;
    private String class_string = "Hello, world";


    @Test
    public void testGetLocalNumber()
    {
        System.out.println("testGetLocalNumber ");
        int a = this.getLocalNumber();
        if (a==14)
        {
            System.out.println("The number is 14");
        } else
        {
            System.out.println("The number is  not 14");
        }
    }
    @Test
    public void testGetClassNumber()
    {
        System.out.println("testGetClassNumber ");
        class_number = this.getClassNumber();

        if (class_number>45)
        {
            System.out.println("The class_number is more than 45");
        } else
        {
            System.out.println("The class_number is less or equal than 45");
        }
    }
    @Test
    public void testGetClassString()
    {
        System.out.println("testGetClassString ");
        class_string = this.getClassString();
        boolean isContain = class_string.toLowerCase().contains("hello");
        Assert.assertTrue("the string doesn't contains \"hello\"",isContain);

    }

    public int getLocalNumber() {
        return 14;
    }
    public int getClassNumber() {
        return class_number;
    }
    public String getClassString() {
        return class_string;
    }
}

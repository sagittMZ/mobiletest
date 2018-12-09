import org.junit.Test;

public class MainTest extends CoreTestCase{

    private int class_number = 20;
    private String class_string = "Hello, world";

    public void typeStartMessage()
    {
    super.typeStartMessage();
    System.out.println("Current class is MainTest");
    }
    @Test
    public void testGetLocalNumber()
    {
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
        class_string = this.getClassString();
        boolean isContain = class_string.toLowerCase().contains("hello");
        if (isContain)
        {
            System.out.println("the string contains \"hello\"");
        } else
        {
            System.out.println("the string doesn't contains \"hello\"");
        }
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

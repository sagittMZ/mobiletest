import org.junit.Test;

public class MainTest { //extends CoreTestCase{
   // MathHelper Math = new MathHelper();
private int class_number;
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

    public int getLocalNumber() {
        return 14;
    }
    public int getClassNumber() {
        return 20;
    }
}

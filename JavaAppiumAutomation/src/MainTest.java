import org.junit.Test;

public class MainTest { //extends CoreTestCase{
   // MathHelper Math = new MathHelper();

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

    public int getLocalNumber() {
        return 14;
    }
}

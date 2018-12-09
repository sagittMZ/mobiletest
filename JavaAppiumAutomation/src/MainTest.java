import org.junit.Test;

public class MainTest extends CoreTestCase{
    MathHelper Math = new MathHelper();

    @Test
    public void myFirstTest()
    {
     int a = Math.multiply(5);  // call function
     int b= Math.multiply(5,15);
        System.out.println(a);
        System.out.println(b);

    }

}

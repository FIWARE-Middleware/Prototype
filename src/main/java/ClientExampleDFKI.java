
import com.kiara.Connection;
import com.kiara.Context;
import com.kiara.Kiara;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rubinste
 */
public class ClientExampleDFKI {
    public static void main(String argv[]) throws Exception {
        if(argv.length != 2)
        {
            System.out.println("Usage: ClientExample <integer1> <integer2>");
            return;
        }

        int param1 = Integer.parseInt(argv[0]);
        int param2 = Integer.parseInt(argv[1]);
        int ret = 0;

        Context context = Kiara.createContext();
        Connection connection = context.openConnection("tcp://127.0.0.1:8080");
        Calculator proxy = connection.getServiceInterface(Calculator.class);

        try
        {
            // Call 'add' method.
            ret = proxy.add(param1, param2);
            System.out.println("Result of adding: " + ret);

            // Call 'subtract' method.
            ret = proxy.subtract(param1, param2);
            System.out.println("Result of subtracting: " + ret);
        }
        catch(Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
            return;
        }
    }

}

import com.kiara.serialization.Serializer;
import com.kiara.serialization.Cdr;
import com.kiara.transport.*;

public class ClientExample
{
    public static void main(String argv[]) throws Exception
    {
        if(argv.length != 2)
        {
            System.out.println("Usage: ClientExample <integer1> <integer2>");
            return;
        }

        int param1 = Integer.parseInt(argv[0]);
        int param2 = Integer.parseInt(argv[1]);
        int ret = 0;

        // Create custom transport, serializer and the proxy.
        Transport transport = new TCPProxyTransport("127.0.0.1", 8080);
        Serializer ser = new Cdr();
        Calculator proxy = new CalculatorProxy(ser, transport);

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

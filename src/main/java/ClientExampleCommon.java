import com.kiara.client.Connection;
import com.kiara.Context;
import com.kiara.Kiara;
import com.kiara.serialization.Serializer;
import com.kiara.transport.Transport;

public class ClientExampleCommon {

    public static void main(String argv[]) throws Exception {
        if (argv.length != 2) {
            System.out.println("Usage: ClientExample <integer1> <integer2>");
            return;
        }

        int param1 = Integer.parseInt(argv[0]);
        int param2 = Integer.parseInt(argv[1]);
        int ret = 0;

        Context context = Kiara.createContext();

        Transport transport = context.createTransport("tcp://127.0.0.1:9090");
        Serializer serializer = context.createSerializer("cdr");

        Connection connection = context.connect(transport, serializer);
        CalculatorClient client = connection.getServiceProxy(CalculatorClient.class);

        try {
            // Call 'add' method.
            ret = client.add(param1, param2);
            System.out.println("Result of adding: " + ret);

            // Call 'subtract' method.
            ret = client.subtract(param1, param2);
            System.out.println("Result of subtracting: " + ret);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return;
        }
    }

}

import com.kiara.serialization.Serializer;
import com.kiara.serialization.Cdr;
import com.kiara.impl.SingleServer;
import com.kiara.transport.*;
import com.kiara.server.*;

public class ServerExample
{
    public static void main(String argv[]) throws Exception
    {
        ServerTransport transport = new TCPServerTransport(8080);
        Serializer serializer = new Cdr();
        SingleServer server = new SingleServer(serializer, transport);
        Servant servant = new CalculatorServantExample();
        server.addService(servant);
        server.serve();
    }
}

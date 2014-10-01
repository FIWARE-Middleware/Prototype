import com.kiara.marshaling.*;
import com.kiara.transport.*;
import com.kiara.server.*;

public class ServerExample
{
    public static void main(String argv[]) throws Exception
    {
        ServerTransport transport = new TCPServerTransport(8080);
        Serializer serializer = new Cdr();
        Server server = new Server(serializer, transport);
        Servant servant = new CalculatorServantExample();
        server.addService(servant);
        server.serve();
    }
}


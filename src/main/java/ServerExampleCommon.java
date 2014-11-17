import com.kiara.Context;
import com.kiara.Kiara;
import com.kiara.server.Server;
import com.kiara.server.Service;
import com.kiara.serialization.Serializer;
import com.kiara.server.Servant;
import com.kiara.transport.ServerTransport;
import com.kiara.transport.Transport;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rubinste
 */
public class ServerExampleCommon {

    public static void main(String argv[]) throws Exception {
        System.out.println("ServerExampleCommon");

        Context context = Kiara.createContext();
        Server server = context.createServer();

        CalculatorServant calculator_impl = new CalculatorServantExample();

        ServerTransport transport = context.createServerTransport("tcp://0.0.0.0:8080");
        Serializer serializer = context.createSerializer("cdr");
        Service service = context.createService();

        service.register(calculator_impl);

        // Add service waiting on TCP with CDR serialization
        server.addService(service, transport, serializer);
        server.addService(service, "tcp://0.0.0.0:9090", "cdr");

        server.run();
    }

}


import com.kiara.Context;
import com.kiara.Kiara;
import com.kiara.Server;
import com.kiara.Service;
import com.kiara.server.Servant;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rubinste
 */
public class ServerExampleDFKI {

    public static void main(String argv[]) throws Exception {
        System.out.println("ServerExampleDFKI");
        Context context = Kiara.createContext();
        Service service = context.newService();

        Servant servant = new CalculatorServantExample();
        service.register(servant);

        // Create server without negotiation
        Server server = context.newServer();

        // Add service waiting on TCP with CDR serialization
        server.addService("tcp://0.0.0.0:8080", "cdr", service);
        server.addService("tcp://0.0.0.0:9090", "cdr", service);
        server.run();
    }

}

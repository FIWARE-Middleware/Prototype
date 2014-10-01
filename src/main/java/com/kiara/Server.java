package com.kiara;

import com.kiara.marshaling.Cdr;
import com.kiara.marshaling.Serializer;
import com.kiara.server.Servant;
import com.kiara.server.SingleServer;
import com.kiara.transport.ServerTransport;
import com.kiara.transport.TCPServerTransport;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final List<Service> services;
    private final List<SingleServer> singleServers;
    private final ExecutorService pool;

    public Server() {
        services = new ArrayList<Service>();
        singleServers = new ArrayList<SingleServer>();
        pool = Executors.newCachedThreadPool();
    }

    public void addService(String path, String protocol, Service service) throws IOException {
        try {
            ServerTransport transport = null;
            Serializer serializer = null;

            services.add(service);

            URI uri = new URI(path);

            if (uri.getScheme().equals("tcp")) {
                transport = new TCPServerTransport(uri.getPort());
            } else {
                throw new RuntimeException("Unknown transport: " + uri.getScheme());
            }

            if (protocol.equals("cdr")) {
                serializer = new Cdr();
            } else {
                throw new RuntimeException("Unknown protocol: " + protocol);
            }

            SingleServer srv = new SingleServer(serializer, transport);

            for (Servant servant : service.getGeneratedServants()) {
                srv.addService(servant);
            }

            singleServers.add(srv);
        } catch (URISyntaxException ex) {
            throw new IOException(ex);
        } catch (Exception ex) {
            throw new IOException(ex);
        }

    }

    public boolean removeService(Service service) {
        return services.remove(service);
    }

    public void run() {
        for (final SingleServer srv : singleServers) {
            pool.execute(new Runnable() {
                public void run() {
                    srv.serve();
                }
            });
        }
    }
}

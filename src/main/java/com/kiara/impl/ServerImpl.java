package com.kiara.impl;

import com.kiara.Context;
import com.kiara.Server;
import com.kiara.Service;
import com.kiara.marshaling.Serializer;
import com.kiara.server.Servant;
import com.kiara.server.SingleServer;
import com.kiara.transport.ServerTransport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImpl implements Server {

    private final Context context;
    private final List<Service> services;
    private final List<SingleServer> singleServers;
    private final ExecutorService pool;

    public ServerImpl(Context context) {
        this.context = context;
        services = new ArrayList<Service>();
        singleServers = new ArrayList<SingleServer>();
        pool = Executors.newCachedThreadPool();
    }

    public void addService(Service service, ServerTransport serverTransport, Serializer serializer) throws IOException {
        services.add(service);
        SingleServer srv = new SingleServer(serializer, serverTransport);

        for (Servant servant : service.getGeneratedServants()) {
            srv.addService(servant);
        }

        singleServers.add(srv);
    }

    public void addService(Service service, String path, String protocol) throws IOException {
        addService(service, context.createServerTransport(path), context.createSerializer(protocol));
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

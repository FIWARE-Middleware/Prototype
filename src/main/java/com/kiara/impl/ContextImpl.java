package com.kiara.impl;

import com.kiara.client.Connection;
import com.kiara.Context;
import com.kiara.server.Server;
import com.kiara.server.Service;
import com.kiara.serialization.Cdr;
import com.kiara.serialization.Serializer;
import com.kiara.transport.ServerTransport;
import com.kiara.transport.TCPProxyTransport;
import com.kiara.transport.TCPServerTransport;
import com.kiara.transport.Transport;
import java.io.IOException;
import java.net.URI;

public class ContextImpl implements Context {
        public Connection connect(String url, String protocol) throws IOException {
        // We should perform here negotation, but for now only a fixed transport/protocol combination
        final Transport transport = createTransport(url);
        final Serializer serializer = createSerializer(protocol);

        return new ConnectionImpl(transport, serializer);
    }

    public Connection connect(Transport transport, Serializer serializer) throws IOException {
        return new ConnectionImpl(transport, serializer);
    }
    
    public Service createService() {
        return new ServiceImpl();
    }

    // Create server without negotiation
    public Server createServer() {
        return new ServerImpl(this);
    }

    public Transport createTransport(String url) throws IOException {
        try {
            URI uri = new URI(url);

            if (!uri.getScheme().equals("tcp"))
                throw new IOException("Unsupported transport: "+uri.getScheme());
            return new TCPProxyTransport(uri.getHost(), uri.getPort());
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public ServerTransport createServerTransport(String url) throws IOException {
        try {
            URI uri = new URI(url);

            if (!uri.getScheme().equals("tcp"))
                throw new IOException("Unsupported transport: "+uri.getScheme());
            return new TCPServerTransport(uri.getPort());
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public Serializer createSerializer(String name) throws IOException {
        if (!"cdr".equals(name))
            throw new IOException("Unsupported serializer: "+name);
        return new Cdr();
    }

}

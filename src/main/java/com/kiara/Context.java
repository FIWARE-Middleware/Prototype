package com.kiara;

import com.kiara.impl.ConnectionImpl;
import com.kiara.impl.ServerImpl;
import com.kiara.impl.ServiceImpl;
import java.io.IOException;
import java.net.URI;

import com.kiara.marshaling.Cdr;
import com.kiara.marshaling.Serializer;
import com.kiara.transport.ServerTransport;
import com.kiara.transport.Transport;
import com.kiara.transport.TCPProxyTransport;
import com.kiara.transport.TCPServerTransport;

public class Context {
    public Connection createConnection(String url, String protocol) throws IOException {
        // We should perform here negotation, but for now only a fixed transport/protocol combination
        final Transport transport = createTransport(url);
        final Serializer serializer = createSerializer(protocol);

        return new ConnectionImpl(transport, serializer);
    }

    public Connection createConnection(Transport transport, Serializer serializer) throws IOException {
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

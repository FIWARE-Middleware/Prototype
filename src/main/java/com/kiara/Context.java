package com.kiara;

import java.io.IOException;
import com.kiara.marshaling.Serializer;
import com.kiara.transport.ServerTransport;
import com.kiara.transport.Transport;

public interface Context {
    public Connection createConnection(String url, String protocol) throws IOException;

    public Connection createConnection(Transport transport, Serializer serializer) throws IOException;
    
    public Service createService();

    // Create server without negotiation
    public Server createServer();

    public Transport createTransport(String url) throws IOException;

    public ServerTransport createServerTransport(String url) throws IOException;

    public Serializer createSerializer(String name) throws IOException;

}

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
import io.netty.handler.codec.http.QueryStringDecoder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class ContextImpl implements Context {

    public Connection connect(String url) throws IOException {
        try {
            URI uri = new URI(url);
            QueryStringDecoder decoder = new QueryStringDecoder(uri);

            String serializerName = null;

            List<String> parameters = decoder.parameters().get("serialization");
            if (parameters != null && !parameters.isEmpty()) {
                serializerName = parameters.get(0);
            }

            if (serializerName == null) {
                throw new IllegalArgumentException("No serializer is specified as a part of the URI");
            }

            // We should perform here negotation, but for now only a fixed transport/protocol combination
            final Transport transport = createTransport(url);
            final Serializer serializer = createSerializer(serializerName);

            return new ConnectionImpl(transport, serializer);
        } catch (URISyntaxException ex) {
            throw new IOException(ex);
        }
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
        if (url == null) {
            throw new NullPointerException(url);
        }

        try {
            URI uri = new URI(url);

            if (!"tcp".equals(uri.getScheme())) {
                throw new IOException("Unsupported transport: " + uri.getScheme());
            }
            return new TCPProxyTransport(uri.getHost(), uri.getPort());
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public ServerTransport createServerTransport(String url) throws IOException {
        try {
            URI uri = new URI(url);

            if (!uri.getScheme().equals("tcp")) {
                throw new IOException("Unsupported transport: " + uri.getScheme());
            }
            return new TCPServerTransport(uri.getPort());
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public Serializer createSerializer(String name) throws IOException {
        if (!"cdr".equals(name)) {
            throw new IOException("Unsupported serializer: " + name);
        }
        return new Cdr();
    }

}

package com.kiara;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.kiara.marshaling.Cdr;
import com.kiara.marshaling.Serializer;
import com.kiara.transport.ProxyTransport;
import com.kiara.transport.TCPProxyTransport;

public class Context {
    public Connection openConnection(String url) throws IOException {
        try {
            URI uri = new URI(url);

            // We should perform here negotation, but for now only a fixed transport/protocol combination

            if (!uri.getScheme().equals("tcp"))
                throw new IOException("Unsupported transport: "+uri.getScheme());
            ProxyTransport transport = new TCPProxyTransport(uri.getHost(), uri.getPort());
            Serializer ser = new Cdr();

            return new Connection(transport, ser);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}

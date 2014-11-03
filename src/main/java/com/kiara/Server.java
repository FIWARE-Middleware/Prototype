package com.kiara;

import com.kiara.marshaling.Serializer;
import com.kiara.transport.ServerTransport;
import java.io.IOException;

public interface Server {

    public void addService(Service service, String path, String protocol) throws IOException;
    
    public void addService(Service service, ServerTransport serverTransport, Serializer serializer) throws IOException;

    public boolean removeService(Service service);

    public void run();
}

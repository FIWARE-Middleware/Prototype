package com.kiara;

import com.kiara.marshaling.Serializer;
import com.kiara.transport.Transport;

public interface Connection {
    public Transport getTransport();

    public Serializer getSerializer();

    public <T> T createClient(Class<T> interfaceClass) throws Exception;

}

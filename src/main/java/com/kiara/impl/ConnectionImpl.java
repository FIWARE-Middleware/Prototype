package com.kiara.impl;

import com.kiara.Connection;
import com.kiara.marshaling.Serializer;
import com.kiara.transport.Transport;
import java.lang.reflect.Constructor;

public class ConnectionImpl implements Connection {
    private final Transport transport;
    private final Serializer serializer;

    public ConnectionImpl(Transport transport, Serializer serializer) {
        super();
        this.transport = transport;
        this.serializer = serializer;
    }

    public Transport getTransport() {
        return transport;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public <T> T createClient(Class<T> interfaceClass) throws Exception {
        String proxyClassName = interfaceClass.getName()+"Proxy";
        Class<?> proxyClass = Class.forName(proxyClassName);
        if (!interfaceClass.isAssignableFrom(proxyClass))
            throw new RuntimeException("Proxy class "+proxyClass+" does not implement interface "+interfaceClass);
        Constructor<?> proxyConstr = proxyClass.getConstructor(Serializer.class, Transport.class);
        proxyConstr.setAccessible(true);
        return interfaceClass.cast(proxyConstr.newInstance(serializer, transport));
    }

}

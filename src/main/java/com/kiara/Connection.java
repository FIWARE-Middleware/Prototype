package com.kiara;

import com.kiara.marshaling.Serializer;
import com.kiara.transport.ProxyTransport;
import java.lang.reflect.Constructor;

public class Connection {
    private final ProxyTransport transport;
    private final Serializer serializer;

    Connection(ProxyTransport transport, Serializer serializer) {
        super();
        this.transport = transport;
        this.serializer = serializer;
    }

    public ProxyTransport getTransport() {
        return transport;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public <T> T getServiceInterface(Class<T> interfaceClass) throws Exception {
        String proxyClassName = interfaceClass.getName()+"Proxy";
        Class<?> proxyClass = Class.forName(proxyClassName);
        if (!interfaceClass.isAssignableFrom(proxyClass))
            throw new RuntimeException("Proxy class "+proxyClass+" does not implement interface "+interfaceClass);
        Constructor<?> proxyConstr = proxyClass.getConstructor(Serializer.class, ProxyTransport.class);
        proxyConstr.setAccessible(true);
        return interfaceClass.cast(proxyConstr.newInstance(serializer, transport));
    }

}

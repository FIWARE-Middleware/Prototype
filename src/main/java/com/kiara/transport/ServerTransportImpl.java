package com.kiara.transport;

import java.nio.ByteBuffer;

public interface ServerTransportImpl extends ServerTransport {

    public void listen(Listener listener);

    public boolean sendreply(Object endpoint, ByteBuffer buffer);

}

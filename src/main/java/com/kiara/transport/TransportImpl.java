package com.kiara.transport;

import java.nio.ByteBuffer;

public interface TransportImpl extends Transport {
    public boolean send(ByteBuffer buffer);

    public ByteBuffer recv();
}

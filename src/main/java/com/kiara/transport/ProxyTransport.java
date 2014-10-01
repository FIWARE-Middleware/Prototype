package com.kiara.transport;

import java.nio.ByteBuffer;

public interface ProxyTransport
{
    public boolean send(ByteBuffer buffer);

    public ByteBuffer recv();
}

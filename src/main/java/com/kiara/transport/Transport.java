package com.kiara.transport;

import java.nio.ByteBuffer;

public interface Transport
{
    public boolean send(ByteBuffer buffer);

    public ByteBuffer recv();
}

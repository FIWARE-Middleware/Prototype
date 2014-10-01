package com.kiara.transport;

import java.nio.ByteBuffer;

public interface Listener
{
    public void accept_request(Object endpoint, ByteBuffer buffer);
}

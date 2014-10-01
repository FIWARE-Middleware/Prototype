package com.kiara.server;

import com.kiara.marshaling.Serializer;
import java.nio.ByteBuffer;

public interface Servant
{
    public String getServiceName();

    public ByteBuffer process(Serializer ser, ByteBuffer buffer);
}

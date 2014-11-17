package com.kiara.serialization;

import java.nio.ByteBuffer;

public interface Serializer
{
    public void serializeService(ByteBuffer buffer, String service);

    public String deserializeService(ByteBuffer buffer);

    public void serializeOperation(ByteBuffer buffer, String operation);

    public String deserializeOperation(ByteBuffer buffer);

    public void serializeString(ByteBuffer buffer, String data);

    public String deserializeString(ByteBuffer buffer);

    public void serializeInteger(ByteBuffer buffer, int data);

    public int deserializeInteger(ByteBuffer buffer);
}

package com.kiara.serialization;

import java.nio.ByteBuffer;

public class Cdr implements Serializer
{
    public void serializeService(ByteBuffer buffer, String service)
    {
        this.serializeString(buffer, service);
    }

    public String deserializeService(ByteBuffer buffer)
    {
        return this.deserializeString(buffer);
    }

    public void serializeOperation(ByteBuffer buffer, String operation)
    {
        this.serializeString(buffer, operation);
    }

    public String deserializeOperation(ByteBuffer buffer)
    {
        return this.deserializeString(buffer);
    }

    public void serializeString(ByteBuffer buffer, String data)
    {
        byte[] bytes = data.getBytes();
        this.serializeInteger(buffer, bytes.length);
        buffer.put(bytes);
    }

    public String deserializeString(ByteBuffer buffer)
    {
        int length = this.deserializeInteger(buffer);
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new String(bytes);
    }

    public void serializeInteger(ByteBuffer buffer, int data)
    {
        buffer.putInt(data);
    }

    public int deserializeInteger(ByteBuffer buffer)
    {
        return buffer.getInt();
    }
}

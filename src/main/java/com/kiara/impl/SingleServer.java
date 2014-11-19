package com.kiara.impl;

import com.kiara.serialization.Serializer;
import com.kiara.server.Servant;
import com.kiara.transport.*;
import java.nio.ByteBuffer;
import java.util.*;

public class SingleServer implements Listener
{
    public SingleServer(Serializer ser, ServerTransport transport)
    {
        m_ser = ser;
        m_transport = (ServerTransportImpl)transport;
        m_servants = new HashMap<String, Servant>();
    }

    public void serve()
    {
        if(m_transport != null)
            m_transport.listen(this);
    }

    public void addService(Servant servant)
    {
        m_servants.put(servant.getServiceName(), servant);
    }

    public void accept_request(Object endpoint, ByteBuffer buffer)
    {
        final Object messageId = m_ser.deserializeMessageId(buffer);
        final String service = m_ser.deserializeService(buffer);
        Servant servant = m_servants.get(service);

        if(servant != null)
        {
            ByteBuffer reply = servant.process(m_ser, buffer, messageId);

            if(reply != null)
            {
                m_transport.sendreply(endpoint, reply);
            }
            else
            {
                // TODO return an error to the client.
            }
        }
        else
        {
            // TODO return an error to the client.
        }
    }

    private Serializer m_ser = null;
    private ServerTransportImpl m_transport = null;
    HashMap<String, Servant> m_servants = null;
}

import com.kiara.marshaling.*;
import com.kiara.transport.*;
import java.nio.ByteBuffer;

class CalculatorProxy
{
    public CalculatorProxy(Serializer ser, ProxyTransport transport)
    {
        m_ser = ser;
        m_transport = transport;
    }

    int add(int param1, int param2)
    {
        if(m_ser != null && m_transport != null)
        {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            m_ser.serializeService(buffer, "Calculator");
            m_ser.serializeOperation(buffer, "add");
            m_ser.serializeInteger(buffer, param1);
            m_ser.serializeInteger(buffer, param2);

            if(m_transport.send(buffer) == true)
            {
                ByteBuffer reply = m_transport.recv();

                if(reply != null)
                {
                    int ret = m_ser.deserializeInteger(reply);
                    return ret;
                }
            }
        }

        return 0;
    }

    int subtract(int param1, int param2)
    {
        if(m_ser != null && m_transport != null)
        {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            m_ser.serializeService(buffer, "Calculator");
            m_ser.serializeOperation(buffer, "subtract");
            m_ser.serializeInteger(buffer, param1);
            m_ser.serializeInteger(buffer, param2);

            if(m_transport.send(buffer) == true)
            {
                ByteBuffer reply = m_transport.recv();

                if(reply != null)
                {
                    int ret = m_ser.deserializeInteger(reply);
                    return ret;
                }
            }
        }

        return 0;
    }

    private Serializer m_ser = null;
    private ProxyTransport m_transport = null;
}

package com.kiara.transport;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;

public class TCPProxyTransport implements Transport
{
    public TCPProxyTransport(String ip_address, int port) throws Exception
    {
        m_address = InetAddress.getByName(ip_address);
        m_port = port;
        m_socket = new Socket(m_address, m_port);
    }

    public boolean send(ByteBuffer buffer)
    {
        if(m_socket != null)
        {
            try
            {
                if(m_socket.isClosed())
                {
                    m_socket = new Socket(m_address, m_port);
                }

                OutputStream os = m_socket.getOutputStream();

                if(buffer.hasArray())
                {
                    os.write(buffer.array());
                    return true;
                }
            }
            catch(IOException ex)
            {
                System.out.println("ERROR<TCPProxyTransport::send>: " + ex.getMessage());
            }
        }
        else
        {
            System.out.println("ERROR<TCPProxyTransport::send>: socket is not initialized");
        }

        return false;
    }

    public ByteBuffer recv()
    {
        try
        {
            InputStream is = m_socket.getInputStream();
            int length = is.available();
            while(length == 0)
                length = is.available();

            byte[] bytes = new byte[length];
            is.read(bytes);
            m_socket.close();
            return ByteBuffer.wrap(bytes);
        }
        catch(Exception ex)
        {
            System.out.println("ERROR<TCPServerTransport::recv>: " + ex.getMessage());
        }

        return null;
    }

    private InetAddress m_address = null;
    private int m_port = 0;
    private Socket m_socket = null;
}

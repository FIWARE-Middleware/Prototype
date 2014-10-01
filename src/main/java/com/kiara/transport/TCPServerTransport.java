package com.kiara.transport;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class TCPServerTransport implements ServerTransport
{
    public TCPServerTransport(int port) throws Exception
    {
        m_socket = new ServerSocket(port);
    }

    public void listen(Listener listener)
    {
        while(true)
        {
            try
            {
                Socket connectionSocket = m_socket.accept();
                InputStream is = connectionSocket.getInputStream();
                int length = is.available();
                while(length == 0)
                length = is.available();

                byte[] bytes = new byte[length];
                is.read(bytes);
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                listener.accept_request(connectionSocket, buffer);
            }
            catch(Exception ex)
            {
                System.out.println("ERROR<TCPServerTransport::listen>: " + ex.getMessage());
            }
        }
    }

    public boolean sendreply(Object endpoint, ByteBuffer buffer)
    {
        if(endpoint != null && endpoint instanceof Socket)
        {
            Socket connectionSocket = (Socket)endpoint;

            try
            {
                OutputStream os = connectionSocket.getOutputStream();

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

        return false;
    }

    private ServerSocket m_socket = null;
}

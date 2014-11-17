package com.kiara.transport;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;

public class TCPProxyTransport implements Transport {

    public TCPProxyTransport(String ip_address, int port) throws Exception {
        m_address = InetAddress.getByName(ip_address);
        m_port = port;
        m_socket = new Socket(m_address, m_port);
    }

    public boolean send(ByteBuffer buffer) {
        if (m_socket != null) {
            try {
                if (m_socket.isClosed()) {
                    m_socket = new Socket(m_address, m_port);
                }

                OutputStream os = m_socket.getOutputStream();

                if (buffer.hasArray()) {
                    byte[] bufferSize = new byte[4];
                    final byte[] array = buffer.array();
                    final int arraySize = array.length;
                    System.err.println("Client write size " + array.length);
                    os.write(0xFF & arraySize);
                    os.write(0xFF & (arraySize >> 8));
                    os.write(0xFF & (arraySize >> 16));
                    os.write(0xFF & (arraySize >> 24));
                    os.write(array);
                    return true;
                }
            } catch (IOException ex) {
                System.out.println("ERROR<TCPProxyTransport::send>: " + ex.getMessage());
            }
        } else {
            System.out.println("ERROR<TCPProxyTransport::send>: socket is not initialized");
        }

        return false;
    }

    private static int littleEndianToInt(byte[] data) {
        return (data[3]) << 24
                | (data[2] & 0xff) << 16
                | (data[1] & 0xff) << 8
                | (data[0] & 0xff);
    }

    public final void readFully(InputStream in, byte b[], int off, int len) throws IOException {
        if (len < 0) {
            throw new IndexOutOfBoundsException();
        }
        int n = 0;
        while (n < len) {
            int count = in.read(b, off + n, len - n);
            if (count < 0) {
                throw new EOFException();
            }
            n += count;
        }
    }

    public ByteBuffer recv() {
        try {
            InputStream is = m_socket.getInputStream();

            int length = 0;
            int num;
            byte[] bytes = new byte[4];
            while (length == 0) {
                readFully(is, bytes, 0, 4);
                length = littleEndianToInt(bytes);
            }

            bytes = new byte[length];
            readFully(is, bytes, 0, length);
            m_socket.close();
            return ByteBuffer.wrap(bytes);
        } catch (Exception ex) {
            System.out.println("ERROR<TCPServerTransport::recv>: " + ex.getMessage());
        }

        return null;
    }

    private InetAddress m_address = null;
    private int m_port = 0;
    private Socket m_socket = null;
}

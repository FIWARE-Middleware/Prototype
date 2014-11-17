package com.kiara.transport;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class TCPServerTransport implements ServerTransport {

    public TCPServerTransport(int port) throws Exception {
        m_socket = new ServerSocket(port);
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

    public void listen(Listener listener) {
        while (true) {
            try {
                Socket connectionSocket = m_socket.accept();
                InputStream is = connectionSocket.getInputStream();

                int length = 0;
                int num;
                byte[] bytes = new byte[4];
                while (length == 0) {
                    readFully(is, bytes, 0, 4);
                    length = littleEndianToInt(bytes);
                }

                bytes = new byte[length];
                readFully(is, bytes, 0, length);
                //num = is.read(bytes, 0, length);
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                listener.accept_request(connectionSocket, buffer);
            } catch (EOFException ex) {
            } catch (Exception ex) {
                System.out.println("ERROR<TCPServerTransport::listen>: " + ex);
            }
        }
    }

    public boolean sendreply(Object endpoint, ByteBuffer buffer) {
        if (endpoint != null && endpoint instanceof Socket) {
            Socket connectionSocket = (Socket) endpoint;

            try {
                OutputStream os = connectionSocket.getOutputStream();

                if (buffer.hasArray()) {
                    byte[] bufferSize = new byte[4];
                    final byte[] array = buffer.array();
                    final int arraySize = array.length;
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
        }

        return false;
    }

    private ServerSocket m_socket = null;
}

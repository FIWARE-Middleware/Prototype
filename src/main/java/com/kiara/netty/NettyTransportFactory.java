/* KIARA - Middleware for efficient and QoS/Security-aware invocation of services and exchange of messages
 *
 * Copyright (C) 2014 German Research Center for Artificial Intelligence (DFKI)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

package com.kiara.netty;

import com.kiara.transport.impl.Global;
import com.kiara.transport.impl.TransportConnectionListener;
import com.kiara.transport.impl.TransportFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLException;

/**
 *
 * @author Dmitri Rubinstein <dmitri.rubinstein@dfki.de>
 */
public abstract class NettyTransportFactory implements TransportFactory {

    private static final boolean SSL = System.getProperty("ssl") != null;

    static {
        InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
    }

    public NettyTransportFactory() {
    }

    protected final EventLoopGroup getEventLoopGroup() {
        return Global.transportGroup;
    }

    protected SslContext createServerSslContext() throws CertificateException, SSLException {
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            return SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            return null;
        }
    }

    public abstract ChannelHandler createServerChildHandler(String path, TransportConnectionListener connectionListener);

}

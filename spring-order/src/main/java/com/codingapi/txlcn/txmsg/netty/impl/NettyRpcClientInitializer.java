package com.codingapi.txlcn.txmsg.netty.impl;

import com.codingapi.txlcn.txmsg.RpcClientInitializer;
import com.codingapi.txlcn.txmsg.RpcConfig;
import com.codingapi.txlcn.txmsg.dto.TxManagerHost;
import com.codingapi.txlcn.txmsg.listener.ClientInitCallBack;
import com.codingapi.txlcn.txmsg.netty.bean.SocketManager;
import com.codingapi.txlcn.txmsg.netty.em.NettyType;
import com.codingapi.txlcn.txmsg.netty.handler.init.NettyRpcClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class NettyRpcClientInitializer implements RpcClientInitializer, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(com.codingapi.txlcn.txmsg.netty.impl.NettyRpcClientInitializer.class);
    private static com.codingapi.txlcn.txmsg.netty.impl.NettyRpcClientInitializer INSTANCE;
    private final NettyRpcClientChannelInitializer nettyRpcClientChannelInitializer;
    private final RpcConfig rpcConfig;
    private final ClientInitCallBack clientInitCallBack;
    private EventLoopGroup workerGroup;

    @Autowired
    public NettyRpcClientInitializer(NettyRpcClientChannelInitializer nettyRpcClientChannelInitializer, RpcConfig rpcConfig, ClientInitCallBack clientInitCallBack) {
        this.nettyRpcClientChannelInitializer = nettyRpcClientChannelInitializer;
        this.rpcConfig = rpcConfig;
        this.clientInitCallBack = clientInitCallBack;
        INSTANCE = this;
    }

    public static void reConnect(SocketAddress socketAddress) {
        Objects.requireNonNull(socketAddress, "non support!");
        INSTANCE.connect(socketAddress);
    }

    public void init(List<TxManagerHost> hosts, boolean sync) {
        NettyContext.type = NettyType.client;
        NettyContext.params = hosts;
        this.workerGroup = new NioEventLoopGroup();
        Iterator var3 = hosts.iterator();

        while(var3.hasNext()) {
            TxManagerHost host = (TxManagerHost)var3.next();
            Optional<Future> future = this.connect(new InetSocketAddress(host.getHost(), host.getPort()));
            if (sync && future.isPresent()) {
                try {
                    ((Future)future.get()).get(10L, TimeUnit.SECONDS);
                } catch (ExecutionException | TimeoutException | InterruptedException var7) {
                    log.error(var7.getMessage(), var7);
                }
            }
        }

    }

    public synchronized Optional<Future> connect(SocketAddress socketAddress) {
        int i = 0;

//        while(i < this.rpcConfig.getReconnectCount()) {
        boolean xianzhi = false;//是否限制断线重连次数,因为默认是重试8次就不连接了
        while(!xianzhi ||     (xianzhi && i < rpcConfig.getReconnectCount())) {
            if (!SocketManager.getInstance().noConnect(socketAddress)) {
                return Optional.empty();
            }

            try {
                log.info("Try connect socket({}) - count {}", socketAddress, i + 1);
                Bootstrap b = new Bootstrap();
                b.group(this.workerGroup);
                b.channel(NioSocketChannel.class);
                b.option(ChannelOption.SO_KEEPALIVE, true);
                b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
                b.handler(this.nettyRpcClientChannelInitializer);
                return Optional.of(b.connect(socketAddress).syncUninterruptibly());
            } catch (Exception var6) {
                log.warn("Connect socket({}) fail. {}ms latter try again.", socketAddress, this.rpcConfig.getReconnectDelay());

                try {
                    Thread.sleep(this.rpcConfig.getReconnectDelay());
                } catch (InterruptedException var5) {
                    var5.printStackTrace();
                }

                ++i;
            }
        }

        log.warn("Finally, netty connection fail , socket is {}", socketAddress);
        this.clientInitCallBack.connectFail(socketAddress.toString());
        return Optional.empty();
    }

    public void destroy() {
        this.workerGroup.shutdownGracefully();
        log.info("RPC client was down.");
    }
}

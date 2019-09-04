package com.codingapi.txlcn.txmsg.netty.bean;

import com.codingapi.txlcn.txmsg.RpcConfig;
import com.codingapi.txlcn.txmsg.dto.AppInfo;
import com.codingapi.txlcn.txmsg.dto.MessageDto;
import com.codingapi.txlcn.txmsg.dto.RpcCmd;
import com.codingapi.txlcn.txmsg.dto.RpcResponseState;
import com.codingapi.txlcn.txmsg.exception.RpcException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.*;

public class SocketManager {
    private static final Logger log = LoggerFactory.getLogger(SocketManager.class);
    private Map<String, AppInfo> appNames;
    private ScheduledExecutorService executorService;
    private ChannelGroup channels;
    private static SocketManager manager = null;
    private long attrDelayTime = 60000L;

    private SocketManager() {
        this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.appNames = new ConcurrentHashMap();
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.executorService.shutdown();

            try {
                this.executorService.awaitTermination(10L, TimeUnit.MINUTES);
            } catch (InterruptedException var2) {
                ;
            }

        }));
    }

    public static SocketManager getInstance() {
        if (manager == null) {
            Class var0 = SocketManager.class;
            synchronized(SocketManager.class) {
                if (manager == null) {
                    manager = new SocketManager();
                }
            }
        }

        return manager;
    }

    public void addChannel(Channel channel) {
        this.channels.add(channel);
    }

    public void removeChannel(Channel channel) {
        this.channels.remove(channel);
        String key = channel.remoteAddress().toString();
        if (this.attrDelayTime < 0L) {
            this.appNames.remove(key);
        } else {
            try {
                this.executorService.schedule(() -> {
                    this.appNames.remove(key);
                }, this.attrDelayTime, TimeUnit.MILLISECONDS);
            } catch (RejectedExecutionException var4) {
                ;
            }

        }
    }

    private Channel getChannel(String key) throws RpcException {
        Iterator var2 = this.channels.iterator();

        Channel channel;
        String val;
        do {
            if (!var2.hasNext()) {
                throw new RpcException("channel not online.");
            }

            channel = (Channel)var2.next();
            val = channel.remoteAddress().toString();
        } while(!key.equals(val));

        return channel;
    }

    public RpcResponseState send(String key, RpcCmd cmd) throws RpcException {
        Channel channel = this.getChannel(key);
        ChannelFuture future = channel.writeAndFlush(cmd).syncUninterruptibly();
        return future.isSuccess() ? RpcResponseState.success : RpcResponseState.fail;
    }

    public MessageDto request(String key, RpcCmd cmd, long timeout) throws RpcException {
        NettyRpcCmd nettyRpcCmd = (NettyRpcCmd)cmd;
        log.debug("get channel, key:{}", key);
        Channel channel = this.getChannel(key);
        channel.writeAndFlush(nettyRpcCmd);
        log.debug("await response");
        if (timeout < 1L) {
            nettyRpcCmd.await();
        } else {
            nettyRpcCmd.await(timeout);
        }

        MessageDto res = cmd.loadResult();
        log.debug("response is: {}", res);
        nettyRpcCmd.loadRpcContent().clear();
        return res;
    }

    public MessageDto request(String key, RpcCmd cmd) throws RpcException {
        return this.request(key, cmd, -1L);
    }

    public List<String> loadAllRemoteKey() {
        List<String> allKeys = new ArrayList();
        Iterator var2 = this.channels.iterator();

        while(var2.hasNext()) {
            Channel channel = (Channel)var2.next();
            allKeys.add(channel.remoteAddress().toString());
        }

        return allKeys;
    }

    public ChannelGroup getChannels() {
        return this.channels;
    }

    public int currentSize() {
        return this.channels.size();
    }

    public boolean noConnect(SocketAddress socketAddress) {
        Iterator var2 = this.channels.iterator();

        Channel channel;
        do {
            if (!var2.hasNext()) {
                return true;
            }

            channel = (Channel)var2.next();
        } while(!channel.remoteAddress().toString().equals(socketAddress.toString()));

        return false;
    }

    public List<String> removeKeys(String moduleName) {
        List<String> allKeys = new ArrayList();
        Iterator var3 = this.channels.iterator();

        while(var3.hasNext()) {
            Channel channel = (Channel)var3.next();
            if (moduleName.equals(this.getModuleName(channel))) {
                allKeys.add(channel.remoteAddress().toString());
            }
        }

        return allKeys;
    }

    public void bindModuleName(String remoteKey, String appName, String labelName) throws RpcException {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppName(appName);
        appInfo.setLabelName(labelName);
        appInfo.setCreateTime(new Date());
        if (this.containsLabelName(labelName)) {
            throw new RpcException("labelName:" + labelName + " has exist.");
        } else {
            this.appNames.put(remoteKey, appInfo);
        }
    }

    public boolean containsLabelName(String moduleName) {
        Set<String> keys = this.appNames.keySet();
        Iterator var3 = keys.iterator();

        AppInfo appInfo;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            String key = (String)var3.next();
            appInfo = (AppInfo)this.appNames.get(key);
        } while(!moduleName.equals(appInfo.getLabelName()));

        return true;
    }

    public void setRpcConfig(RpcConfig rpcConfig) {
        this.attrDelayTime = rpcConfig.getAttrDelayTime();
    }

    public String getModuleName(Channel channel) {
        String key = channel.remoteAddress().toString();
        return this.getModuleName(key);
    }

    public String getModuleName(String remoteKey) {
        AppInfo appInfo = (AppInfo)this.appNames.get(remoteKey);
        return appInfo == null ? null : appInfo.getLabelName();
    }

    public List<AppInfo> appInfos() {
        return new ArrayList(this.appNames.values());
    }
}

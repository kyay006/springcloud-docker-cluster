package com.codingapi.txlcn.tc.support;

import com.codingapi.txlcn.common.util.id.ModIdProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class MyModIdProvider implements ModIdProvider {

    @Value("${server.port}")
    private String serverPort;

    public String getSeverID() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://" + address.getHostAddress() + ":" + this.serverPort;
    }

    @Override
    public String modId() {
        return getSeverID();
    }

}
package com.yanglx.dubbo.test.ui;

import com.yanglx.dubbo.test.utils.StrUtils;

import java.util.UUID;

public class MyConfigurableDubboSettings {

    private UUID id;

    private String name;

    private String username;

    private String password;

    private String timeout;

    private String address;

    private String ip;

    private String protocol;

    private String port;

    private String version;

    private String group;

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public MyConfigurableDubboSettings() {
        this(UUID.randomUUID());
    }

    public MyConfigurableDubboSettings(UUID id) {
        this.id = id;
    }

    public void setConfig(String name, String address, String version, String group, String username, String password, String timeout) {
        String protocol = address.substring(0, address.indexOf("://"));
        String ip = address.substring(address.indexOf("://") + 3, address.lastIndexOf(":"));
        String port = address.substring(address.lastIndexOf(":") + 1);
        this.ip = ip;
        this.protocol = protocol;
        this.port = port;
        this.version = version;
        this.group = group;
        this.name = name;
        this.username = username;
        this.password = password;
        this.timeout = timeout;
    }

    public void setConfig(String name, String address, String version,String username, String password, String timeout, String group, UUID uuid) {
        this.id = uuid;
        String protocol = address.substring(0, address.indexOf("://"));
        String ip = address.substring(address.indexOf("://") + 3, address.lastIndexOf(":"));
        String port = address.substring(address.lastIndexOf(":") + 1);
        this.ip = ip;
        this.protocol = protocol;
        this.port = port;
        this.version = version;
        this.group = group;
        this.name = name;
    }

    public String getProcessedAddress() {
        if (StrUtils.isNotBlank(this.protocol) && StrUtils.isNotBlank(this.ip) && StrUtils.isNotBlank(this.port)) {
            return this.protocol + "://" + this.ip + ":" + this.port;
        } else {
            return null;
        }
    }
}

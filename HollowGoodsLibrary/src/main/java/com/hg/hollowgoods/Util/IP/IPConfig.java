package com.hg.hollowgoods.Util.IP;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by HG on 2017-07-11.
 */
@Table(name = "IPConfig")
public class IPConfig implements Serializable {

    @Column(name = "tableId", isId = true)
    private int tableId;

    @Column(name = "ip")
    private String ip;
    @Column(name = "port")
    private String port;

    public IPConfig() {
    }

    public IPConfig(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

}

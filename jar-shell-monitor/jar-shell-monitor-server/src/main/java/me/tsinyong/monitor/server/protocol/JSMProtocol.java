package me.tsinyong.monitor.server.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class JSMProtocol {

    @Setter
    @Getter
    private long timeStamp;

    @Setter
    @Getter
    private String content;

    public JSMProtocol(String content) {
        this.content = content;
        this.timeStamp = System.currentTimeMillis();
    }
}

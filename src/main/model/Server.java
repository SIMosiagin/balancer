package main.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Server {

    private String name;

    private String forwardTo;

    private long timeoutMs;

    private int weight;

    @JsonCreator
    public Server(
            @JsonProperty("name") String name,
            @JsonProperty("forward_to") String forwardTo,
            @JsonProperty("timeout_ms") long timeoutMs,
            @JsonProperty("weight") int weight
    ) {
        this.name = name;
        this.forwardTo = forwardTo;
        this.timeoutMs = timeoutMs;
        this.weight = weight;
    }
}

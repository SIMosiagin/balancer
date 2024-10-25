package main.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import main.model.strategies.AssignStrategy;

import java.util.List;

@Getter
@Setter
@ToString
public class Config {

    private int workerProcesses;

    private AssignStrategy strategy;

    private List<Server> upstreams;

    @JsonCreator
    public Config(
            @JsonProperty("worker_processes") int workerProcesses,
            @JsonProperty("strategy") AssignStrategy strategy,
            @JsonProperty("upstreams") List<Server> upstreams
    ) {
        this.workerProcesses = workerProcesses;
        this.strategy = strategy;
        this.upstreams = upstreams;
    }
}

package main.model.strategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import main.model.Server;

import java.util.List;
import java.util.Map;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Хранит типы по имени
        include = JsonTypeInfo.As.PROPERTY, // Добавляет свойство для хранения типа
        property = "type" // Имя свойства, где будет храниться информация о типе
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoundRobin.class, name = "RoundRobin")
})
public interface AssignStrategy {

    public Server routeRequest(List<Server> upstreams);
}

package sfedu.xast.models;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Document(collection = "history")
public class HistoryContent {
    @Id
    private String id;
    private String className;
    private LocalDateTime createdDate;
    private String actor;
    private String methodName;
    private Map<String, Object> object; //для хранения состояния объекта
    private Status status;

    public enum Status{
        SUCCESS, FAULT
    }

    public HistoryContent() {
        this.createdDate = LocalDateTime.now();
        this.actor = "system";
    }
}

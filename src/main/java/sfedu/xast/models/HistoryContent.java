package sfedu.xast.models;
import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@Setter
public class HistoryContent {

    private String id;
    private String className;
    private String createdDate;
    private String actor = "system";
    private String methodName;
    private Map<String, Object> object; //для хранения состояния JSON
    private String status;

    public enum Status{
        SUCCESS, FAULT
    }

    public HistoryContent() {
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}

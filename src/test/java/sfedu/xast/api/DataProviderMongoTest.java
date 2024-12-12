package sfedu.xast.api;

import org.junit.jupiter.api.Test;
import sfedu.xast.models.HistoryContent;

import java.util.Map;

public class DataProviderMongoTest {

    @Test
    public void testCRUDWithMongoDB(){
        DataProviderMongo dataProvider = new DataProviderMongo("test", "historyContent");

        HistoryContent content = new HistoryContent();
        content.setId("1");
        content.setClassName("ExampleClass");
        content.setMethodName("exampleMethod");
        content.setObject(Map.of("key1", "value1", "key2", "value2"));
        content.setStatus(HistoryContent.Status.SUCCESS);

        dataProvider.insertHistoryContent(content);

        HistoryContent retrieved = dataProvider.findHistoryContentById("1");
        System.out.println(retrieved);

        content.setStatus(HistoryContent.Status.FAULT);
        dataProvider.updateHistoryContent(content);

        dataProvider.deleteHistoryContent("1");
    }
}

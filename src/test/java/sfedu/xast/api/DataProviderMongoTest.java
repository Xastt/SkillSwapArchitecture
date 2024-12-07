package sfedu.xast.api;


import org.junit.Test;
import sfedu.xast.models.HistoryContent;

import java.util.List;
import java.util.Map;

public class DataProviderMongoTest{

    @Test
    public void testCRUDMongoOperations() {
        DataProviderMongo dataProvider = new DataProviderMongo();

        HistoryContent newEntry = new HistoryContent();
        newEntry.setClassName("YourClassName");
        newEntry.setActor("user");
        newEntry.setMethodName("methodName");
        newEntry.setObject(Map.of("Architecture", "So good!"));
        newEntry.setStatus("SUCCESS");

        dataProvider.create(newEntry);

        List<HistoryContent> allEntries = dataProvider.findAll();
        allEntries.forEach(entry -> System.out.println(entry.getClassName()));

        dataProvider.close();
    }



}
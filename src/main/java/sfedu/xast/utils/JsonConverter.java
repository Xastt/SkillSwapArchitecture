package sfedu.xast.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import sfedu.xast.models.HistoryContent;
import java.util.List;
import java.util.Map;

public class JsonConverter {

    private final ObjectMapper mapper = new ObjectMapper();

    protected <T> List<T> jsonArrayToObjectList(List<Map<String, Object>> map, Class<T> tClass){
        try{
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, tClass);
            return mapper.convertValue(map, listType);
        }catch (Exception e){
            throw new ClassCastException(e.getMessage());
        }
    }

    public Map<String, Object> convertToJson(HistoryContent historyContent){
        return mapper.convertValue(historyContent, new TypeReference<Map<String, Object>>(){});
    }
}

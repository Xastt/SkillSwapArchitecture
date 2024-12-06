package sfedu.xast.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfedu.xast.models.HistoryContent;
import sfedu.xast.repository.HistoryContentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoryContentService {

    @Autowired
    private HistoryContentRepository historyContentRepository;

    protected <T> List<T> jsonArrayToObjectList(List<Map<String, Object>> map, Class<T> tClass){
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(map, mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass));
        }catch (Exception e){
            throw new ClassCastException(e.getMessage());
        }
    }

    public Map<String, Object> objectToJson(HistoryContent historyContent){
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("id", historyContent.getId());
        jsonObject.put("className", historyContent.getClassName());
        jsonObject.put("createdDate", historyContent.getCreatedDate());
        jsonObject.put("actor", historyContent.getActor());
        jsonObject.put("methodName", historyContent.getMethodName());
        jsonObject.put("object", historyContent.getObject());
        jsonObject.put("status", historyContent.getStatus());
        return jsonObject;
    }
}

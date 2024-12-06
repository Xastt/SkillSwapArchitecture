package sfedu.xast.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sfedu.xast.models.HistoryContent;

@Repository
public interface HistoryContentRepository extends MongoRepository<HistoryContent, String> {
    //пока что тут пусто....
}

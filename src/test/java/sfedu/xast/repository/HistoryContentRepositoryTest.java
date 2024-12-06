package sfedu.xast.repository;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import sfedu.xast.models.HistoryContent;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@DataMongoTest
public class HistoryContentRepositoryTest extends TestCase {

    @Autowired
    private HistoryContentRepository repository;

    @Test
    public void testSave() {
        HistoryContent historyContent = new HistoryContent();
        historyContent.setClassName("TestClass");
        historyContent.setMethodName("testMethod");
        historyContent.setStatus(HistoryContent.Status.SUCCESS);

        HistoryContent saved = repository.save(historyContent);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getClassName()).isEqualTo("TestClass");
        assertThat(saved.getMethodName()).isEqualTo("testMethod");
        assertThat(saved.getStatus()).isEqualTo(HistoryContent.Status.SUCCESS);
    }



}
package sfedu.xast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfedu.xast.repository.HistoryContentRepository;

@Service
public class HistoryContentService {

    private final HistoryContentRepository historyContentRepository;

    @Autowired
    public HistoryContentService(HistoryContentRepository historyContentRepository) {
        this.historyContentRepository = historyContentRepository;
    }
}

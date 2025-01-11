package sfedu.xast.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class PersInfList {
    @ElementList(inline = true)
    private List<PersForApi> records;

    public PersInfList() {
        this.records = new ArrayList<>();
    }

    public PersInfList(List<PersForApi> records) {
        this.records = records;
    }

    public List<PersForApi> getRecords() {
        return records;
    }

    public void setRecords(List<PersForApi> records) {
        this.records = records;
    }
}

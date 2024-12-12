package sfedu.xast.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class PersInfList {
    @ElementList(inline = true)
    private List<PersInf> records;

    public PersInfList() {
        this.records = new ArrayList<>();
    }

    public PersInfList(List<PersInf> records) {
        this.records = records;
    }

    public List<PersInf> getRecords() {
        return records;
    }

    public void setRecords(List<PersInf> records) {
        this.records = records;
    }
}

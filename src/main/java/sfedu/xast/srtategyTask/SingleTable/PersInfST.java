package sfedu.xast.srtategyTask.SingleTable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import sfedu.xast.srtategyTask.MappedSuperclass.ProfInfMS;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PERS_INF")
public class PersInfST extends PersonST {

    @OneToMany(mappedBy = "pers", cascade = CascadeType.ALL)
    private List<ProfInfST> providedSkills = new ArrayList<>();

    public PersInfST() {
        super();
    }

    public PersInfST(String surname, String name, String phoneNumber, String email) {
        super(surname, name, phoneNumber, email);
    }

    public List<ProfInfST> getProvidedSkills() {
        return providedSkills;
    }

    public void setProvidedSkills(List<ProfInfST> providedSkills) {
        this.providedSkills = providedSkills;
    }
}
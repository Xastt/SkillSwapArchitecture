package sfedu.xast.srtategyTask.TablePerClass;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import sfedu.xast.models.ProfInf;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persinf_tpc")
public class PersInfTPC extends PersonTPC {

    @OneToMany(mappedBy = "pers", cascade = CascadeType.ALL)
    private List<ProfInf> providedSkills = new ArrayList<>();

    public PersInfTPC() {
        super();
    }

    public PersInfTPC(String surname, String name, String phoneNumber, String email) {
        super(surname, name, phoneNumber, email);
    }

    public List<ProfInf> getProvidedSkills() {
        return providedSkills;
    }

    public void setProvidedSkills(List<ProfInf> providedSkills) {
        this.providedSkills = providedSkills;
    }
}


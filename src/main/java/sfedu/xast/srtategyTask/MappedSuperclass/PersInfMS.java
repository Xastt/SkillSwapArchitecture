package sfedu.xast.srtategyTask.MappedSuperclass;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persinf_ms")
public class PersInfMS extends PersonMS {

    @OneToMany(mappedBy = "pers", cascade = CascadeType.ALL)
    private List<ProfInfMS> providedSkills = new ArrayList<>();

    public PersInfMS() {
        super();
    }

    public PersInfMS(String surname, String name, String phoneNumber, String email) {
        super(surname, name, phoneNumber, email);
    }

    public List<ProfInfMS> getProvidedSkills() {
        return providedSkills;
    }

    public void setProvidedSkills(List<ProfInfMS> providedSkills) {
        this.providedSkills = providedSkills;
    }
}

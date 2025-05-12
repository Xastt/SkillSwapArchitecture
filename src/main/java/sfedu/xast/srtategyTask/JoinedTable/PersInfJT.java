package sfedu.xast.srtategyTask.JoinedTable;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persinf")
@PrimaryKeyJoinColumn(name = "person_id")
public class PersInfJT extends PersonJT{

    @OneToMany(mappedBy = "pers", cascade = CascadeType.ALL)
    private List<ProfInfJT> providedSkills = new ArrayList<>();

    public PersInfJT() {
        super();
    }

    public PersInfJT(String surname, String name, String phoneNumber, String email) {
        super(surname, name, phoneNumber, email);
    }

    public List<ProfInfJT> getProvidedSkills() {
        return providedSkills;
    }

    public void setProvidedSkills(List<ProfInfJT> providedSkills) {
        this.providedSkills = providedSkills;
    }
}

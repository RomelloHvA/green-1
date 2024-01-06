package app.models;
/**
 * Model for the sdgs
 * @author Romello ten Broeke
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Sdg {
    @Id
    private Long id;
    @Column(name = "sdg_title")
    private String sdgTitle;
    @Column(name = "sdg_description")
    private String sdgDescription;

    public Sdg(){}

    public Sdg(Long id, String sdgTitle, String sdgDescription) {
        this.id = id;
        this.sdgTitle = sdgTitle;
        this.sdgDescription = sdgDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSdgTitle() {
        return sdgTitle;
    }

    public void setSdgTitle(String sdgTitle) {
        this.sdgTitle = sdgTitle;
    }

    public String getSdgDescription() {
        return sdgDescription;
    }

    public void setSdgDescription(String sdgDescription) {
        this.sdgDescription = sdgDescription;
    }
}

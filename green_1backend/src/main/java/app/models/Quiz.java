package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.type.NumericBooleanConverter;

import java.util.List;
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quizName;

    @Column(name = "is_published", columnDefinition = "TINYINT", length = 1)
    private boolean isPublished;

    @Column(name = "is_concept", columnDefinition = "TINYINT", length = 1)
    private boolean isConcept;

    @Column(name = "is_live", columnDefinition = "TINYINT", length = 1)
    private boolean isLive;

    @ManyToOne
    private Sector sector;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @OrderBy("questionIndex ASC")
    private List<Question> quizQuestions;

    public Quiz(){}

    public void setIsLive(boolean live) {
        isLive = live;
    }

    public void setSector(Sector sector){
        this.sector = sector;
    }

    public Sector getSector(){
        return sector;
    }

    public boolean getIsPublished() {
        return isPublished;
    }

    public boolean getIsConcept() {
        return isConcept;
    }

    public boolean getIsLive() {
        return isLive;
    }


    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public void setIsConcept(boolean isConcept) {
        this.isConcept = isConcept;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setQuizQuestions(List<Question> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public Long getId() {
        return id;
    }

    public String getQuizName() {
        return quizName;
    }

    public List<Question> getQuizQuestions() {
        return quizQuestions;
    }
}

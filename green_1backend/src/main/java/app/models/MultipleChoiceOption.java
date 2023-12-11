package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "multiplechoiceoption")
public class MultipleChoiceOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "multiplechoice_option")
    private String option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multiplechoicequestion_id")
    @JsonBackReference
    private MultipleChoiceQuestion multipleChoiceQuestion;

    private int sdg;

    public MultipleChoiceOption() {

    }

    public int getSDG() {
        return sdg;
    }

    public void setSDG(int SDG) {
        this.sdg = SDG;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.multipleChoiceQuestion = multipleChoiceQuestion;
    }

    public Long getId() {
        return id;
    }

    public String getOption() {
        return option;
    }

    public MultipleChoiceQuestion getMultipleChoiceQuestion() {
        return multipleChoiceQuestion;
    }
}

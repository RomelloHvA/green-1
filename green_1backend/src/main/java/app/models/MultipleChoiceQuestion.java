package app.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "MultipleChoiceQuestion")
@Table(name = "multiplechoicequestion")
public class MultipleChoiceQuestion extends Question {

    @OneToMany(mappedBy = "multipleChoiceQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MultipleChoiceOption> options = new ArrayList<>();
    @Column(name = "answer_limit")
    private Integer answerLimit;

    private static final String TYPE = "multiplechoice";



    public MultipleChoiceQuestion() {}

    public MultipleChoiceQuestion(Long id, String question, String imgPath, Quiz quiz, int questionIndex, Integer answerLimit) {
        super(id, question, imgPath, questionIndex, quiz);
        this.answerLimit = answerLimit;
    }
    @JsonProperty("type")
    public String getType() {
        return TYPE;
    }

    public List<MultipleChoiceOption> getOptions() {
        return options;
    }

    public void setOptions(List<MultipleChoiceOption> options) {
        this.options = options;
    }

    public void setAnswerLimit(Integer answerLimit) {
        this.answerLimit = answerLimit;
    }


    public Integer getAnswerLimit() {
        return answerLimit;
    }
}

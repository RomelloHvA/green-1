package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = YesNoQuestion.class, name = "yesno"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "multiplechoice")
})
public abstract class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private String question;

    private String imgPath;

    @Column(name = "question_index")
    private int questionIndex;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id")
    @JsonBackReference
    private Quiz quiz;

    public int getIndex() {
        return questionIndex;
    }

    public void setIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }


    public Quiz getQuiz() {
        return quiz;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }
}

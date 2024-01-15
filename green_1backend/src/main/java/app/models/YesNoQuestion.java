package app.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "YesNoQuestion")
@Table(name = "yesnoquestion")
public class YesNoQuestion extends Question {

    private static final String TYPE = "yesno";

    private int sdg;
    public YesNoQuestion() {}

    public YesNoQuestion(Long id, String question, String imgPath, Quiz quiz, int questionIndex, int sdg) {
        super(id, question, imgPath, questionIndex, quiz);
        this.sdg = sdg;
    }
    @JsonProperty("type")

    public String getType() {
        return TYPE;
    }

    public int getSdg() {
        return sdg;
    }

    public void setSdg(int sdg) {
        this.sdg = sdg;
    }
}

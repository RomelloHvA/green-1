package app.models;

import app.repositories.Identifiable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * This is a model of a quiz result.
 * @author santoshkakkar
 */
@Entity
@Table(name = "quiz_result")
@NamedQueries({
        @NamedQuery(name = "Get_quiz_result_by_user_id", query = "SELECT q FROM QuizResult q WHERE q.user = ?1"),
})
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long resultId;

    @ElementCollection
    private Set<Long> sdgArray;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private LocalDate dateOfQuiz;

    /**
     * Instantiates a new Quiz result.
     *
     * @param sdgArray   the sdg array
     * @param user       the user that is later used to get the user id
     * @param dateOfQuiz the date of quiz
     */
    public QuizResult(Set<Long> sdgArray, User user, LocalDate dateOfQuiz) {
        this.sdgArray = sdgArray;
        this.user = user;
        this.dateOfQuiz = dateOfQuiz;
    }

    public QuizResult() {
    }

    /**
     * Gets sdg array.
     *
     * @return the sdg array
     */
    public Set<Long> getSdgArray() {
        return sdgArray;
    }

    /**
     * Sets sdg array.
     *
     * @param sdgArray the sdg array
     */
    public void setSdgArray(Set<Long> sdgArray) {
        this.sdgArray = sdgArray;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets date of quiz.
     *
     * @return the date of quiz
     */
    public LocalDate getDateOfQuiz() {
        return dateOfQuiz;
    }

    /**
     * Sets date of quiz.
     *
     * @param dateOfQuiz the date of quiz
     */
    public void setDateOfQuiz(LocalDate dateOfQuiz) {
        this.dateOfQuiz = dateOfQuiz;
    }

    /**
     * Sets result id.
     *
     * @param resultId the result id
     */
    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    /**
     * Gets result id.
     *
     * @return the result id
     */
    public Long getResultId() {
        return resultId;
    }



}

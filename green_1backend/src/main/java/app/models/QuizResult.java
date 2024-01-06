package app.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class QuizResult {

    @Id
    private Long resultId;

    @ElementCollection
    private Set<Long> sdgArray;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate dateOfQuiz;

    public QuizResult(Long resultId, Set<Long> sdgArray, User user, LocalDate dateOfQuiz) {
        this.resultId = resultId;
        this.sdgArray = sdgArray;
        this.user = user;
        this.dateOfQuiz = dateOfQuiz;
    }

    public QuizResult() {

    }

    public Set<Long> getSdgArray() {
        return sdgArray;
    }

    public void setSdgArray(Set<Long> sdgArray) {
        this.sdgArray = sdgArray;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDateOfQuiz() {
        return dateOfQuiz;
    }

    public void setDateOfQuiz(LocalDate dateOfQuiz) {
        this.dateOfQuiz = dateOfQuiz;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Long getResultId() {
        return resultId;
    }
}

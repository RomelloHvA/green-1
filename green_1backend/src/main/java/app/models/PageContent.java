package app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "PageContent")
public class PageContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contentId;
    @Column
    private String contentTitle;
    @Column
    private String contentDutch;
    @Column
    private String contentEnglish;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pageId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Page fkPage;
    @Column
    private String contentConcept;

    public PageContent(){}

    public PageContent(int contentId, String contentTitle, String contentDutch, String contentEnglish, Page fkPage, String contentConcept) {
        this.contentId = contentId;
        this.contentTitle = contentTitle;
        this.contentDutch = contentDutch;
        this.contentEnglish = contentEnglish;
        this.fkPage = fkPage;
        this.contentConcept = contentConcept;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDutch() {
        return contentDutch;
    }

    public void setContentDutch(String contentDutch) {
        this.contentDutch = contentDutch;
    }

    public String getContentEnglish() {
        return contentEnglish;
    }

    public void setContentEnglish(String contentEnglish) {
        this.contentEnglish = contentEnglish;
    }

    public Page getFkPage() {
        return fkPage;
    }

    public void setFkPage(Page page) {
        this.fkPage = page;
    }

    public String getContentConcept() {
        return contentConcept;
    }

    public void setContentConcept(String contentConcept) {
        this.contentConcept = contentConcept;
    }
}

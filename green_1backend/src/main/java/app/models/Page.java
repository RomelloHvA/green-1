package app.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Page")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pageId;
    @Column(name = "pageTitle")
    private String pageTitle;

    @OneToMany(mappedBy = "fkPage")
    private List<PageContent> pageContents;
    public Page(Long pageId, String pageTitle) {
        this.pageId = pageId;
        this.pageTitle = pageTitle;
    }

    public Page() {

    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long page_id) {
        this.pageId = page_id;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String page_title) {
        this.pageTitle = page_title;
    }
}

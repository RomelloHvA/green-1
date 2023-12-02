package app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;

    @Column(name = "imagePath")
    private String imagePath;

    @Column(name = "imageName")
    private String imageName;

    @OneToOne
    @JoinColumn(name = "pageId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Page fkPageImage;

    public Image() {
    }

    public Image(Long imageId, String imagePath, String imageName, Page fkPageImage) {
        this.imageId = imageId;
        this.imagePath = imagePath;
        this.imageName = imageName;
        this.fkPageImage = fkPageImage;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setFkPageImage(Page fkPageImage) {
        this.fkPageImage = fkPageImage;
    }

    public Long getImageId() {
        return imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public Page getFkPageImage() {
        return fkPageImage;
    }

}

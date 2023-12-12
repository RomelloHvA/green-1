package app.models;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "action_plan")
public class ActionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @ManyToOne (cascade = CascadeType.MERGE)
    private Sector sector;
    @ElementCollection
    private Set<Long> sdgArray;

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Long> getSdgArray() {
        return sdgArray;
    }

    public void setSdgArray(Set<Long> sdgId) {
        this.sdgArray = sdgId;
    }

    public ActionPlan() {

    }

    //    public static List<ActionPlan> createSamplePlan(int id) {
//        List<ActionPlan> plans = new ArrayList<>();
//
//        for (int i = 1; i <= 2; i++) {
//            plans.add(new ActionPlan((id * 2 - 2) + i, id, makeTitle(id), "This is a sample description"));
//        }
//
//        return plans;
//    }

    private static String makeTitle(int code) {
        switch (code) {
            case 1: {
                return "No Poverty";
            }
            case 2: {
                return "Zero Hunger";
            }
            case 3: {
                return "Good Health and Well Being";
            }
            case 4: {
                return "Quality Education";
            }
            case 5: {
                return "Gender Equality";
            }
            case 6: {
                return "Clean Water and Sanitation";
            }
            case 7: {
                return "Affordable and Clean Energy";
            }
            case 8: {
                return "Decent Work and Economic Growth";
            }
            case 9: {
                return "Industry, Innovation, and Infrastructure";
            }
            case 10: {
                return "Reduced Inequality";
            }
            case 11: {
                return "Sustainable Cities and Communities";
            }
            case 12: {
                return "Responsible Consumption and Production";
            }
            case 13: {
                return "Climate Action";
            }
            case 14: {
                return "Life Below Water";
            }
            case 15: {
                return "Life on Land";
            }
            case 16: {
                return "Peace, Justice, and Strong Institutions";
            }
            case 17: {
                return "Partnerships for the Goals";
            }
            // Add cases for other numeric values as needed
            default: {
                return "No Title";
            }
        }
    }

}

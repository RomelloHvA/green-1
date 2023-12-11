package app.models;

import java.util.ArrayList;
import java.util.List;

public class ActionPlan {
    int id;
    int sdgId;
    String title;
    String description;

    public ActionPlan(int id, int sdgId, String title, String description) {
        this.id = id;
        this.sdgId = sdgId;
        this.title = title;
        this.description = description;
    }

    public static List<ActionPlan> createSamplePlan(int id) {
        List<ActionPlan> plans = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            plans.add(new ActionPlan((id * 2 - 2) + i, id, makeTitle(id), "This is a sample description"));
        }

        return plans;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSdgId() {
        return sdgId;
    }

    public void setSdgId(int sdgId) {
        this.sdgId = sdgId;
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
}

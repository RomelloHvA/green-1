package app.models;

import java.util.Random;

public class Goal {
    private int id;
    private int user_id;
    private String image;
    private String title;
    private int sdgId;

    public Goal(int id, int user_id, String title, int sdgId) {
        this.id = id;
        this.user_id = user_id;
        this.image = makeImage(sdgId);
        this.title = title;
        this.sdgId = sdgId;
    }

    public Goal() {
    }

    public Goal(int id, int user_id, String title) {
        this.id = id;
        this.user_id = user_id;
        this.image = image;
        this.title = title;
        this.sdgId = sdgId;
    }

    public static Goal createSampleGoal(int id, int userId) {
        Random randomizer = new Random();
        int sdgId = randomizer.nextInt(17) + 1;
        return new Goal(id, userId, makeTitle(sdgId), sdgId);
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


    /**
     * Function assigns the icon to its goal
     *
     * @return {*}
     * @author Justin Chan
     */
    private String makeImage(int sdgId) {
        switch (sdgId) {
            case 1: {
                return image = "E-WEB-Goal-01.png";
            }
            case 2: {
                return image = "E-WEB-Goal-02.png";
            }
            case 3: {
                return image = "E-WEB-Goal-03.png";
            }
            case 4: {
                return image = "E-WEB-Goal-04.png";
            }
            case 5: {
                return image = "E-WEB-Goal-05.png";
            }
            case 6: {
                return image = "E-WEB-Goal-06.png";
            }
            case 7: {
                return image = "E-WEB-Goal-07.png";
            }
            case 8: {
                return image = "E-WEB-Goal-08.png";
            }
            case 9: {
                return image = "E-WEB-Goal-09.png";
            }
            case 10: {
                return image = "E-WEB-Goal-10.png";
            }
            case 11: {
                return image = "E-WEB-Goal-11.png";
            }
            case 12: {
                return image = "E-WEB-Goal-12.png";
            }
            case 13: {
                return image = "E-WEB-Goal-13.png";
            }
            case 14: {
                return image = "E-WEB-Goal-14.png";
            }
            case 15: {
                return image = "E-WEB-Goal-15.png";
            }
            case 16: {
                return image = "E-WEB-Goal-16.png";
            }
            case 17: {
                return image = "E-WEB-Goal-17.png";
            }
            default: {
                return image = "@/assets/img/logos/logo.png";
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSdgId() {
        return sdgId;
    }

    public void setSdgId(int sdgId) {
        this.sdgId = sdgId;
    }
}

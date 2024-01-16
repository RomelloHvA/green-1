package app.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Profile {

    private int user_id;
    private String first_name;
    private String last_name;
    private String bio;
    private String occupation;
    private LocalDate date_of_birth;
    private String photo;
    private List<Goal> goals;

    private static final String[] FIRST_NAMES = {"John", "Mary", "David", "Lisa", "Michael", "Sarah"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Taylor", "Clark", "Wilson"};

    public Profile(int user_id, String first_name, String last_name, String bio, String occupation, LocalDate date_of_birth, String photo, List<Goal> goals) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.bio = bio;
        this.occupation = occupation;
        this.date_of_birth = date_of_birth;
        this.photo = photo;
        this.goals = goals;
    }
    public Profile() {

    }

    public Profile(int user_id, String first_name, String last_name) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Profile(int user_id, String first_name, String last_name, String bio, String occupation, LocalDate date_of_birth, String photo) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.bio = bio;
        this.occupation = occupation;
        this.date_of_birth = date_of_birth;
        this.photo = photo;
    }

//    public static Profile createSampleProfile(int id, List<Goal> goal) {
//        List<Goal> goals = goal != null ? goal : new ArrayList<>();
//        Random random = new Random();
//        Profile newProfile = new Profile(id, FIRST_NAMES[random.nextInt(FIRST_NAMES.length)],
//                LAST_NAMES[random.nextInt(LAST_NAMES.length)], "Hi I'm a Software Engineer at the HvA!",
//                "Software Engineer",
//                LocalDate.of(1999, 12, 12),
//                "/assets/photo/profielfoto-silhouet.jpg");
//        newProfile.setGoals(goals);
//        return newProfile;
//    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}

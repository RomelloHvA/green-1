package app.models;

import java.time.LocalDate;
import java.util.Random;

public class User {

    private int user_id;
    private int sector_id;
    private String first_name;
    private String last_name;
    private String email;
    private int security_clearance;
    private String password;
    private String username;
    private String bio;
    private String occupation;
    private LocalDate date_of_birth;
    private String postalcode;

    private static final String[] FIRST_NAMES = {"John", "Mary", "David", "Lisa", "Michael", "Sarah"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Taylor", "Clark", "Wilson"};

    public User(int user_id, String first_name, String last_name) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public static User createSampleUser() {
        Random random = new Random();
        return new User(random.nextInt(1000),FIRST_NAMES[random.nextInt(FIRST_NAMES.length)],
                LAST_NAMES[random.nextInt(LAST_NAMES.length)] );


    }

    public User(int user_id, int sector_id, String first_name, String last_name, String email, int security_clearance, String password, String username, String bio, String occupation, LocalDate date_of_birth, String postalcode) {
        this.user_id = user_id;
        this.sector_id = sector_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.security_clearance = security_clearance;
        this.password = password;
        this.username = username;
        this.bio = bio;
        this.occupation = occupation;
        this.date_of_birth = date_of_birth;
        this.postalcode = postalcode;
    }
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setSector_id(int sector_id) {
        this.sector_id = sector_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSecurity_clearance(int security_clearance) {
        this.security_clearance = security_clearance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public int getUser_id() {
        return user_id;
    }

    public int getSector_id() {
        return sector_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public int getSecurity_clearance() {
        return security_clearance;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getBio() {
        return bio;
    }

    public String getOccupation() {
        return occupation;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public String getPostalcode() {
        return postalcode;
    }
}

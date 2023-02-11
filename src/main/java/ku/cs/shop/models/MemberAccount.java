package ku.cs.shop.models;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemberAccount {
    private String name;
    private String username;
    private String password;
    private String role;
    private String shopName;
    private String profilePic;
    private String time;

    public MemberAccount(String name,String username,String password,String role,String shopName,String profilePic,String time){
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.shopName = shopName;
        this.profilePic = profilePic;
        this.time = time;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
    public boolean checkUsername(String username){
        return this.username.equals(username);
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyy - kk:mm:ss");
        this.time = now.format(formatter);
    }

    public void setRoleToSeller() {this.role = "seller";}
    public void setProfilePic(){
        this.profilePic = this.username + ".jpg";
    }
    public void resetProfilePic(){
        this.profilePic = "default.jpg";
    }

    public void changePassword(String password){
        this.password = password;
    }
    public void changeShopName(String shopName) { this.shopName = shopName; }

    public String getTime() {
        return time;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getShopName() {
        return shopName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String toCsv(){
        return name + "," + username + "," + password + "," + role + "," + shopName + "," + profilePic + "," + time ;
    }

    public String toString(){
        return name;
    }
}
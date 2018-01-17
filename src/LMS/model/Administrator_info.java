package LMS.model;

public class Administrator_info {
    public String admin_id;
    public String admin_name;
    public String date_in;
    public boolean isWorking;
    private String password;

    public Administrator_info(String admin_id, String admin_name, String date_in, Boolean isWorking)
    {
        setAdmin_id(admin_id);
        setAdmin_name(admin_name);
        setDate_in(date_in);
        setWorking(isWorking);
    }
    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }
    public String getAdmin_id() {
        return admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getDate_in() {
        return date_in;
    }

    public void setDate_in(String date_in) {
        this.date_in = date_in;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
}

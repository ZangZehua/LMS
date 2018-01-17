package LMS.model;

public class Reader_info{
    private String reader_id;
    private String reader_name;
    private String reader_sex;
    private String birthday;
    private String number;
    private String phone;
    private String cardtype;
    private int cardID;
    private int level;
    private String date_in;
    private double arrears;
    private String password;

    public Reader_info(String reader_id, String reader_name, String reader_sex, String birthday, String number, String phone, String cardtype,int cardID,int level,String date_in,double arrears)
    {
        setArrears(arrears);
        setLevel(level);
        setPhone(phone);
        setNumber(number);
        setBirthday(birthday);
        setReader_sex(reader_sex);
        setReader_name(reader_name);
        setReader_id(reader_id);
        setDate_in(date_in);
        setCardID(cardID);
        setCardtype(cardtype);
    }

    public String getReader_id() {
        return reader_id;
    }

    public void setReader_id(String reader_id) {
        this.reader_id = reader_id;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setArrears(double arrears) {
        this.arrears = arrears;
    }

    public String getCardtype() {
        return cardtype;
    }

    public int getCardID() {
        return cardID;
    }

    public double getArrears() {
        return arrears;
    }

    public String getDate_in() {
        return date_in;
    }

    public void setDate_in(String date_in) {
        this.date_in = date_in;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getReader_sex() {
        return reader_sex;
    }

    public void setReader_sex(String reader_sex) {
        this.reader_sex = reader_sex;
    }

    public String getReader_name() {
        return reader_name;
    }

    public void setReader_name(String reader_name) {
        this.reader_name = reader_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

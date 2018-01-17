package LMS.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import javax.xml.crypto.Data;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectDB
{
    //驱动程序名
    final static String driver = "com.mysql.jdbc.Driver";
    //URL指向要访问的数据库名LMS
    final static String url = "jdbc:mysql://127.0.0.1:3306/db_lms";
    //MySQL配置时的用户名
    final static String user = "root";
    //MySQL配置时的密码
    final static String password = "951997";
    public Connection connection = null;
    public Statement statement = null;
    public ResultSet resultSet = null;

    public static Reader_info reader_info;//save reader's information
    public static Administrator_info administrator_info;//admin info

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    long current_Date = System.currentTimeMillis();//new日期对象
    Date current_Date1 = new Date(current_Date);//转换提日期输出格式

    public ConnectDB() {
        try {
            Class.forName(driver);//指定连接类型
            connection = DriverManager.getConnection(url, user, password);//获取连接
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //use for log in
    public int getUserInfo(String username_reader_in, String password_reader_in)
    {
        String username_reader = null;
        String password_reader = null;
        int user_type = -1;
        String tempString = "select * from userinfo where 证件编号 = \"" + username_reader_in + "\"";
        int column;//表格列数
        if(username_reader_in == null || password_reader_in == null)
        {
            return -1;//didn't get the username and password from controller
        }
        try {
            resultSet = statement.executeQuery(tempString);
            while(resultSet.next())
            {
                username_reader = resultSet.getString("证件编号");
                password_reader = resultSet.getString("密码");
                user_type = resultSet.getInt("用户类型");
                if(username_reader == null)
                {
                    return -3;//the reader hasn't registered
                }
                else
                {
                    if(password_reader.equals(password_reader_in))
                    {
                        //reader
                        if(user_type == 0)
                        {
                            resultSet = statement.executeQuery("select * from readers where 读者证编号 = \"" + username_reader_in + "\"");
                            while(resultSet.next()) {
                                reader_info = new Reader_info(resultSet.getString("读者证编号"), resultSet.getString("姓名"), resultSet.getString("性别"), resultSet.getString("出生日期"), resultSet.getString("联系电话"), resultSet.getString("手机"), resultSet.getString("证件名称"), resultSet.getInt("证件编号"), resultSet.getInt("会员级别"), resultSet.getString("办证日期"),0);
                            }
                            reader_info.setPassword(password_reader);
                        }
                        else if(user_type == 1)
                        {
                            resultSet = statement.executeQuery("SELECT * FROM admin where 管理员编号 = \"" + username_reader_in + "\"");
                            while(resultSet.next()) {
                                administrator_info = new Administrator_info(resultSet.getString("管理员编号"), resultSet.getString("管理员姓名"), resultSet.getString("入职日期"), resultSet.getBoolean("是否在职"));
                            }
                            administrator_info.setPassword(password_reader);
                        }
                        return user_type;//password correct and return the user_type
                    }
                    else
                    {
                        return -2;//password incorrect
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -3;
    }
    //use for register
    public int checkUser(String username_reader_in)
    {
        String username_reader = null;
        String tempString = "select 读者证编号 from readers where 读者证编号 = \"" + username_reader_in + "\"";
        if(username_reader_in == null)
        {
            return -1;//didn't get the username and password from controller
        }
        try {
            resultSet = statement.executeQuery(tempString);
            while(resultSet.next())
            {
                username_reader = resultSet.getString("读者证编号");
                if(username_reader != null)
                {
                    return -2;//the reader has registered
                }
                else if (username_reader == null)
                {
                    return -3;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -3;
    }
    //获取已过期图书
    private static ObservableList<Books_expired> books_expireds;
    public ObservableList<Books_expired> findaBooks_expireds(String reader_id, String book_id) {
        Books_expired tempBooks_expired = null;
        books_expireds = FXCollections.observableArrayList();
        long date_in = 0;
        lineCount = 0;
        String date_inString;
        String tempString = "select * from borrow where 读者证编号 like \"%%" + reader_id + "%%\" and 图书编号 like \"%%" + book_id + "%%\"";
        try {
            resultSet = statement.executeQuery(tempString);
            while(resultSet.next()) {
                date_inString = resultSet.getString("应还日期");
                try {
                    date_in = dateFormat.parse(date_inString).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(date_in > current_Date)
                {
                    continue;
                }
                lineCount++;
                tempBooks_expired = new Books_expired(resultSet.getString("读者证编号"), resultSet.getString("图书编号"), resultSet.getString("借阅日期"),resultSet.getString("应还日期"),resultSet.getBoolean("是否遗失"));
                books_expireds.add(tempBooks_expired);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books_expireds;
    }
    private static int lineCount = 0;
    public int getLineCount(String reader_id)
    {
        findaBooks_expireds(reader_id,"");
        return lineCount;
    }

    //获取已借图书
    private static ObservableList<Books_borrowed> books_borrowed;
    public ObservableList<Books_borrowed> findabook_borrowed(String reader_id, String book_id)
    {
        Books_borrowed tempBooks_borrowed = null;
        books_borrowed = FXCollections.observableArrayList();
        String tempString = "select * from borrow where 读者证编号 like \"%%" + reader_id + "%%\" and 图书编号 like \"%%" + book_id + "%%\"";
        try {
            resultSet = statement.executeQuery(tempString);
            while(resultSet.next()) {
                tempBooks_borrowed = new Books_borrowed(resultSet.getString("读者证编号"), resultSet.getString("图书编号"), resultSet.getString("借阅日期"),resultSet.getString("应还日期"),resultSet.getBoolean("是否遗失"));

                books_borrowed.add(tempBooks_borrowed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books_borrowed;
    }
    public boolean borrowBook(String reader_id, String book_id, String date_out, String date_in)
    {
        int booksHave = checkBooksHave(reader_id);
        booksHave++;
        int stock = checkBookOut(book_id);
        stock++;
        String sql_readers_borrow = "update readers_borrow set 已借书数 = " + booksHave + " where 读者证编号 = \"" + reader_id + "\"";
        String sql_borrow = "insert borrow values (\"" + reader_id + "\",\"" + book_id + "\",\"" + date_out + "\",\"" + date_in + "\"," + false +  ")";
        String sql_books = "update books set 借出数量 = " + stock + " where 图书编号 = \"" + book_id + "\"";
        try {
            statement.execute(sql_readers_borrow);
            statement.execute(sql_borrow);
            statement.execute(sql_books);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void returnBook(String reader_id, String book_id)
    {
        int num_out = checkBookOut(book_id);
        num_out--;
        System.out.println(num_out);
        int booksHave = checkBooksHave(reader_id);
        booksHave--;
        String sql_borrow = "delete from borrow where 读者证编号 = \"" + reader_id + "\" and 图书编号 = \"" + book_id + "\"";
        String sql_books = "update books set 借出数量 = " + num_out + " where 图书编号 = \"" + book_id + "\"";
        String sql_readers_borrow = "update readers_borrow set 已借书数 = " + booksHave + " where 读者证编号 = \"" + reader_id + "\"";
        try {
            statement.execute(sql_borrow);
            statement.execute(sql_books);
            statement.execute(sql_readers_borrow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //图书入库，已完成
    public void Book_in(String bookid, String bookname, String author, String publishing, String category, double price, String datein, int numin, int numout, int numlost)
    {
        String sql = "insert into books values (\"" + bookid+ "\",\"" + bookname + "\",\"" + author + "\",\"" + publishing + "\",\"" + category + "\"," +  price + ",\"" + datein + "\"," + numin + "," + numout + "," + numlost +")";
        try {
            statement.execute(sql);
            System.out.println("ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //更改密码，已完成
    public void ChangePW(String newPW)
    {
        String sql = "update userinfo set 密码 = \"" + newPW + "\" where 证件编号 = \"" + administrator_info.getAdmin_id() + "\"";
        try {
            statement.execute(sql);
            System.out.println("changing password succeed");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //查找图书 已完成
    private static ObservableList<Books> books_found;
    public ObservableList<Books> findabook(String bookid, String bookname, String author)
    {
        Books tempBooks = null;
        books_found = FXCollections.observableArrayList();
        String tempString = "select * from books where 图书编号 like \"%%" + bookid + "%%\" and 书名 like \"%%" + bookname + "%%\" and 作者 like \"%%" + author + "%%\"";
        try {
            resultSet = statement.executeQuery(tempString);
            while(resultSet.next()) {
                tempBooks = new Books(resultSet.getString("图书编号"), resultSet.getString("书名"), resultSet.getString("作者"),resultSet.getString("出版社"),resultSet.getString("类别"),resultSet.getDouble("单价"),resultSet.getString("入库日期"),resultSet.getInt("入库数量"),resultSet.getInt("借出数量"),resultSet.getInt("遗失数量"));
                books_found.add(tempBooks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books_found;
    }
    //查找挂失用户
    private static ObservableList<Loss_report> loss_reports;
    public ObservableList<Loss_report> findLoss_report(String reader_id) {
        Loss_report tempLoss_report = null;
        loss_reports = FXCollections.observableArrayList();
        String tempString = "select * from loss_reporting where 读者证编号 like \"%%" + reader_id + "%%\"";
        try {
            resultSet = statement.executeQuery(tempString);
            while(resultSet.next()) {
                tempLoss_report = new Loss_report(resultSet.getString("读者证编号"),resultSet.getString("挂失日期"));
                loss_reports.add(tempLoss_report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loss_reports;
    }
    public int lossReport(String reader_id, String date)
    {
        String sql = "insert loss_reporting values (\"" + reader_id + "\",\"" + date + "\")";
        try {
            statement.execute(sql);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int cancelreport(String reader_id)
    {
        String sql = "delete from loss_reporting where 读者证编号 = \"" + reader_id + "\"";
        try {
            statement.execute(sql);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //检查读者是否挂失
    public int checkReaderReport(String reader_id)
    {
        String reader = null;
        String tempString = "select * from loss_reporting where 读者证编号 = \"" + reader_id + "\"";
        try {
            resultSet = statement.executeQuery(tempString);
            while(resultSet.next()) {
                reader = resultSet.getString("读者证编号");
                if(reader.equals(""))
                {
                    return 0;
                }
                else
                {
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //获取所有用户信息
    private static ObservableList<Reader_info> reader_infos;
    public ObservableList<Reader_info> findReader_infos(String reader_id, String reader_name)
    {
        Reader_info tempReader_info = null;
        reader_infos = FXCollections.observableArrayList();
        String tempString = "select * from readers where 读者证编号 like \"%%" + reader_id + "%%\" and 姓名 like \"%%" + reader_name + "%%\"";
        try {
            resultSet = statement.executeQuery(tempString);
            while(resultSet.next()) {
                tempReader_info = new Reader_info(resultSet.getString("读者证编号"), resultSet.getString("姓名"), resultSet.getString("性别"),resultSet.getString("出生日期"),resultSet.getString("联系电话"),resultSet.getString("手机"),resultSet.getString("证件名称"),resultSet.getInt("证件编号"),resultSet.getInt("会员级别"),resultSet.getString("办证日期"),0);
                reader_infos.add(tempReader_info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reader_infos;
    }
    //图书丢失
    private static ObservableList<Books_lost> books_losts;
    public ObservableList<Books_lost> findBooks_losts() {
        Books_lost tempBooks_lost = null;
        books_losts = FXCollections.observableArrayList();
        String tempString = "select * from books_lost";
        try {
            resultSet = statement.executeQuery(tempString);
            while(resultSet.next()) {
                tempBooks_lost = new Books_lost(resultSet.getString("读者证编号"), resultSet.getString("图书编号"),resultSet.getString("丢失日期"));
                books_losts.add(tempBooks_lost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books_losts;
    }
    public int reportBookLost(String reader_id, String book_id, String lost_date)
    {
        int lostbooks = checkBookLost(book_id);
        lostbooks++;
        String sql_books_lost = "insert books_lost values(\"" + reader_id + "\",\"" + book_id + "\" ,\"" + lost_date + "\")";
        String sql_books = "update books set 遗失数量 = " + lostbooks + " where 图书编号 = \"" + book_id + "\"";
        try {
            statement.execute(sql_books_lost);
            statement.execute(sql_books);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //获取全部图书个数
    public int getAllBooksNum()
    {
        int booksNum = 0;
        String sql_books = "SELECT COUNT(*) FROM books";
        try {
            resultSet = statement.executeQuery(sql_books);
            while (resultSet.next()) {
                booksNum = resultSet.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksNum;
    }
    public int getAllBooksNumByCate(String category)
    {
        int booksNumBycate = 0;
        String sql_books = "SELECT COUNT(*) FROM books where 类别 = \"" + category + "\"";
        try {
            resultSet = statement.executeQuery(sql_books);
            while (resultSet.next()) {
                booksNumBycate = resultSet.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksNumBycate;
    }
    private static ObservableList<PieChart.Data> books_info;
    public ObservableList<PieChart.Data> getBooks_info()
    {
        books_info = FXCollections.observableArrayList();
        books_info.addAll(new PieChart.Data("计算机科学",getAllBooksNumByCate("1")));
        books_info.addAll(new PieChart.Data("自然科学",getAllBooksNumByCate("2")));
        books_info.addAll(new PieChart.Data("医学",getAllBooksNumByCate("3")));
        books_info.addAll(new PieChart.Data("农林",getAllBooksNumByCate("4")));
        books_info.addAll(new PieChart.Data("建筑",getAllBooksNumByCate("5")));
        books_info.addAll(new PieChart.Data("科普",getAllBooksNumByCate("6")));
        books_info.addAll(new PieChart.Data("通信",getAllBooksNumByCate("7")));
        books_info.addAll(new PieChart.Data("化学",getAllBooksNumByCate("8")));
        books_info.addAll(new PieChart.Data("物理",getAllBooksNumByCate("9")));
        books_info.addAll(new PieChart.Data("美文",getAllBooksNumByCate("10")));
        return books_info;
    }
    //获取被借图书个数
    public int getAllBorrowedBooksNum()
    {
        int booksNum = 0;
        String sql_books = "SELECT COUNT(*) FROM borrow";
        try {
            resultSet = statement.executeQuery(sql_books);
            while (resultSet.next()) {
                booksNum = resultSet.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksNum;
    }
    public int getAllBorrowedBooksNumByCate(String category)
    {
        int booksNumBycate = 0;
        String sql_books = "SELECT COUNT( * ) FROM borrow,books where borrow.图书编号 = books.图书编号 and books.类别 = \"" + category + "\"";
        try {
            resultSet = statement.executeQuery(sql_books);
            while (resultSet.next()) {
                booksNumBycate = resultSet.getInt("COUNT( * )");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksNumBycate;
    }
    private static ObservableList<PieChart.Data> borrowed_books_info;
    public ObservableList<PieChart.Data> getBorrowed_books_info()
    {
        borrowed_books_info = FXCollections.observableArrayList();
        borrowed_books_info.addAll(new PieChart.Data("计算机科学",getAllBorrowedBooksNumByCate("1")));
        borrowed_books_info.addAll(new PieChart.Data("自然科学",getAllBorrowedBooksNumByCate("2")));
        borrowed_books_info.addAll(new PieChart.Data("医学",getAllBorrowedBooksNumByCate("3")));
        borrowed_books_info.addAll(new PieChart.Data("农林",getAllBorrowedBooksNumByCate("4")));
        borrowed_books_info.addAll(new PieChart.Data("建筑",getAllBorrowedBooksNumByCate("5")));
        borrowed_books_info.addAll(new PieChart.Data("科普",getAllBorrowedBooksNumByCate("6")));
        borrowed_books_info.addAll(new PieChart.Data("通信",getAllBorrowedBooksNumByCate("7")));
        borrowed_books_info.addAll(new PieChart.Data("化学",getAllBorrowedBooksNumByCate("8")));
        borrowed_books_info.addAll(new PieChart.Data("物理",getAllBorrowedBooksNumByCate("9")));
        borrowed_books_info.addAll(new PieChart.Data("美文",getAllBorrowedBooksNumByCate("10")));
        return borrowed_books_info;
    }
    //获取读者总数
    public int getAllReadersNum()
    {
        int readersNum = 0;
        String sql_books = "SELECT COUNT(*) FROM readers";
        try {
            resultSet = statement.executeQuery(sql_books);
            while (resultSet.next()) {
                readersNum = resultSet.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readersNum;
    }
    public int getAllReadersNumBySex(String sex)
    {
        int readersNumbySex = 0;
        String sql_books = "SELECT COUNT( * ) FROM readers where 性别 = \"" + sex + "\"";
        try {
            resultSet = statement.executeQuery(sql_books);
            while (resultSet.next()) {
                readersNumbySex = resultSet.getInt("COUNT( * )");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readersNumbySex;
    }
    private static ObservableList<PieChart.Data> readers_Sex_info;
    public ObservableList<PieChart.Data> getReaders_Sex_info()
    {
        readers_Sex_info = FXCollections.observableArrayList();
        readers_Sex_info.addAll(new PieChart.Data("男",getAllReadersNumBySex("男")));
        readers_Sex_info.addAll(new PieChart.Data("女",getAllReadersNumBySex("女")));
        return readers_Sex_info;
    }


    public int checkBookOut(String book_id)
    {
        int stock = 0;
        String sql = "select 借出数量 from books where 图书编号 = \"" + book_id + "\"";
        try {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next())
            {
                stock = resultSet.getInt("借出数量");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }
    public int checkBookLost(String book_id)
    {
        int lost = 0;
        String sql = "select 遗失数量 from books where 图书编号 = \"" + book_id + "\"";
        try {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next())
            {
                lost = resultSet.getInt("遗失数量");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lost;
    }
    public int checkBooksHave(String reader_id)
    {
        if(checkUser(reader_id) == -3)
        {
            System.out.println(checkUser(reader_id));
            return  -1;
        }
        int booksHave = 0;
        String sql = "select 已借书数 from readers_borrow where 读者证编号 = \"" + reader_id + "\"";
        try {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next())
            {
                booksHave = resultSet.getInt("已借书数");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksHave;
    }
    public int checkLevel(String reader_id)
    {
        int level = 0;
        String sql = "select 会员级别 from readers where 读者证编号 = \"" + reader_id + "\"";
        try {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next())
            {
                level = resultSet.getInt("会员级别");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return level;
    }
    public int getLevelDays(int level)
    {
        int days = 0;
        String sql = "select 最长借出天数 from member_level where 会员级别 = \"" + level + "\"";
        try {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next())
            {
                days = resultSet.getInt("最长借出天数");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return days;
    }
    public int getLevelNums(int level)
    {
        int nums = 0;
        String sql = "select 最多借书册数 from member_level where 会员级别 = \"" + level+ "\"";
        try {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next())
            {
                nums = resultSet.getInt("最多借书册数");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nums;
    }


    public double getArrears(String reader_id)
    {
        double arrears = 0;
        String sql = "select * from arrears where 读者证编号 = \"" + reader_id + "\"";
        try {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next())
            {
                arrears = resultSet.getDouble("欠款");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrears;
    }
    public int setArrears(String reader_id, double arrears)
    {
        String sql = "UPDATE arrears set 欠款 = "+ arrears + "where 读者证编号 = \"" + reader_id + "\"";
        try {
            statement.execute(sql);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int newCard(String reader_id, String reader_name, String sex, String birthday, String contect, String phone, String cardtype, int cardID, int level, String date_in, String password)
    {
        String sql_readers = "insert readers values(\"" + reader_id + "\",\"" + reader_name + "\",\"" + sex + "\",\"" + birthday + "\",\"" + contect + "\",\"" + phone + "\",\"" + cardtype + "\"," + cardID + "," + level + ",\"" + date_in +"\")";
        String sql_readers_borrow = "insert readers_borrow values(\"" + reader_id + "\", 0 )";
        String sql_arrears = "insert arrears values(\"" + reader_id + "\"," + 0.0 + ")";
        String sql_userinfo = "insert userinfo values(\"" + reader_id + "\",\"" + password + "\"," + 0 + ")";
        try {
            statement.execute(sql_readers);
            statement.execute(sql_readers_borrow);
            statement.execute(sql_arrears);
            statement.execute(sql_userinfo);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Administrator_info getAdministrator_info() {
        return administrator_info;
    }

    public static Reader_info getReader_info() {
        return reader_info;
    }
}

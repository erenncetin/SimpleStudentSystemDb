
import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class DataBase {

    private Connection con;
    private static final String USERNAME = System.getenv("usernameDb");
    private static final String PASSWORD = System.getenv("passwordDb");
    private static final String URL = System.getenv("pathDb");

    // Constructor
    public DataBase() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("There is an error on the connection of the database");
            e.printStackTrace();
        }
    }

    // Select
    public void select(DefaultTableModel model) {

        try {
            
            String sql = "select * from public.\"StudentInformation\"";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String department = rs.getString(3);
                model.addRow(new Object[]{id,name,department});
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is an error while executing select operation.");
        }
    }

    // Böyle bir öğrenci var mı yok mu onu aramasını sağlayan bir fonksiyon
    // Eğer 1 ise --> vardır
    // Eğer 0 ise  --> yoktur
    public int selectSpecificStudent(int studentId) {

        try {
            String sql = "select * from public.\"StudentInformation\" where \"StudentId\" = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is an error while executing select operation.");
            return 0;
        }
    }
      
    // Insert
    // Eğer 1 dönüyorsa --> başarılı bir şekilde işlem gerçekleştirildi
    // Eğer 0 dönüyorsa --> İşlem başarısız oldu 
    // Int dönmesinin sebebi uygulama ekranına hata yazısı yazdırıp yazdırılmamasının test edilmesi
    public int insert(int id, String name, String department) {

        try {          
            String sql = "insert into public.\"StudentInformation\" values (?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3,department);
            st.executeUpdate();
            return 1; 

        } 
        catch (Exception e) {
            return 0;
        }
    }

    // Delete
    public int delete(int id) {

        try {
            
            String sql = "delete from public.\"StudentInformation\" where \"StudentId\" = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            return 1;

        } 
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Update
    public int update(int id, String name, String deparmtent) {

        try {

            String sql = "update public.\"StudentInformation\" set \"StudentId\" = ?, \"StudentName\" = ?, \"Department\" = ? where \"StudentId\" = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3, deparmtent);
            st.setInt(4, id);
            st.executeUpdate();
            return 1;

        } 
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is an error while update operation is executing");
            return 0;
        }

    }
    
}

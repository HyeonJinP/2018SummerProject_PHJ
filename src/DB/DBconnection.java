package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DBconnection 
{
    public static Connection dbConn;
    // �����ͺ��̽� ���� ����
    
        public static Connection getConnection()
        {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //Class.forName() : �������� �ڹ� Ŭ���� �ε�     
                //com.mysql.jdbc.Driver : MySQL�� JDBC ����̹� Ŭ����
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/member?useUnicode=true&characterEncoding=utf8", "root","super0115nhj&");
                //DB ���� �ּ�, ���� �̸�, ���� ��й�ȣ
                //����̹� ���� ��� DriverManager�� ���
                
                System.out.println("Database�� ����Ǿ����ϴ�.\n"); // ���� ���� ��
                
            } catch (ClassNotFoundException cnfe) { // ���� ���� ���
                System.out.println("DB ����̹� �ε� ���� :"+cnfe.toString());
            } catch (SQLException sqle) {
                System.out.println("DB ���ӽ��� : "+sqle.toString());
            } catch (Exception e) {
                System.out.println("Unkonwn error");
                e.printStackTrace();
            }
            return conn;     
        }
}
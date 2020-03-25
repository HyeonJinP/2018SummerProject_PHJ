package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DBconnection 
{
    public static Connection dbConn;
    // 데이터베이스 연결 설정
    
        public static Connection getConnection()
        {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //Class.forName() : 동적으로 자바 클래스 로딩     
                //com.mysql.jdbc.Driver : MySQL의 JDBC 드라이버 클래스
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/member?useUnicode=true&characterEncoding=utf8", "root","비밀번호");
                //DB 연결 주소, 계정 이름, 계정 비밀번호
                //드라이버 있을 경우 DriverManager에 등록
                
                System.out.println("Database에 연결되었습니다.\n"); // 연결 성공 시
                
            } catch (ClassNotFoundException cnfe) { // 연결 실패 경우
                System.out.println("DB 드라이버 로딩 실패 :"+cnfe.toString());
            } catch (SQLException sqle) {
                System.out.println("DB 접속실패 : "+sqle.toString());
            } catch (Exception e) {
                System.out.println("Unkonwn error");
                e.printStackTrace();
            }
            return conn;     
        }
}

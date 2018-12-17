import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author liupeqing
 * @date 2018/11/24 18:06
 */
public class App {

        public static void main(String[] args) {

            Connection conn = null;
            try{

                DruidDataSource druidDataSource = new DruidDataSource();
                //mysql
//            druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//            druidDataSource.setUrl("jdbc:mysql://localhost:3306/springcloud?characterEncoding=utf8&useSSL=true");
//            druidDataSource.setUsername("root");
//            druidDataSource.setPassword("root");
                //oracle
                druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
                druidDataSource.setUrl("jdbc:oracle:thin:@//127.0.0.1:1521/orcl");
                druidDataSource.setUsername("scott");
                druidDataSource.setPassword("3bB5ivE1");
                // 3.2) 连接池的参数(初始连接数，最大连接数，最长等待时间，最大等待个数)
                druidDataSource.setInitialSize(5);
                druidDataSource.setMaxActive(10);
                druidDataSource.setMaxWait(2000);
                druidDataSource.setValidationQuery("SELECT 1 from dual");
                druidDataSource.setTestWhileIdle(true);
                druidDataSource.setTestOnBorrow(false);
                druidDataSource.setTestOnReturn(false);
                druidDataSource.setPoolPreparedStatements(false);
                druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(200);

                conn = druidDataSource.getConnection();
                String sql = "select s.*,c.ZGH FROM V_STUDENTINFO s " +
                        "LEFT JOIN  V_CLASSINFO c on c.BJDM = s.BH " +
                        "left JOIN V_JZGINFO j on j.zgh = c.zgh " +
                        "ORDER BY s.XH asc";
                //预编译sql
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while(rs.next()){

                    System.out.println(rs.getString("XM")+"  "+rs.getString("ZGH"));
                }

                for (int i=0;i<10;i++){
                    System.out.println("第" + i + "个连接:" + conn.hashCode() +  druidDataSource.getConnection());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
        }
        }

}

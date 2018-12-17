package testbims;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liupeqing
 * @date 2018/11/27 17:00
 */
@Data
public class Jzg implements Serializable {
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Jzg(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

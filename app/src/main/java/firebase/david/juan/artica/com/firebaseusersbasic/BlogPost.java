package firebase.david.juan.artica.com.firebaseusersbasic;

import java.util.Date;

/**
 * Created by interoperabilidad on 19/09/15.
 */
public class BlogPost {
    private String userName;
    private String message;
    private Date date = new Date();

    public BlogPost() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

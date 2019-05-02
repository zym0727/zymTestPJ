package zym.pojo.param;

import zym.pojo.MessageInteraction;

/**
 * @author zym
 * *
 */
public class MessageReply extends MessageInteraction {

    private String title;

    private String studentName;

    private String userName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

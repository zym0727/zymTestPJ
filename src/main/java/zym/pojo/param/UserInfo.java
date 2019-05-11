package zym.pojo.param;

import zym.pojo.Users;

/**
 * @author zym
 * *
 */
public class UserInfo extends Users {
    private String className;

    private String sexDetail;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSexDetail() {
        return sexDetail;
    }

    public void setSexDetail(String sexDetail) {
        this.sexDetail = sexDetail;
    }
}

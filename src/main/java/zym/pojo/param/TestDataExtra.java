package zym.pojo.param;

import zym.pojo.TestData;

/**
 * @author zym
 * *
 */
public class TestDataExtra extends TestData {

    private Integer itemBankId;

    private Integer languageMarkId;

    public void setItemBankId(Integer itemBankId) {
        this.itemBankId = itemBankId;
    }

    public void setLanguageMarkId(Integer languageMarkId) {
        this.languageMarkId = languageMarkId;
    }

    public Integer getItemBankId() {
        return itemBankId;
    }

    public Integer getLanguageMarkId() {
        return languageMarkId;
    }
}

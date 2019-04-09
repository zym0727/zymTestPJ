package zym.pojo.param;

import zym.pojo.TestData;

/**
 * @author zym
 * *
 */
public class InputOutput extends TestData {
    private Boolean isCorrect;

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "InputOutput{" +
                "id=" + getId() +
                ", input='" + getInput() + '\'' +
                ", output='" + getOutput() + '\'' +
                '}';
    }
}

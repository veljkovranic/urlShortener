/**
 * Created by veljko on 7/31/17.
 */
public class UrlModel {
    private String rawValue;
    private String resultValue;
    private int numberOfRedirections;

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public int getNumberOfRedirections() {
        return numberOfRedirections;
    }

    public void setNumberOfRedirections(int numberOfRedirections) {
        this.numberOfRedirections = numberOfRedirections;
    }
}

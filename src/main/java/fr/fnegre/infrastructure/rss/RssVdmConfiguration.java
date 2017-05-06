package fr.fnegre.infrastructure.rss;

public class RssVdmConfiguration {
    private String baseUrl;
    private String suffixPageParameter;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSuffixPageParameter() {
        return suffixPageParameter;
    }

    public void setSuffixPageParameter(String suffixPageParameter) {
        this.suffixPageParameter = suffixPageParameter;
    }
}

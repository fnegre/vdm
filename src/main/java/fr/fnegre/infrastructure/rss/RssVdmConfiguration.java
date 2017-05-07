package fr.fnegre.infrastructure.rss;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RssVdmConfiguration {

    @Value("${vdm.rss.baseUrl}")
    private String baseUrl;

    @Value("${vdm.rss.suffixPageParameter}")
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

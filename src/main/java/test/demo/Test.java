package test.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
@ConfigurationProperties("test")
public class Test {

    @NotNull
    private String projectName;
    private int version;
    private String projectNameAndVersion;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getProjectNameAndVersion() {
        return projectNameAndVersion;
    }

    public void setProjectNameAndVersion(String projectNameAndVersion) {
        this.projectNameAndVersion = projectNameAndVersion;
    }
}

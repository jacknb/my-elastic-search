package com.adelmo.esearch.template.beans;

/**
 * service_info表信息
 *
 * @author znb
 * @date 17-11-19.
 */
public class ServiceInfo {

    /**
     * service id
     */
    private String serviceId;

    /**
     * service name
     */
    private String serviceName;

    /**
     * system id
     */
    private String systemId;

    /**
     * system name
     */
    private String systemName;

    /**
     * project id
     */
    private String projectId;

    /**
     * project name
     */
    private String projectName;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ServiceInfo() {
    }

    public ServiceInfo(String serviceId, String serviceName, String systemId, String systemName, String projectId, String projectName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.systemId = systemId;
        this.systemName = systemName;
        this.projectId = projectId;
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", systemId='" + systemId + '\'' +
                ", systemName='" + systemName + '\'' +
                ", projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}

package com.albedo.java.common.config.apidoc.customizer;

import com.albedo.java.common.config.AlbedoSwaggerProperties;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class AlbedoSwaggerCustomizer implements SwaggerCustomizer, Ordered {
    public static final int DEFAULT_ORDER = 0;
    private int order = 0;
    private final AlbedoSwaggerProperties albedoSwaggerProperties;

    public AlbedoSwaggerCustomizer(AlbedoSwaggerProperties albedoSwaggerProperties) {
        this.albedoSwaggerProperties = albedoSwaggerProperties;
    }

    @Override
    public void customize(Docket docket) {
        Contact contact = new Contact(this.albedoSwaggerProperties.getContactName(), this.albedoSwaggerProperties.getContactUrl(), this.albedoSwaggerProperties.getContactEmail());
        ApiInfo apiInfo = new ApiInfo(this.albedoSwaggerProperties.getTitle(), this.albedoSwaggerProperties.getDescription(), this.albedoSwaggerProperties.getVersion(), this.albedoSwaggerProperties.getTermsOfServiceUrl(), contact, this.albedoSwaggerProperties.getLicense(), this.albedoSwaggerProperties.getLicenseUrl(), new ArrayList());
        docket.host(this.albedoSwaggerProperties.getHost())
            .protocols(new HashSet(Arrays.asList(this.albedoSwaggerProperties.getProtocols())))
            .apiInfo(apiInfo).useDefaultResponseMessages(this.albedoSwaggerProperties.isUseDefaultResponseMessages())
            .forCodeGeneration(true).directModelSubstitute(ByteBuffer.class, String.class)
            .genericModelSubstitutes(new Class[]{ResponseEntity.class}).select()
            .paths(PathSelectors.regex(this.albedoSwaggerProperties.getDefaultIncludePattern())).build();
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}

package com.scout24.techchalenge.webpageanalyserapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Web Page Element Analyser.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}

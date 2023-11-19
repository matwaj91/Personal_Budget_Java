package PersonalBudget.configuration.webFilters;

import PersonalBudget.common.webFilters.RequestExecutionTimeFilter;
import PersonalBudget.common.webFilters.RequestResponseLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFiltersConfiguration {

    @Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> requestResponseLoggingFilter() {
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestResponseLoggingFilter());
        registrationBean.setOrder(0);
        return registrationBean;
     }

    @Bean
    public FilterRegistrationBean<RequestExecutionTimeFilter> RequestExecutionTimeFilter() {
        FilterRegistrationBean<RequestExecutionTimeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestExecutionTimeFilter());
        registrationBean.setOrder(1);
        return registrationBean;
     }
}

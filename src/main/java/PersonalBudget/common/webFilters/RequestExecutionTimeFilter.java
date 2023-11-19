package PersonalBudget.common.webFilters;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Slf4j
@Component
public class RequestExecutionTimeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        log.info("Request execution time: {} ms", endTime - startTime);
    }

}

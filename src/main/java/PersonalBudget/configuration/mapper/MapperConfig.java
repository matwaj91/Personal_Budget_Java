package PersonalBudget.configuration.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public record MapperConfig() {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

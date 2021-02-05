package vn.compedia.api.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import vn.compedia.api.dto.auth.api.ApiUserDTO;
import vn.compedia.api.entity.Account;
import vn.compedia.api.entity.Role;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableTransactionManagement
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        configModelMapper(modelMapper);
        return modelMapper;
    }

    //configuring default locale
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("vi"));
        return localeResolver;
    }

    //configuring ResourceBundle
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    private void configModelMapper(ModelMapper modelMapper) {
        configUserToUserDTOMapper(modelMapper);
        configUserDTOToUserMapper(modelMapper);
    }

    private void configUserToUserDTOMapper(ModelMapper modelMapper) {
        Converter<Set<Role>, Set<String>> converter =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet());

        modelMapper.typeMap(Account.class, ApiUserDTO.class)
                .addMappings(mapper -> mapper.using(converter).map(Account::getRoleId, ApiUserDTO::setRoleId));
    }

    private void configUserDTOToUserMapper(ModelMapper modelMapper) {
        Converter<Set<String>, Set<Role>> converter =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().stream()
                        .map(roleName -> {
                            Role role = new Role();
                            role.setName(roleName);
                            return role;
                        })
                        .collect(Collectors.toSet());


        modelMapper.typeMap(ApiUserDTO.class, Account.class)
                .addMappings(mapper -> mapper.using(converter).map(ApiUserDTO::getRoleId, Account::setRoleId));
    }
}

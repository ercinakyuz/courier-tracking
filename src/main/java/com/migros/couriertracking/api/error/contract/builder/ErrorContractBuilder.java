package com.migros.couriertracking.api.error.contract.builder;

import com.migros.couriertracking.api.error.contract.ErrorContract;
import com.migros.couriertracking.infrastructure.error.GenericError;
import com.migros.couriertracking.infrastructure.exception.CoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ErrorContractBuilder {

    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public ErrorContract build(final CoreException coreException) {
        return ErrorContract.builder()
                .code(buildCode(coreException.getCode()))
                .message(StringUtils.isBlank(coreException.getUserMessage()) ? getResourceMessage(coreException.getCode()) : coreException.getUserMessage())
                .build();
    }

    public ErrorContract build(final String code) {
        return ErrorContract.builder()
                .code(buildCode(code))
                .message(getResourceMessage(code))
                .build();
    }

    private String getResourceMessage(String resourceKey) {
        if (StringUtils.isBlank(resourceKey))
            resourceKey = GenericError.CODE;
        String message;
        try {
            message = resourceBundleMessageSource.getMessage(resourceKey, null, LocaleContextHolder.getLocale());
        }
        catch (Exception exception){
            message = resourceBundleMessageSource.getMessage(GenericError.CODE, null, LocaleContextHolder.getLocale());
        }
        return message;

    }

    private String buildCode(String code) {
        if (StringUtils.isBlank(code))
            code = GenericError.CODE;
        return code;
    }

}

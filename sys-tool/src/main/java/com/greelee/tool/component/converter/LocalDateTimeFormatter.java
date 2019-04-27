package com.greelee.tool.component.converter;

import com.greelee.tool.util.time.DateConst;
import com.greelee.tool.util.time.DateUtil;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 在 HTTP 请求中 字符串转换成 LocalDateTime
 */
@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {


    @Override
    public LocalDateTime parse(@Nonnull String text, @Nullable Locale locale) {
        return DateUtil.getDateTimeByString(text);
    }

    @Override
    public String print(@Nonnull LocalDateTime object, @Nullable Locale locale) {
        return object.format(DateTimeFormatter.ofPattern(DateConst.DEFAULT_DATE_FORMAT));
    }
}

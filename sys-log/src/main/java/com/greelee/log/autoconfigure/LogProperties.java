package com.greelee.log.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "sys.log")
public class LogProperties {

    /**
     * 是否记录到数据库,若为 false 则每次打印在 log 中,若为 true 则导入到数据库中
     */
    private boolean writeToDataBase = false;

}

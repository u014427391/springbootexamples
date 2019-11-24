package com.example.springboot.i18n.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年11月23日  修改内容:
 * </pre>
 */
public class MessagesLocalResolver implements LocaleResolver {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String localeFlag = request.getParameter("locale");
        LOG.info("localeFlag:{}",localeFlag);
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(localeFlag)) {
            String[] split = localeFlag.split("_");
            locale = new Locale(split[0],split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}

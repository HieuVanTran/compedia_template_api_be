package vn.compedia.api.utility;

import vn.compedia.api.config.Constant;

import java.util.Properties;

public class PropertiesUtil {
    private static Properties props;
    private static Properties evnProps;
    private static Properties emailProps;
    private static Properties viLangsProps;
    private static Properties loLangsProps;

    public static String getMessage(String name, int localeId) {
        if (localeId == Constant.VN) {
            return getViMessage(name);
        }
        return getLaoMessage(name);
    }

    public static String getViMessage(String name) {
        try {
            if (viLangsProps == null) {
                viLangsProps = new Properties();
                viLangsProps.load(PropertiesUtil.class.getResourceAsStream("/messages_vi.properties"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viLangsProps.getProperty(name);
    }

    public static String getLaoMessage(String name) {
        try {
            if (loLangsProps == null) {
                loLangsProps = new Properties();
                loLangsProps.load(PropertiesUtil.class.getResourceAsStream("/messages_lo.properties"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loLangsProps.getProperty(name);
    }

    public static String getProperty(String name) {
        try {
            if (props == null) {
                props = new Properties();
                props.load(PropertiesUtil.class.getResourceAsStream("/application.properties"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props.getProperty(name);
    }

    public static String getPropertyEnvironment(String name) {
        try {
            if (evnProps == null) {
                evnProps = new Properties();
                evnProps.load(PropertiesUtil.class.getResourceAsStream("/environment.properties"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evnProps.getProperty(name);
    }

    public static String getEmailProperty(String name) {
        try {
            if (emailProps == null) {
                emailProps = new Properties();
                emailProps.load(PropertiesUtil.class.getResourceAsStream("/email.properties"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailProps.getProperty(name);
    }

}

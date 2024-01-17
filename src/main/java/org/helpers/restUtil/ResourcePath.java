package org.helpers.restUtil;


import org.propertyHelper.PropertiesUtils;
import org.propertyHelper.PropertyFileEnum;

public class ResourcePath {
        static final String QA_BASE_URI = PropertiesUtils.getProperty(PropertyFileEnum.APICONFIG,"QA_BASE_URI");
        static final String DEV_BASE_URI = PropertiesUtils.getProperty(PropertyFileEnum.APICONFIG,"DEV_BASE_URI");
        static final String ANONYMOUS_TOKEN = PropertiesUtils.getProperty(PropertyFileEnum.APICONFIG,"ANONYMOUS_TOKEN");
}

package me.zhengjie.utils;

import java.util.regex.Pattern;

/**
 * @author master
 * @create 2019-09-06 16:03
 * 正则表达式的工具类，利用正则的预编译，效率高
 */
public class PatternUtil {

    public final static Pattern VIP_PATTERN_PRICE = Pattern.compile("(<em class=\"J-price\">)(\\d+)(<em class=\"price-postfix\">)");
    public final static Pattern VIP_PATTERN_TITLE = Pattern.compile("(<p class=\"pib-title-detail\" title=\")(.*)(\">)");
    public final static Pattern VIP_PATTERN_IMAGE = Pattern.compile("(<a href=\")(.*)(\" class=\"J-mer-bigImgZoom\")");
}

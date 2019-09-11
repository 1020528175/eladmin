package me.zhengjie.utils;

import java.util.regex.Pattern;

/**
 * @author master
 * @create 2019-09-06 16:03
 * 正则表达式的工具类，利用正则的预编译，效率高
 */
public class PatternUtil {

    /**
     * <em class="J-price">1998<em class="price-postfix">起</em>  有多个sku时，就有多个价格，显示的默认是最低的价格
     */
    public final static Pattern VIP_PATTERN_PRICE_MULTI_SKU = Pattern.compile("(<em class=\"J-price\">)(\\d+)(<em class=\"price-postfix\">)");
    /**
     * <em class="J-price">2043</em>  只有一个sku时，就只有一个价格
     */
    public final static Pattern VIP_PATTERN_PRICE_ONE_SKU = Pattern.compile("(<em class=\"J-price\">)(\\d+)(</em>)");
    public final static Pattern VIP_PATTERN_TITLE = Pattern.compile("(<p class=\"pib-title-detail\" title=\")(.*)(\">)");
    public final static Pattern VIP_PATTERN_IMAGE = Pattern.compile("(<a href=\")(.*)(\" class=\"J-mer-bigImgZoom\")");
}

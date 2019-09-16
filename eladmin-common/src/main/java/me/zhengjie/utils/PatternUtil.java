package me.zhengjie.utils;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author master
 * @create 2019-09-06 16:03
 * 正则表达式的工具类，利用正则的预编译，效率高
 */
public class PatternUtil {
    private PatternUtil() {
    }

    /**
     * <em class="J-price">1998<em class="price-postfix">起</em>  有多个sku时，就有多个价格，显示的默认是最低的价格
     */
    private final static Pattern VIP_PATTERN_PRICE_MULTI_SKU = Pattern.compile("(<em class=\"J-price\">)(\\d+)(<em class=\"price-postfix\">)");
    /**
     * <em class="J-price">2043</em>  只有一个sku时，就只有一个价格
     */
    private final static Pattern VIP_PATTERN_PRICE_ONE_SKU = Pattern.compile("(<em class=\"J-price\">)(\\d+)(</em>)");
    private final static Pattern VIP_PATTERN_TITLE = Pattern.compile("(<p class=\"pib-title-detail\" title=\")(.*)(\">)");
    private final static Pattern VIP_PATTERN_IMAGE = Pattern.compile("(<a href=\")(.*)(\" class=\"J-mer-bigImgZoom\")");

//    private final static Pattern JD_PATTERN_PRICE_MULTI_SKU = Pattern.compile("(<span class=\"price J-p-\\d\">)(\\d+)(</span>)");
//    private final static Pattern JD_PATTERN_PRICE_ONE_SKU = Pattern.compile("(<span class=\"price J-p-\\d\">)(\\d+)(</span>)");
    private final static Pattern JD_PATTERN_TITLE = Pattern.compile("(<div class=\"p-name\">)(.*)(</div>)");
    private final static Pattern JD_PATTERN_IMAGE = Pattern.compile("(data-origin=\")(.*)(\" alt=\")");
    //https://c0.3.cn/stock?skuId=4217474&area=22_1930_50948_0&venderId=1000075026&buyNum=1&choseSuitSkuIds=&cat=12218,12222,17038&extraParam={%22originid%22:%221%22}&fqsp=2&pdpin=&pduid=1545674275&ch=1&callback=jQuery8867319
    public final static String JD_PRICE_LINK = "https://c0.3.cn/stock?skuId=%s&area=22_1930_50948_0&venderId=1000075026&buyNum=1&choseSuitSkuIds=&cat=12218,12222,17038&extraParam=&fqsp=2&pdpin=&pduid=1545674275&ch=1";

    private final static Pattern TAOBAO_PATTERN_PRICE_MULTI_SKU = Pattern.compile("(name=\"current_price\" value= \")([0-9]+.[0-9]+)(\"/>)");
    private final static Pattern TAOBAO_PATTERN_PRICE_ONE_SKU = Pattern.compile("(name=\"current_price\" value= \")(\\d.\\d)(\"/>)");
    private final static Pattern TAOBAO_PATTERN_TITLE = Pattern.compile("(<title>)(.*)(-淘宝网</title>)");
    private final static Pattern TAOBAO_PATTERN_IMAGE = Pattern.compile("(id=\"J_ImgBooth\" src=\")(.*)(\")");

    private final static Pattern TMALL_PATTERN_PRICE_MULTI_SKU = Pattern.compile("(<em class=\"J-price\">)(\\d+)(<em class=\"price-postfix\">)");
    private final static Pattern TMALL_PATTERN_PRICE_ONE_SKU = Pattern.compile("(<em class=\"J-price\">)(\\d+)(</em>)");
    private final static Pattern TMALL_PATTERN_TITLE = Pattern.compile("(<p class=\"pib-title-detail\" title=\")(.*)(\">)");
    private final static Pattern TMALL_PATTERN_IMAGE = Pattern.compile("(<a href=\")(.*)(\" class=\"J-mer-bigImgZoom\")");

    public final static String VIP_LINK = "https://detail.vip.com/";
    public final static String JD_LINK = "https://item.jd.com/";
    public final static String TAOBAO_LINK = "https://item.taobao.com/";
    public final static String TMALL_LINK = "https://detail.tmall.com/";

    private final static String VIP_NAME = "唯品会";
    private final static String JD_NAME = "京东";
    private final static String TAOBAO_NAME = "淘宝网";
    private final static String TMALL_NAME = "天猫";


    public static Pattern getPattennTitle(@NotNull String link) {
        switch (link.substring(0,link.indexOf("com/") + 4)) {
            case PatternUtil.VIP_LINK:
                return PatternUtil.VIP_PATTERN_TITLE;
            case PatternUtil.JD_LINK:
                return PatternUtil.JD_PATTERN_TITLE;
            case PatternUtil.TAOBAO_LINK:
                return PatternUtil.TAOBAO_PATTERN_TITLE;
            case PatternUtil.TMALL_LINK:
                return PatternUtil.TMALL_PATTERN_TITLE;
            default:
                return null;
        }
    }

    public static Pattern getPattennImage(@NotNull String link) {
        switch (link.substring(0,link.indexOf("com/") + 4)) {
            case PatternUtil.VIP_LINK:
                return PatternUtil.VIP_PATTERN_IMAGE;
            case PatternUtil.JD_LINK:
                return PatternUtil.JD_PATTERN_IMAGE;
            case PatternUtil.TAOBAO_LINK:
                return PatternUtil.TAOBAO_PATTERN_IMAGE;
            case PatternUtil.TMALL_LINK:
                return PatternUtil.TMALL_PATTERN_IMAGE;
            default:
                return null;
        }
    }

    public static Pattern getPattennOneSkuPrice(@NotNull String link) {
        switch (link.substring(0,link.indexOf("com/") + 4)) {
            case PatternUtil.VIP_LINK:
                return PatternUtil.VIP_PATTERN_PRICE_ONE_SKU;
//            case PatternUtil.JD_LINK:
//                return PatternUtil.JD_PATTERN_PRICE_ONE_SKU;
            case PatternUtil.TAOBAO_LINK:
                return PatternUtil.TAOBAO_PATTERN_PRICE_ONE_SKU;
            case PatternUtil.TMALL_LINK:
                return PatternUtil.TMALL_PATTERN_PRICE_ONE_SKU;
            default:
                return null;
        }
    }

    public static Pattern getPattennMultiSkuPrice(@NotNull String link) {
        switch (link.substring(0,link.indexOf("com/") + 4)) {
            case PatternUtil.VIP_LINK:
                return PatternUtil.VIP_PATTERN_PRICE_MULTI_SKU;
//            case PatternUtil.JD_LINK:
//                return PatternUtil.JD_PATTERN_PRICE_MULTI_SKU;
            case PatternUtil.TAOBAO_LINK:
                return PatternUtil.TAOBAO_PATTERN_PRICE_MULTI_SKU;
            case PatternUtil.TMALL_LINK:
                return PatternUtil.TMALL_PATTERN_PRICE_MULTI_SKU;
            default:
                return null;
        }
    }

    public static String getPattennMallName(@NotNull String link) {
        switch (link.substring(0,link.indexOf("com/") + 4)) {
            case PatternUtil.VIP_LINK:
                return PatternUtil.VIP_NAME;
            case PatternUtil.JD_LINK:
                return PatternUtil.JD_NAME;
            case PatternUtil.TAOBAO_LINK:
                return PatternUtil.TAOBAO_NAME;
            case PatternUtil.TMALL_LINK:
                return PatternUtil.TMALL_NAME;
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Matcher matcher = PatternUtil.TAOBAO_PATTERN_PRICE_MULTI_SKU.matcher("<input type=\"hidden\" name=\"current_price\" value= \"2600.00\"/>");
        if (matcher.find()){
            System.out.println("matcher.group(2) = " + matcher.group(2));
        }
    }

}

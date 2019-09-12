package me.zhengjie.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.Query;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Zheng Jie
 * @date 2019-6-4 14:59:48
 */
@Slf4j
public class QueryHelp {

    /**
     * @描述 :  转换为Predicate
     * @作者 :  Dong ZhaoYang
     * @日期 :  2017/8/7
     * @时间 :  17:25
     */
    @SuppressWarnings("unchecked")
    public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();

        if(query == null){
            return cb.and(list.toArray(new Predicate[list.size()]));
        }
        try {
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Query q = field.getAnnotation(Query.class);
                if (q != null) {
                    String propName = q.propName();
                    String joinName = q.joinName();
                    String blurry = q.blurry();
                    String attributeName = isBlank(propName) ? field.getName() : propName;
                    Class<?> fieldType = field.getType();
                    Object val = field.get(query);
                    if (ObjectUtil.isNull(val)) {
                        continue;
                    }
                    /// 可以通过发射获取关联表对象中的字段。但是却不知道这些字段应该用哪种方式来查询，因为@Query注解只有一个查询类型
//                    Field field1 = val.getClass().getDeclaredField(propName);
//                    if (field1 != null){
//                        field1.setAccessible(true);
//                        String title = field1.get(val).toString();
//                        field1.setAccessible(false);
//                    }
                    ///

                    Join join = null;
                    // 模糊多字段
                    if (ObjectUtil.isNotEmpty(blurry)) {
                        String[] blurrys = blurry.split(",");
                        List<Predicate> orPredicate = new ArrayList<>();
                        for (String s : blurrys) {
                            orPredicate.add(cb.like(root.get(s)
                                    .as(String.class), "%" + val.toString() + "%"));
                        }
                        Predicate[] p = new Predicate[orPredicate.size()];
                        list.add(cb.or(orPredicate.toArray(p)));
                        continue;
                    }
                    if (ObjectUtil.isNotEmpty(joinName)) {
                        switch (q.join()) {
                            case LEFT:
                                join = root.join(joinName, JoinType.LEFT);
                                break;
                            case RIGHT:
                                join = root.join(joinName, JoinType.RIGHT);
                                break;
                            default: break;
                        }
                    }
                    switch (q.type()) {
                        case EQUAL:
                            //.as((Class<? extends Comparable>) fieldType) 2019-9-12 这里出现过问题：as()的本质是转换Expression对象的类型，
                            //问题现象：GoodsMonitorQueryCriteria类中的openStatus是Boolean对象，但是GoodsMonitor类中的openStatus是boolean基本数据类型，
                            //跟踪代码可以知道fieldType就是Boolean，root是GoodsMonitor的root对象，获取到openStatus是boolean，这样使用as()方法就是把boolean转换成Boolean，
                            //这是查询语句就变成了where cast(goodsmonit0_.open_status as char)= ?，这是就肯定查询不到东西了，
                            //去掉as()方法，不转换Expression的类型就不会有问题，查询语句就是where goodsmonit0_.open_status = ?
                            //所以使用as()方法转换类型有风险，当查询对象和数据表的映射对象中的字段类型不一致时，转换就会有可能出问题
                            list.add(cb.equal(getExpression(attributeName,join,root),val));
                            break;
                        case GREATER_THAN:
                            list.add(cb.greaterThanOrEqualTo(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case LESS_THAN:
                            list.add(cb.lessThanOrEqualTo(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case LESS_THAN_NQ:
                            list.add(cb.lessThan(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case INNER_LIKE:
                            list.add(cb.like(getExpression(attributeName,join,root)
                                    .as(String.class), "%" + val.toString() + "%"));
                            break;
                        case LEFT_LIKE:
                            list.add(cb.like(getExpression(attributeName,join,root)
                                    .as(String.class), "%" + val.toString()));
                            break;
                        case RIGHT_LIKE:
                            list.add(cb.like(getExpression(attributeName,join,root)
                                    .as(String.class), val.toString() + "%"));
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Long>)val)) {
                                list.add(getExpression(attributeName,join,root).in((Collection<Long>) val));
                            }
                            break;
                        default: break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return cb.and(list.toArray(new Predicate[list.size()]));
    }

    @SuppressWarnings("unchecked")
    private static <T, R> Expression<T> getExpression(String attributeName, Join join, Root<R> root) {
        if (ObjectUtil.isNotEmpty(join)) {
            return join.get(attributeName);
        } else {
            return root.get(attributeName);
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}

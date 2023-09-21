package com.dy.sales.flowers.config.filter;


import com.google.common.collect.Lists;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * 过滤器责任链工厂
 *
 * @author chao.lan
 */
@Component
public class FilterChainFactory {

    private static List<SsoFilter> filters;

    /**
     * 方式一：通过构造函数实现注入 保证过滤器的顺序 按照order升序
     * @param filtersProvider 过滤器提供者
     */
    public FilterChainFactory(ObjectProvider<List<SsoFilter>> filtersProvider) {
        List<SsoFilter> filters = filtersProvider.getIfAvailable();
        if (CollectionUtils.isEmpty(filters)) {
            FilterChainFactory.filters = Lists.newArrayList();
            return;
        }
        FilterChainFactory.filters = filters;
    }

    /**
     * 方式二：通过setter方法实现注入 保证过滤器的顺序 按照order升序
     */
//    @Autowired
//    public void setFilters(List<SsoFilter> filters) {
//        if (CollectionUtils.isEmpty(filters)) {
//            FilterChainFactory.filters = Lists.newArrayList();
//            return;
//        }
//        filters.sort((a, b) -> {
//            int orderA = getOrder(a);
//            int orderB = getOrder(b);
//            return Integer.compare(orderA, orderB);
//        });
//        FilterChainFactory.filters = filters;
//    }

    public static FilterChain getFilterChain() {
        return new SsoFilterChain(filters);
    }

    public static class SsoFilterChain implements FilterChain {
        private List<SsoFilter> filters;

        private Iterator<SsoFilter> iterator;

        public SsoFilterChain(List<SsoFilter> filters) {
            this.filters = filters;
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
            if (this.iterator == null) {
                this.iterator = this.filters.iterator();
            }

            if (this.iterator.hasNext()) {
                SsoFilter nextFilter = this.iterator.next();
                nextFilter.doFilter(servletRequest, servletResponse, this);
            }
        }
    }


    private int getOrder(SsoFilter a) {
        Order order = a.getClass().getAnnotation(Order.class);
        return order == null ? Integer.MAX_VALUE : order.value();
    }
}

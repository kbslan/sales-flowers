package com.dy.sales.flowers.config.filter;


import com.google.common.collect.Lists;
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


@Component
public class FilterChainFactory {

    private static List<SSOFilter> filters;

    public static FilterChain getFilterChain() {
        return new SsoFilterChain(filters);
    }

    public static class SsoFilterChain implements FilterChain {
        private List<SSOFilter> filters;

        private Iterator<SSOFilter> iterator;

        public SsoFilterChain(List<SSOFilter> filters) {
            this.filters = filters;
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
            if (this.iterator == null) {
                this.iterator = this.filters.iterator();
            }

            if (this.iterator.hasNext()) {
                SSOFilter nextFilter = this.iterator.next();
                nextFilter.doFilter(servletRequest, servletResponse, this);
            }
        }
    }

    @Autowired
    public void setFilters(List<SSOFilter> filters) {
        if (CollectionUtils.isEmpty(filters)) {
            FilterChainFactory.filters = Lists.newArrayList();
            return;
        }
        filters.sort((a, b) -> {
            int orderA = getOrder(a);
            int orderB = getOrder(b);
            return Integer.compare(orderA, orderB);
        });
        FilterChainFactory.filters = filters;
    }

    private int getOrder(SSOFilter a) {
        Order order = a.getClass().getAnnotation(Order.class);
        return order == null ? Integer.MAX_VALUE : order.value();
    }
}

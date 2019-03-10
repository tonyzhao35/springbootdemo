package demo.demoapigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 登录过滤器
 */
@Component
public class LoginFilter extends ZuulFilter {
    /**
     * 过滤器类型（前置过滤器）
     *
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器顺序（越小越先执行）
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * 过滤是否生效
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        System.out.println(request.getRequestURI());
        //通常使用ACL列表
        if ("/apigateway/order/api/v1/order/save".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        return false;
    }

    /**
     * 过滤的业务逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("进入拦截器业务逻辑（检查token）");
        //JWT安全性去中心化技术
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        //判断token,如为空则拦截
        if (StringUtils.isEmpty(token)) {
            System.out.println("被拦截");
            rc.setSendZuulResponse(false);
            rc.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        } else {
            System.out.println("放行");
        }
        return null;
    }
}

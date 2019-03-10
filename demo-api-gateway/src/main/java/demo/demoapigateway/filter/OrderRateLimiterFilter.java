package demo.demoapigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 限流
 */
@Component
public class OrderRateLimiterFilter extends ZuulFilter {

    //每秒产生2个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //最先执行
        return -4;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        //接口限流
        if ("/apigateway/order/api/v1/order/save".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("------------------------------");
        System.out.println("进入拦截器业务逻辑（流量检查）");
        RequestContext rc = RequestContext.getCurrentContext();
        if (!RATE_LIMITER.tryAcquire()) {
            System.out.println("被限流");
            rc.setSendZuulResponse(false);
            rc.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }else{
            System.out.println("放行");
        }
        return null;
    }

}

package vn.compedia.api.logging;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import vn.compedia.api.entity.VietTienLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Log4j2
@Component
public class VietTienInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("begin for request = {} url = {}", request.getSession().getId(), request.getRequestURL());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("end for request = {} url = {}", request.getSession().getId(), request.getRequestURL());

        try {
            String url = request.getRequestURL().toString();
            VietTienLog log_ = new VietTienLog();
            log_.setCode(response.getStatus());
            log_.setRequestId(request.getSession().getId());
            log_.setUrl(url);
            log_.setCreateDate(new Date());

        } catch (Exception e) {
            log.error("[postHandle] saveLog", e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, //
                                Object handler, Exception ex) throws Exception {
        log.info("afterCompletion  {}", new Date());
    }
}

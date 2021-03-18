package zzxkj.blog.exceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
/**
**全局处理
* */
@ControllerAdvice
public class ControllerExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView Exception(HttpServletRequest request, Exception e) throws Exception {

        //如果该异常的状态码已经被设定返回true，则抛出，否则返回false
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        logger.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        return modelAndView;
    }

}

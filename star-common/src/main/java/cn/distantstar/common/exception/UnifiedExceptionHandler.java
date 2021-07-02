package cn.distantstar.common.exception;

import cn.distantstar.common.result.ResponseEnum;
import cn.distantstar.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * @author: distantstar
 */
@Slf4j
@Component
@RestControllerAdvice
public class UnifiedExceptionHandler {

    /**
     * 通用异常设置
     * @param e 异常
     * @return 返回失败
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.info(e.getMessage(), e);
        return Result.fail();
    }

    /**
     * 数据库异常
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public Result handleBadSqlGrammarException(BadSqlGrammarException e){
        log.error(e.getMessage(), e);
        return Result.fail(ResponseEnum.BAD_SQL_GRAMMAR_ERROR);
    }

    /**
     * 返回自定义异常类
     * @param e 异常
     * @return 返回异常信息和状态码
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBadSqlGrammarException(BusinessException e){
        log.error(e.getMessage(), e);
        return Result.fail().message(e.getMessage()).code(e.getCode());
    }

    /**
     * Controller上一层相关异常
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public Result handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        //SERVLET_ERROR(-102, "servlet请求异常"),
        return Result.fail()
                .message(ResponseEnum.SERVLET_ERROR.getMessage())
                .code(ResponseEnum.SERVLET_ERROR.getCode());
    }

}

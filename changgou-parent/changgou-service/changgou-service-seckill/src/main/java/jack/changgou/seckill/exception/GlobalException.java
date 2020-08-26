package jack.changgou.seckill.exception;

import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler
    @ResponseBody
    public Result handleException(Exception ex) {
        return new Result(false, StatusCode.ERROR, ex.getMessage());
    }
}

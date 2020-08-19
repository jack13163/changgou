package jack.changgou.config;

import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Result error(Exception ex){
        ex.printStackTrace();
        return new Result<String>(false, StatusCode.ERROR, ex.getMessage());
    }
}

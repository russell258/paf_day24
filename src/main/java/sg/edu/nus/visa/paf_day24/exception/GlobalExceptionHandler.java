package sg.edu.nus.visa.paf_day24.exception;


import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BankAccountNotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleBankAccountNotFoundException(BankAccountNotFoundException ex, HttpServletRequest request){
        // form custom error msg

        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(404);
        errMsg.setTimeStamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        // return error with injected error message
        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", errMsg);
        return mav;
    }

    @ExceptionHandler(AmountNotSufficientException.class)
    public ModelAndView handleAmountNotSufficientException(AmountNotSufficientException ex, HttpServletRequest request){
        //form custom error msg

        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(404);
        errMsg.setTimeStamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        // return the error page with injected error message
        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", errMsg);
        return mav;
    }

        @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleAmountNotSufficientException(IllegalArgumentException ex,
            HttpServletRequest request) {
        // forming the custom error message
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(400);
        errMsg.setTimeStamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        // return the error 
        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorMessage> handleHttpServerErrorException(HttpServerErrorException ex,
            HttpServletRequest request) {
        // forming the custom error message
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(ex.getStatusCode().value());
        errMsg.setTimeStamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        // return the error 
        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.BAD_REQUEST);
    }

}

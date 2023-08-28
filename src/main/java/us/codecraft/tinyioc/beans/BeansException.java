package us.codecraft.tinyioc.beans;

/**
 * BeansException
 *
 * @author 郭佳俊
 * @since 2023/8/28
 **/
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

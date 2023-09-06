package us.codecraft.tinyioc;

/**
 * @author yihua.huang@dianping.com
 */
public class OutputServiceImpl implements OutputService {

    private HelloWorldService helloWorldService;

    @Override
    public String output(String text){
        System.out.println("output invoke");
        return text;
    }

    public void setHelloWorldService(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }
}

package us.codecraft.tinyioc;

/**
 * @author yihua.huang@dianping.com
 */
public class HelloWorldServiceImpl implements HelloWorldService {

    private String text;

    private OutputService outputService;

    @Override
    public void helloWorld(){
        System.out.println("helloWorld invoke");
        outputService.output(text);
    }

    @Override
    public void throwEx() throws Exception {
        throw new Exception("test");
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOutputService(OutputService outputService) {
        this.outputService = outputService;
    }

}

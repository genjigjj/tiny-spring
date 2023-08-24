package us.codecraft.tinyioc.beans.io;

import java.net.URL;

/**
 * @author yihua.huang@dianping.com
 */
public interface ResourceLoader {

    Resource getResource(String location);
}

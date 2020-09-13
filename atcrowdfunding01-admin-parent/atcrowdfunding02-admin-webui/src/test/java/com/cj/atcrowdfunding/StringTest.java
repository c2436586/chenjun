package com.cj.atcrowdfunding;

import com.cj.atcrowdfunding.util.CrowdUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringTest {

    private Logger logger=LoggerFactory.getLogger(String.class);

    @Test
    public void Test(){
        String sources="2436586";
        String encoded=CrowdUtil.md5(sources);
        logger.info(encoded);
    }

}

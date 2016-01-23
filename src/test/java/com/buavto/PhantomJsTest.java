package com.buavto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PhantomJsTest {

    @Value("${path_to_phatomjs}")
    public String phatomjsPath;
    @Value("${path_to_load_html_js}")
    public String htmljsPath;

    @Test
    public void test() throws Exception {
        String url = "https://auto.ria.com/search/?category_id=1&marka_id=0&model_id=0&state=0#category_id=1&marka_id[0]=0&model_id[0]=0&state[0]=0&s_yers[0]=2014&po_yers[0]=0&price_ot=10000&currency=1&page=0&countpage=10";
        String destPath = "./target/111.html";
        String cmd = phatomjsPath + " " + htmljsPath + " " + url + " " + destPath;
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        System.out.println(reader.readLine());
        System.out.println("done");
    }
}

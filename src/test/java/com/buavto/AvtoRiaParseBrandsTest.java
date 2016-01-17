package com.buavto;

import com.buavto.dao.BrandsDao;
import com.buavto.model.Brand;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AvtoRiaParseBrandsTest {

    private final static String PATH = "C:\\Users\\fein\\IdeaProjects\\BuAvtoAggregator\\src\\test\\resources\\brands_avto_ria.txt";

    @Autowired
    private BrandsDao brandsDao;

    @Test
    @Ignore
    public void test() throws Exception {

        FileInputStream fis = new FileInputStream(PATH);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {
            Brand brand = new Brand();
            brand.setName(line.trim().toUpperCase());
            brandsDao.save(brand);
            System.out.println("Brand: [" + brand.getName() + "] was saved");
        }

        br.close();

    }
}

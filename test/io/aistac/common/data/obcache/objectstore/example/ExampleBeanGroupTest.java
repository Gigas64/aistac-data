/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.aistac.common.data.obcache.objectstore.example;

import io.aistac.common.data.obcache.objectstore.example.ExampleGroupBean;
import io.aistac.common.canonical.data.BeanTester;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Darryl Oatridge
 */
public class ExampleBeanGroupTest {

    @Test
    public void test() throws Exception {
        String className = ExampleGroupBean.class.getName();
        BeanTester.testObjectBean(className, false, true);
    }

}
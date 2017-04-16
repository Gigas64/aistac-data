/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.aistac.common.data.obcache.objectstore.example;

import io.aistac.common.data.obcache.objectstore.example.ExampleInheritBean;
import io.aistac.common.canonical.data.example.BeanBuilder;
import io.aistac.common.canonical.data.BeanTester;
import io.aistac.common.canonical.data.ObjectBean;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Darryl
 */
public class ExampleInheritBeanTest {

    /**
     * Tests the underlying bean.
     */
    @Test
    public void test_ExampleInheritBean() throws Exception {
        boolean isPrintXML = false;
        boolean isGroupKey = false;
        List<String> exempt = Stream.of("getId","getKey").collect(Collectors.toList());
        BeanTester.testObjectBean(ExampleInheritBean.class.getName(), isPrintXML, isGroupKey, exempt);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * @(#)ExampleDataTypeBean.java
 *
 * Copyright:	Copyright (c) 2016
 * Company:		Oathouse.com Ltd
 */
package io.aistac.common.data.obcache.objectstore.example;

import io.aistac.common.canonical.data.ObjectBean;
import io.aistac.common.canonical.data.ObjectBean;
import io.aistac.common.canonical.data.ObjectEnum;
import io.aistac.common.canonical.data.example.ExampleBean;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jdom2.Element;

/**
 * The {@code ExampleDataTypeBean} Class
 *
 * @author Darryl Oatridge
 * @version 1.00 30-Mar-2016
 */
public class ExampleDataTypeBean extends ObjectBean {

    private static final long serialVersionUID = 198153880451404313L;

    private volatile int age;
    private volatile long sdValue;
    private volatile boolean checked;
    private volatile String name;
    private volatile ExampleBean exampleObject;
    private final int[] day;
    private final boolean[] week;
    private final ExampleBean[] example;
    private final String[] months;
    private final Set<Boolean> refCheck;
    private final Set<Integer> refId;
    private final Set<String> refName;
    private final Set<ExampleBean> refObject;
    private final Map<Integer,Integer> ywdRoom;
    private final Map<Integer,String> ywdName;
    private final Map<Integer,ExampleBean> ywdObject;
    private final Map<Integer,Set<Integer>> archiveValue;
    private final Map<Integer,Set<String>> archiveName;
    private final Map<Integer,Set<ExampleBean>> archiveObject;

    public ExampleDataTypeBean(int identifier, String owner, int age, long sdValue, boolean checked, String name,
            ExampleBean exampleObject, int[] day, boolean[] week, ExampleBean[] example, String[] months,
            Set<Boolean> refCheck,
            Set<Integer> refId,
            Set<String> refName,
            Set<ExampleBean> refObject,
            Map<Integer, Integer> ywdRoom,
            Map<Integer, String> ywdName,
            Map<Integer, ExampleBean> ywdObject,
            Map<Integer, Set<Integer>> archiveValue,
            Map<Integer, Set<String>> archiveName,
            Map<Integer, Set<ExampleBean>> archiveObject) {
        super(identifier, owner);
        this.age = age;
        this.sdValue = sdValue;
        this.checked = checked;
        this.name = name;
        this.exampleObject = exampleObject;
        this.day = day;
        this.week = week;
        this.example = example;
        this.months = months;
        this.refCheck = refCheck;
        this.refId = refId;
        this.refName = refName;
        this.refObject = refObject;
        this.ywdRoom = ywdRoom;
        this.ywdName = ywdName;
        this.ywdObject = ywdObject;
        this.archiveValue = archiveValue;
        this.archiveName = archiveName;
        this.archiveObject = archiveObject;
    }

    public ExampleDataTypeBean() {
        super();
        this.age = 0;
        this.sdValue = 0;
        this.checked = true;
        this.name = "";
        this.exampleObject = null;
        this.day = null;
        this.week = null;
        this.example = null;
        this.months = null;
        this.refCheck = null;
        this.refId = null;
        this.refName = null;
        this.refObject = null;
        this.ywdRoom = null;
        this.ywdName = null;
        this.ywdObject = null;
        this.archiveValue = null;
        this.archiveName = null;
        this.archiveObject = null;
    }


    public int getId() {
        return super.getIdentifier();
    }

    public int getKey() {
        return super.getGroupKey();
    }

    /**
     * crates all the elements that represent this bean at this level.
     * @return List of elements in order
     */
    @Override
    public List<Element> getXMLElement() {
        List<Element> rtnList = new LinkedList<>();
        // create and add the content Element
        super.getXMLElement().stream().forEach((e) -> {
            rtnList.add(e);
        });
        Element bean = new Element("ExampleDataTypeBean");
        rtnList.add(bean);
        // set the data

        // TODO Add code here - For example:
        // bean.setAttribute("myInt", Integer.toString(myInt));
        // bean.setAttribute("myString", myString);
        // bean.setAttribute("myEnum",myEnum.toString());
        // Element allElements = new Element("myRef_set");
        // bean.addContent(allElements);
        // if(myRef != null) {
        //     for(int i : myRef) {
        //         Element eachElement = new Element("myRef");
        //         eachElement.setAttribute("myRef", Integer.toString(i));
        //         allElements.addContent(eachElement);
        //     }
        // }

        bean.setAttribute("serialVersionUID", Long.toString(serialVersionUID));
        return(rtnList);
    }

    /**
     * sets all the values in the bean from the XML. Remember to
     * put default values in getAttribute() and check the content
     * of getText() if you are parsing to a value.
     *
     * @param root element of the DOM
     */
    @Override
    public void setXMLDOM(Element root) {
        // extract the super meta data
        super.setXMLDOM(root);
        // extract the bean data
        Element bean = root.getChild("ExampleDataTypeBean");
        // set up the data

        // TODO Add code here - For example:
        // myInt = Integer.parseInt(bean.getAttributeValue("myInt", "-1"));
        // myString = bean.getAttributeValue("myString", "");
        // myEnum = MyEnum.valueOf(bean.getAttributeValue("myEnum", "NO_VALUE"));
        // List allElements = bean.getChild("myRef_set").getChildren("myRef");
        // myRefs.clear();
        // for(Object o : allElements) {
        //     Element eachElement = (Element) o;
        //     myRefs.add(Integer.parseInt(eachElement.getAttributeValue("myRef", "-1")));
        // }

    }

}

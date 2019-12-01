package com.jasperjinx.svl.components;

import javafx.scene.shape.SVGPath;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;


class SVGLoader {

    private static final String RESOURCE_PATH = "src/com/jasperjinx/svl/resources/icon/";




    /**
     *
     * @param name Receive icon name
     * @return SVGPath
     */
    public static SVGPath getPathByName(SVGIcon name) {
        return new SVGPath() {{
            setContent(name.getPath());
        }};
    }





}

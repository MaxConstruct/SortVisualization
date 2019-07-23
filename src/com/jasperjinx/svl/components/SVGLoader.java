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
     * @param file Receive .svg File
     * @return SVGPath content as String
     */
    public static String load(File file) {
        try {
            var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            var xpathExpression = "//path/@d";
            var expression = XPathFactory.newInstance().newXPath().compile(xpathExpression);
            var svgPath = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            if(svgPath.getLength() == 1)
                return svgPath.item(0).getNodeValue();
            return svgPath.item(1).getNodeValue();
        } catch (Exception ex) {
            System.out.println("Unsupported File: "+file.getName());
            return null;
        }
    }

    /**
     *
     * @param name Receive icon name
     * @return SVGPath
     */
    public static SVGPath getPathByName(SVGIcon name) {
        return new SVGPath() {{
            setContent(load(new File(getReourcePath(name))));
        }};
    }

    private static String getReourcePath(SVGIcon name) {
        return RESOURCE_PATH + name.toString().toLowerCase() + ".svg";
    }

}

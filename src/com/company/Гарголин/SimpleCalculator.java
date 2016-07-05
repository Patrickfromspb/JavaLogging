package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Gargolin Pavel on 03.07.2016.
 */
public class SimpleCalculator {
    public static double operation(Element e) {
        // Operation has two arguments- another operation or argument
        // if argument then just read its value
        // else recursively call operation
        // attribute operationType tells us what operation to perform
        NodeList nodeList = e.getChildNodes();
        ArrayList<Double> operands = new ArrayList<Double>();
        for (int i = 0; i < nodeList.getLength(); i++)
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (nodeList.item(i).getNodeName() == "arg")
                    operands.add(Double.valueOf(nodeList.item(i).getFirstChild().getNodeValue()));
                if (nodeList.item(i).getNodeName() == "operation")
                    operands.add(operation((Element)nodeList.item(i)));
            }
        switch (e.getAttribute("OperationType")) {
            case "MUL":
                return operands.get(0) * operands.get(1);
            case "SUB":
                return operands.get(0) - operands.get(1);
            case "SUM":
                return operands.get(0) + operands.get(1);
            case "DIV":
                return operands.get(0) / operands.get(1);
            default:
                System.out.println("Encountered Error");
                System.exit(-1);
                return(0);
        }
    }

    public static void main(String[] args)  {

        File inputFile = new File("SampleTest.xml");
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);

        doc.getDocumentElement().normalize();
        Node node = doc.getDocumentElement();// go through root "Simple Calculatore"

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                node = nodeList.item(i);
            }    // go through "Expressions"
        nodeList = node.getChildNodes();
            ArrayList<Double> results = new ArrayList<Double>(); // store results of every expression
        for (int i = 0; i < nodeList.getLength(); i++)
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                results.add(expressionresult(((Element)nodeList.item(i)))); // to be called on each expression
            }
        output(results);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    // get the result of each enclosing operation
    public static double expressionresult(Element node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
               return(operation((Element) nodeList.item(i)));
            }
        System.out.println("Encountered Error");
        System.exit(-1);
        return(0);
    }
        public static void output(ArrayList<Double> results){
        try {
            Document doc =DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element rootElement = doc.createElement("SimpleCalculator");
            doc.appendChild(rootElement);

            Element ExpressionResults = doc.createElement("ExpressionResults");
            rootElement.appendChild(ExpressionResults );

            for(double i: results) {
            Element e= doc.createElement("ExpressionResult");
                e.setTextContent(Double.toString(i));
                ExpressionResults.appendChild(e);
            }
            // write the content into xml file
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer =
                    transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult resulting =
                    new StreamResult(new File("ResultSampleTest.xml"));
            transformer.transform(source, resulting);
            // Output to console for testing
         //   StreamResult consoleResult = new StreamResult(System.out);
           // transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}

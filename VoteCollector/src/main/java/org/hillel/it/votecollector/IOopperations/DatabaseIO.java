package org.hillel.it.votecollector.IOopperations;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.hillel.it.votecollector.model.entity.NetworkMemberSite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.12.13
 * Time: 21:56
 */
public class DatabaseIO {

    private static String xmlPath;
    private static String binaryPath;
    private static DocumentBuilder builder;


    static {
        try {
            List<String> lines = IOUtils.readLines(DatabaseIO.class.getResourceAsStream("/application.properties"));
            for (String str : lines) {
                if (str.contains("xml.folder")) {
                    xmlPath = str.split("=")[1];
                    File file = new File(xmlPath);
                    file.mkdir();

                }
                if (str.contains("binary.folder")) {
                    binaryPath = str.split("=")[1];
                    File file = new File(binaryPath);
                    file.mkdir();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List load(String fileName) {
        File file = new File(binaryPath + fileName);
        if (!file.exists()) return new ArrayList();
        InputStream fis = null;
        System.out.println("Loading data from " + file);
        try {
            fis = new FileInputStream(file);

            return (List<Object>) SerializationUtils.deserialize(fis);
        } catch (Exception e) {
            System.out.println("error reading " + file);
            return new ArrayList();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
            }

        }
    }

    public static void save(List list, String fileName) {
        OutputStream fos = null;
        File file = new File(binaryPath + fileName);
        System.out.println("Saving data to " + file);
        try {
            fos = new FileOutputStream(file);
            SerializationUtils.serialize(new ArrayList<Object>(list), fos);
        } catch (FileNotFoundException e) {
            System.out.println("error saving to file " + file);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static Object[][] loadBundle(String fileName) {
        List<String> data;
        try {
            data = FileUtils.readLines(new File(fileName), "iso-8859-1");
        } catch (IOException e) {
            throw new RuntimeException("Bundle " + fileName + " read error");
        }

        for (String s : data) System.out.println(s);

        Object[][] result = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).contains("=")) continue;
            String[] s = data.get(i).split("=");
            result[i] = new Object[2];
            result[i][0] = s[0].trim();
            StringEscapeUtils.unescapeJava(s[1]);
            result[i][1] = s[1];
        }
        return result;
    }

    public static void saveToXML(List<NetworkMemberSite> sites, String fileName) {
        File file = new File(xmlPath + fileName);
        System.out.println("Saving data to " + file);
        preapareXML();
        Document doc = builder.newDocument();
        Element rootElement = doc.createElement("List");
        for (NetworkMemberSite site : sites) {
            if (site == null) continue;
            Element userElemet = doc.createElement("User");
            rootElement.appendChild(userElemet);

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(site.getId())));
            userElemet.appendChild(idElement);

            Element creationDateElement = doc.createElement("creationDate");
            creationDateElement.appendChild(doc.createTextNode(site.getCreationDate().toString()));
            userElemet.appendChild(creationDateElement);

            Element changeDateElement = doc.createElement("changeDate");
            changeDateElement.appendChild(doc.createTextNode(site.getChangeDate().toString()));
            userElemet.appendChild(changeDateElement);

            Element isPublishedElement = doc.createElement("isPublished");
            isPublishedElement.appendChild(doc.createTextNode(String.valueOf(site.isPublished())));
            userElemet.appendChild(isPublishedElement);

            Element urlElement = doc.createElement("url");
            urlElement.appendChild(doc.createTextNode(site.getUrl()));
            userElemet.appendChild(urlElement);
        }


        doc.appendChild(rootElement);
        Transformer transformer;
        FileOutputStream fs = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            fs = new FileOutputStream(file, false);
            StreamResult streamResult = new StreamResult(fs);
            transformer.transform(new DOMSource(doc), streamResult);
        } catch (FileNotFoundException | TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

    public static List<NetworkMemberSite> loadFromXML(String fileName) {
        File file = new File(xmlPath + fileName);
        System.out.println("Loading data from " + file);
        List<NetworkMemberSite> sites = new ArrayList<>();
        preapareXML();
        Document document = null;
        try {
            document = builder.parse(new FileInputStream(file));
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            return sites;
        }

        Element inventory = document.getDocumentElement();
        NodeList sitesNode = inventory.getChildNodes();
        NetworkMemberSite site;
        for (int i = 0; i < sitesNode.getLength(); i++) {
            Node siteNode = sitesNode.item(i);
            NodeList prop = siteNode.getChildNodes();
            site = new NetworkMemberSite(Integer.parseInt(prop.item(0).getTextContent()));
            SimpleDateFormat formater = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.US);
            try {
                site.setCreationDate(formater.parse(prop.item(1).getTextContent()));
                site.setChangeDate(formater.parse(prop.item(2).getTextContent()));
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            site.setPublished(Boolean.parseBoolean(prop.item(3).getTextContent()));
            site.setPublished(Boolean.parseBoolean(prop.item(3).getTextContent()));
            site.setUrl(prop.item(4).getTextContent());
            sites.add(site);
        }
        return sites;
    }

    private static void preapareXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
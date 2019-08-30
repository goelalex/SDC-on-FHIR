package com.cancercareontario.SDCParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.utilities.graphql.StringValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ca.uhn.fhir.context.FhirContext;

/**
 * @author Joel Francis 
 * Canada Health Infoway
 *
 */
public class App {

	public static void main(String argv[]) {
		FhirContext ctx = FhirContext.forR4();
		loadSDCForm("C:\\PERSONAL\\SDC_on_FHIR\\PKG_Adrenal_Answers.xml", ctx);
	}

	public static void loadSDCForm(String filePath, FhirContext ctx) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(filePath));
			parseSDCForm(document, ctx);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void parseSDCForm(Document document, FhirContext ctx) {

		// get forminstanceVersion and ID
		String Id = getFormID(document);
		System.out.println("Form ID: " + Id);
		// get Body node
		Node body = getBodyElement(document);
		// get all the children of the body node
		ArrayList<Node> childrenOfBody = getAllChildrenFromBody(body);
		System.out.println("# of children in Body: " + childrenOfBody.size());
		// there should be only 1 child in the body - "ChildItems"
		Element childItems = (Element) childrenOfBody.get(0);
		NodeList questionList = getAllQuestionNodes(childItems);
		System.out.println("# of questions: " + questionList.getLength());
		// get the list of questions with selected = "true";
		ArrayList<Node> answeredQuestions = getSelectedTrueQuestions(questionList, Id, ctx);
	}

	/**
	 * Method that gets the root Element - SDCPackage
	 * 
	 * @param document
	 * @return
	 */
	public static Element getRootElement(Document document) {
		Element root = document.getDocumentElement(); // SDCPackage
		System.out.println("Root: " + root.getNodeName());
		return root;
	}

	/**
	 * Method that return the Body Element
	 * 
	 * @param document
	 * @return
	 */
	public static Node getBodyElement(Document document) {
		Node body = null;
		Element root = getRootElement(document);
		NodeList nList = document.getElementsByTagName("Body");
		body = nList.item(0);
		return body;
	}

	/**
	 * Method that gets the Children of the Body element. There should be only 1
	 * ChildItems
	 * 
	 * @param body
	 * @return
	 */
	public static ArrayList<Node> getAllChildrenFromBody(Node body) {
		NodeList children = body.getChildNodes();
		return removeWhiteSpaces(children);
	}

	/**
	 * Method that gets all the sections form the ChildItems under Body
	 * 
	 * @param childItems
	 * @return
	 */
	public static NodeList getSectionsFromChildItems(Element childItems) {
		NodeList nodeList = childItems.getElementsByTagName("Section");
		return nodeList;
	}

	/**
	 * Method that removes all the "# text" elements form the list
	 * 
	 * @param nodeList
	 * @return
	 */
	public static ArrayList<Node> removeWhiteSpaces(NodeList nodeList) {
		ArrayList<Node> returnList = new ArrayList<Node>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (Node.ELEMENT_NODE == node.getNodeType()) {
				returnList.add(node);
			}
		}

		return returnList;
	}

	public static NodeList getAllQuestionNodes(Element childItems) {
		ArrayList<Node> questions = new ArrayList<Node>();
		NodeList questionList = childItems.getElementsByTagName("Question");
		return questionList;
	}

	public static void printQuestionName(ArrayList<Node> questionList) {
		for (int i = 0; i < questionList.size(); i++) {
		}
	}

	public static ArrayList<Node> getSelectedTrueQuestions(NodeList questionList, String Id, FhirContext ctx) {

		ArrayList<Node> answeredQuestions = new ArrayList<Node>();

		for (int i = 0; i < questionList.getLength(); i++) {
			Element questionElement = (Element) questionList.item(i);
			// get the ListItems under this question where selected = true
			NodeList listItemList = questionElement.getElementsByTagName("ListItem");
			for (int j = 0; j < listItemList.getLength(); j++) {
				Element listItemElement = (Element) listItemList.item(j);
				if (listItemElement.hasAttribute("selected")) {
					answeredQuestions.add(questionElement);
					System.out.println("QUESTION.ID: " + questionElement.getAttribute("ID"));
					System.out.println("LISTITEM.ID: " + listItemElement.getAttribute("ID"));
					System.out.println("LISTITEM.TITLE: " + listItemElement.getAttribute("title"));
					System.out.println("*******************************************************************");
					buildObservationResource(questionElement, listItemElement, Id, ctx);
				}
			}
		}
		return answeredQuestions;
	}

	public static void buildObservationResource(Element questionElement, Element listItemElement, String id, FhirContext ctx) {
		Observation observation = new Observation();
		observation.addIdentifier().setSystem("https://CAP.org")
				.setValue(id + "." + questionElement.getAttribute("ID"));
		observation.setStatus(ObservationStatus.FINAL);
		observation.getCode().addCoding().setSystem("https://CAP.org").setCode(questionElement.getAttribute("ID"))
		.setDisplay(questionElement.getAttribute("title"));
		observation.getCode().addCoding().setSystem("https://CAP.org").setCode(listItemElement.getAttribute("ID"))
				.setDisplay(listItemElement.getAttribute("title"));
		observation.setValue(new StringType().setValue(listItemElement.getAttribute("title")));
		observation.addDerivedFrom().setReference("DocumentReference/" + id);
		String encoded = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(observation);
		System.out.println(encoded);
		System.out.println("*******************************************************************");
		
	}

	/**
	 * Method that get the formInstranceVrsionURI and ID
	 * 
	 * @param document
	 * @return
	 */
	public static String getFormID(Document document) {
		Element root = getRootElement(document);
		NodeList nodeList = root.getElementsByTagName("FormDesign");
		Element formDesignNode = (Element) nodeList.item(0);
		return formDesignNode.getAttribute("ID") + "." + formDesignNode.getAttribute("formInstanceVersionURI");
	}

}

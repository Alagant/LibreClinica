//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.21 at 11:57:20 PM CST 
//


package org.cdisc.ns.odm.v121;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ODMcomplexTypeDefinition-Association complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ODMcomplexTypeDefinition-Association"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.2}KeySet"/&gt;
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.2}KeySet"/&gt;
 *         &lt;element ref="{http://www.cdisc.org/ns/odm/v1.2}Annotation"/&gt;
 *         &lt;group ref="{http://www.cdisc.org/ns/odm/v1.2}AssociationElementExtension" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.2}AssociationAttributeDefinition"/&gt;
 *       &lt;attGroup ref="{http://www.cdisc.org/ns/odm/v1.2}AssociationAttributeExtension"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ODMcomplexTypeDefinition-Association", propOrder = {
    "content"
})
public class ODMcomplexTypeDefinitionAssociation {

    @XmlElementRefs({
        @XmlElementRef(name = "KeySet", namespace = "http://www.cdisc.org/ns/odm/v1.2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Annotation", namespace = "http://www.cdisc.org/ns/odm/v1.2", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> content;
    @XmlAttribute(name = "StudyOID", required = true)
    protected String studyOID;
    @XmlAttribute(name = "MetaDataVersionOID", required = true)
    protected String metaDataVersionOID;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "KeySet" is used by two different parts of a schema. See: 
     * line 1543 of file:/C:/Users/lucio/PycharmProjects/Alagant/LibreClinica/odm/src/main/schemas/ODM1-2-1-foundation.xsd
     * line 1542 of file:/C:/Users/lucio/PycharmProjects/Alagant/LibreClinica/odm/src/main/schemas/ODM1-2-1-foundation.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link ODMcomplexTypeDefinitionKeySet }{@code >}
     * {@link JAXBElement }{@code <}{@link ODMcomplexTypeDefinitionAnnotation }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<?>>();
        }
        return this.content;
    }

    /**
     * Gets the value of the studyOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyOID() {
        return studyOID;
    }

    /**
     * Sets the value of the studyOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyOID(String value) {
        this.studyOID = value;
    }

    /**
     * Gets the value of the metaDataVersionOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetaDataVersionOID() {
        return metaDataVersionOID;
    }

    /**
     * Sets the value of the metaDataVersionOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetaDataVersionOID(String value) {
        this.metaDataVersionOID = value;
    }

}

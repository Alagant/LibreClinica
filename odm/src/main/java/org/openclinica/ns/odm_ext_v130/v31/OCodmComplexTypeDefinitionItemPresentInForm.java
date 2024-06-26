//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.21 at 11:57:20 PM CST 
//


package org.openclinica.ns.odm_ext_v130.v31;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OCodmComplexTypeDefinition-ItemPresentInForm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OCodmComplexTypeDefinition-ItemPresentInForm"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LeftItemText" type="{http://www.cdisc.org/ns/odm/v1.3}text" minOccurs="0"/&gt;
 *         &lt;element name="RightItemText" type="{http://www.cdisc.org/ns/odm/v1.3}text" minOccurs="0"/&gt;
 *         &lt;element name="ItemHeader" type="{http://www.cdisc.org/ns/odm/v1.3}text" minOccurs="0"/&gt;
 *         &lt;element name="ItemSubHeader" type="{http://www.cdisc.org/ns/odm/v1.3}text" minOccurs="0"/&gt;
 *         &lt;element name="SectionLabel" type="{http://www.cdisc.org/ns/odm/v1.3}text"/&gt;
 *         &lt;element ref="{http://www.openclinica.org/ns/odm_ext_v130/v3.1}ItemResponse"/&gt;
 *         &lt;element ref="{http://www.openclinica.org/ns/odm_ext_v130/v3.1}SimpleConditionalDisplay" minOccurs="0"/&gt;
 *         &lt;group ref="{http://www.openclinica.org/ns/odm_ext_v130/v3.1}PresentInFormElementExtension" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://www.openclinica.org/ns/odm_ext_v130/v3.1}ItemPresentInFormAttributeDefinition"/&gt;
 *       &lt;attGroup ref="{http://www.openclinica.org/ns/odm_ext_v130/v3.1}ItemPresentInFormAttributeExtension"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OCodmComplexTypeDefinition-ItemPresentInForm", propOrder = {
    "leftItemText",
    "rightItemText",
    "itemHeader",
    "itemSubHeader",
    "sectionLabel",
    "itemResponse",
    "simpleConditionalDisplay"
})
public class OCodmComplexTypeDefinitionItemPresentInForm {

    @XmlElement(name = "LeftItemText")
    protected String leftItemText;
    @XmlElement(name = "RightItemText")
    protected String rightItemText;
    @XmlElement(name = "ItemHeader")
    protected String itemHeader;
    @XmlElement(name = "ItemSubHeader")
    protected String itemSubHeader;
    @XmlElement(name = "SectionLabel", required = true)
    protected String sectionLabel;
    @XmlElement(name = "ItemResponse", required = true)
    protected OCodmComplexTypeDefinitionItemResponse itemResponse;
    @XmlElement(name = "SimpleConditionalDisplay")
    protected OCodmComplexTypeDefinitionSimpleConditionalDisplay simpleConditionalDisplay;
    @XmlAttribute(name = "FormOID", required = true)
    protected String formOID;
    @XmlAttribute(name = "ParentItemOID")
    protected String parentItemOID;
    @XmlAttribute(name = "ColumnNumber")
    protected BigInteger columnNumber;
    @XmlAttribute(name = "PageNumber")
    protected String pageNumber;
    @XmlAttribute(name = "DefaultValue")
    protected String defaultValue;
    @XmlAttribute(name = "PHI", required = true)
    protected String phi;
    @XmlAttribute(name = "ShowItem")
    protected String showItem;

    /**
     * Gets the value of the leftItemText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftItemText() {
        return leftItemText;
    }

    /**
     * Sets the value of the leftItemText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftItemText(String value) {
        this.leftItemText = value;
    }

    /**
     * Gets the value of the rightItemText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightItemText() {
        return rightItemText;
    }

    /**
     * Sets the value of the rightItemText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightItemText(String value) {
        this.rightItemText = value;
    }

    /**
     * Gets the value of the itemHeader property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemHeader() {
        return itemHeader;
    }

    /**
     * Sets the value of the itemHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemHeader(String value) {
        this.itemHeader = value;
    }

    /**
     * Gets the value of the itemSubHeader property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemSubHeader() {
        return itemSubHeader;
    }

    /**
     * Sets the value of the itemSubHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemSubHeader(String value) {
        this.itemSubHeader = value;
    }

    /**
     * Gets the value of the sectionLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectionLabel() {
        return sectionLabel;
    }

    /**
     * Sets the value of the sectionLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectionLabel(String value) {
        this.sectionLabel = value;
    }

    /**
     * Gets the value of the itemResponse property.
     * 
     * @return
     *     possible object is
     *     {@link OCodmComplexTypeDefinitionItemResponse }
     *     
     */
    public OCodmComplexTypeDefinitionItemResponse getItemResponse() {
        return itemResponse;
    }

    /**
     * Sets the value of the itemResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link OCodmComplexTypeDefinitionItemResponse }
     *     
     */
    public void setItemResponse(OCodmComplexTypeDefinitionItemResponse value) {
        this.itemResponse = value;
    }

    /**
     * Gets the value of the simpleConditionalDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link OCodmComplexTypeDefinitionSimpleConditionalDisplay }
     *     
     */
    public OCodmComplexTypeDefinitionSimpleConditionalDisplay getSimpleConditionalDisplay() {
        return simpleConditionalDisplay;
    }

    /**
     * Sets the value of the simpleConditionalDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link OCodmComplexTypeDefinitionSimpleConditionalDisplay }
     *     
     */
    public void setSimpleConditionalDisplay(OCodmComplexTypeDefinitionSimpleConditionalDisplay value) {
        this.simpleConditionalDisplay = value;
    }

    /**
     * Gets the value of the formOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormOID() {
        return formOID;
    }

    /**
     * Sets the value of the formOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormOID(String value) {
        this.formOID = value;
    }

    /**
     * Gets the value of the parentItemOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentItemOID() {
        return parentItemOID;
    }

    /**
     * Sets the value of the parentItemOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentItemOID(String value) {
        this.parentItemOID = value;
    }

    /**
     * Gets the value of the columnNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getColumnNumber() {
        return columnNumber;
    }

    /**
     * Sets the value of the columnNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setColumnNumber(BigInteger value) {
        this.columnNumber = value;
    }

    /**
     * Gets the value of the pageNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the value of the pageNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageNumber(String value) {
        this.pageNumber = value;
    }

    /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the phi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPHI() {
        return phi;
    }

    /**
     * Sets the value of the phi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPHI(String value) {
        this.phi = value;
    }

    /**
     * Gets the value of the showItem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShowItem() {
        return showItem;
    }

    /**
     * Sets the value of the showItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShowItem(String value) {
        this.showItem = value;
    }

}

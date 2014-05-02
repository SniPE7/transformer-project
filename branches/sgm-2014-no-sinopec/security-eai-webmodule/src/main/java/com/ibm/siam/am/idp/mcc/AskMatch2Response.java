
package com.ibm.siam.am.idp.mcc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AskMatch2Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "askMatch2Result"
})
@XmlRootElement(name = "AskMatch2Response")
public class AskMatch2Response {

    @XmlElementRef(name = "AskMatch2Result", namespace = "http://Hirsch.Match.Code", type = JAXBElement.class)
    protected JAXBElement<String> askMatch2Result;

    /**
     * Gets the value of the askMatch2Result property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAskMatch2Result() {
        return askMatch2Result;
    }

    /**
     * Sets the value of the askMatch2Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAskMatch2Result(JAXBElement<String> value) {
        this.askMatch2Result = ((JAXBElement<String> ) value);
    }

}

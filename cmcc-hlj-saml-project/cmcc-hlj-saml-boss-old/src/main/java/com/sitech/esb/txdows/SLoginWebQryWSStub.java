/**
 * SLoginWebQryWSStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */
package com.sitech.esb.txdows;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axis2.databinding.ADBException;

/*
 * SLoginWebQryWSStub java implementation
 */

public class SLoginWebQryWSStub extends org.apache.axis2.client.Stub {
  protected org.apache.axis2.description.AxisOperation[] _operations;

  // hashmaps to keep the fault mapping
  private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
  private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
  private java.util.HashMap faultMessageMap = new java.util.HashMap();

  private static int counter = 0;

  private static synchronized java.lang.String getUniqueSuffix() {
    // reset the counter if it is greater than 99999
    if (counter > 99999) {
      counter = 0;
    }
    counter = counter + 1;
    return java.lang.Long.toString(System.currentTimeMillis()) + "_" + counter;
  }

  private void populateAxisService() throws org.apache.axis2.AxisFault {

    // creating the Service with a unique name
    _service = new org.apache.axis2.description.AxisService("SLoginWebQryWS" + getUniqueSuffix());
    addAnonymousOperations();

    // creating the operations
    org.apache.axis2.description.AxisOperation __operation;

    _operations = new org.apache.axis2.description.AxisOperation[1];

    __operation = new org.apache.axis2.description.OutInAxisOperation();

    __operation.setName(new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "callService"));
    _service.addOperation(__operation);

    _operations[0] = __operation;

  }

  // populates the faults
  private void populateFaults() {

  }

  /**
   * Constructor that takes in a configContext
   */

  public SLoginWebQryWSStub(org.apache.axis2.context.ConfigurationContext configurationContext, java.lang.String targetEndpoint)
      throws org.apache.axis2.AxisFault {
    this(configurationContext, targetEndpoint, false);
  }

  /**
   * Constructor that takes in a configContext and useseperate listner
   */
  public SLoginWebQryWSStub(org.apache.axis2.context.ConfigurationContext configurationContext, java.lang.String targetEndpoint, boolean useSeparateListener)
      throws org.apache.axis2.AxisFault {
    // To populate AxisService
    populateAxisService();
    populateFaults();

    _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext, _service);

    _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(targetEndpoint));
    _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);

    // Set the soap version
    _serviceClient.getOptions().setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);

  }

  /**
   * Default Constructor
   */
  public SLoginWebQryWSStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

    this(configurationContext, "tcp://10.110.0.206:52000/esbWS/services/sLoginWebQryWS");

  }

  /**
   * Default Constructor
   */
  public SLoginWebQryWSStub() throws org.apache.axis2.AxisFault {

    this("tcp://10.110.0.206:52000/esbWS/services/sLoginWebQryWS");

  }

  /**
   * Constructor taking the target endpoint
   */
  public SLoginWebQryWSStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
    this(null, targetEndpoint);
  }

  /**
   * Auto generated method signature
   * 
   * @see com.sitech.esb.txdows.SLoginWebQryWS#callService
   * @param callService
   * 
   */

  public com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse callService(

  com.sitech.esb.txdows.SLoginWebQryWSStub.CallService callService)

  throws java.rmi.RemoteException

  {
    org.apache.axis2.context.MessageContext _messageContext = null;
    try {
      org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
      _operationClient.getOptions().setAction("urn:callService");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

      addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

      // create a message context
      _messageContext = new org.apache.axis2.context.MessageContext();

      // create SOAP envelope with that payload
      org.apache.axiom.soap.SOAPEnvelope env = null;

      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), callService, optimizeContent(new javax.xml.namespace.QName(
          "http://txdoWS.esb.sitech.com", "callService")));

      // adding SOAP soap_headers
      _serviceClient.addHeadersToEnvelope(env);
      // set the message context with that soap envelope
      _messageContext.setEnvelope(env);

      // add the message contxt to the operation client
      _operationClient.addMessageContext(_messageContext);

      // execute the operation client
      _operationClient.execute(true);

      org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
          .getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
      org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

      java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse.class,
          getEnvelopeNamespaces(_returnEnv));

      return (com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse) object;

    } catch (org.apache.axis2.AxisFault f) {

      org.apache.axiom.om.OMElement faultElt = f.getDetail();
      if (faultElt != null) {
        if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
          // make the fault by reflection
          try {
            java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap.get(faultElt.getQName());
            java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
            java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
            // message class
            java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(faultElt.getQName());
            java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
            java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
            java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] { messageClass });
            m.invoke(ex, new java.lang.Object[] { messageObject });

            throw new java.rmi.RemoteException(ex.getMessage(), ex);
          } catch (java.lang.ClassCastException e) {
            // we cannot intantiate the class - throw the original
            // Axis fault
            throw f;
          } catch (java.lang.ClassNotFoundException e) {
            // we cannot intantiate the class - throw the original
            // Axis fault
            throw f;
          } catch (java.lang.NoSuchMethodException e) {
            // we cannot intantiate the class - throw the original
            // Axis fault
            throw f;
          } catch (java.lang.reflect.InvocationTargetException e) {
            // we cannot intantiate the class - throw the original
            // Axis fault
            throw f;
          } catch (java.lang.IllegalAccessException e) {
            // we cannot intantiate the class - throw the original
            // Axis fault
            throw f;
          } catch (java.lang.InstantiationException e) {
            // we cannot intantiate the class - throw the original
            // Axis fault
            throw f;
          }
        } else {
          throw f;
        }
      } else {
        throw f;
      }
    } finally {
      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
    }
  }

  /**
   * A utility method that copies the namepaces from the SOAPEnvelope
   */
  private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env) {
    java.util.Map returnMap = new java.util.HashMap();
    java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
    while (namespaceIterator.hasNext()) {
      org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
      returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
    }
    return returnMap;
  }

  private javax.xml.namespace.QName[] opNameArray = null;

  private boolean optimizeContent(javax.xml.namespace.QName opName) {

    if (opNameArray == null) {
      return false;
    }
    for (int i = 0; i < opNameArray.length; i++) {
      if (opName.equals(opNameArray[i])) {
        return true;
      }
    }
    return false;
  }

  // tcp://10.110.0.206:52000/esbWS/services/sLoginWebQryWS
  public static class SrvReturnBean implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name =
     * SrvReturnBean Namespace URI = http://ws.esb.sitech.com/xsd Namespace
     * Prefix = ns1
     */

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("http://ws.esb.sitech.com/xsd")) {
        return "ns1";
      }
      return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * field for EsbRetCode
     */

    protected int localEsbRetCode;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localEsbRetCodeTracker = false;

    /**
     * Auto generated getter method
     * 
     * @return int
     */
    public int getEsbRetCode() {
      return localEsbRetCode;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *          EsbRetCode
     */
    public void setEsbRetCode(int param) {

      // setting primitive attribute tracker to true

      if (param == java.lang.Integer.MIN_VALUE) {
        localEsbRetCodeTracker = false;

      } else {
        localEsbRetCodeTracker = true;
      }

      this.localEsbRetCode = param;

    }

    /**
     * field for RetCode
     */

    protected java.lang.String localRetCode;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localRetCodeTracker = false;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getRetCode() {
      return localRetCode;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *          RetCode
     */
    public void setRetCode(java.lang.String param) {

      if (param != null) {
        // update the setting tracker
        localRetCodeTracker = true;
      } else {
        localRetCodeTracker = true;

      }

      this.localRetCode = param;

    }

    /**
     * field for RetMatrix This was an Array!
     */

    protected TxdoBuf[] localRetMatrix;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localRetMatrixTracker = false;

    /**
     * Auto generated getter method
     * 
     * @return TxdoBuf[]
     */
    public TxdoBuf[] getRetMatrix() {
      return localRetMatrix;
    }

    /**
     * validate the array for RetMatrix
     */
    protected void validateRetMatrix(TxdoBuf[] param) {

    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *          RetMatrix
     */
    public void setRetMatrix(TxdoBuf[] param) {

      validateRetMatrix(param);

      if (param != null) {
        // update the setting tracker
        localRetMatrixTracker = true;
      } else {
        localRetMatrixTracker = true;

      }

      this.localRetMatrix = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * 
     * @param param
     *          TxdoBuf
     */
    public void addRetMatrix(TxdoBuf param) {
      if (localRetMatrix == null) {
        localRetMatrix = new TxdoBuf[] {};
      }

      // update the setting tracker
      localRetMatrixTracker = true;

      java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localRetMatrix);
      list.add(param);
      this.localRetMatrix = (TxdoBuf[]) list.toArray(new TxdoBuf[list.size()]);

    }

    /**
     * field for RetMsg
     */

    protected java.lang.String localRetMsg;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localRetMsgTracker = false;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getRetMsg() {
      return localRetMsg;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *          RetMsg
     */
    public void setRetMsg(java.lang.String param) {

      if (param != null) {
        // update the setting tracker
        localRetMsgTracker = true;
      } else {
        localRetMsgTracker = true;

      }

      this.localRetMsg = param;

    }

    /**
     * isReaderMTOMAware
     * 
     * @return true if the reader supports MTOM
     */
    public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;

      try {
        isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
      } catch (java.lang.IllegalArgumentException e) {
        isReaderMTOMAware = false;
      }
      return isReaderMTOMAware;
    }

    /**
     * 
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName) {

				public void serialize(XMLStreamWriter xmlWriter)
						throws javax.xml.stream.XMLStreamException {
					SrvReturnBean.this.serialize(parentQName, factory,
							xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
					parentQName, factory, dataSource);

		}

    /**
     * @param parentQName
     * @param factory
     * @param xmlWriter
     * @throws javax.xml.stream.XMLStreamException
     * @throws org.apache.axis2.databinding.ADBException
     */
    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory, XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory, XMLStreamWriter xmlWriter,
        boolean serializeType) throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {

      java.lang.String prefix = null;
      java.lang.String namespace = null;

      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();

      if ((namespace != null) && (namespace.trim().length() > 0)) {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null) {
            prefix = generatePrefix(namespace);
          }

          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        }
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      }

      if (serializeType) {

        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://ws.esb.sitech.com/xsd");
        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":SrvReturnBean", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "SrvReturnBean", xmlWriter);
        }

      }
      if (localEsbRetCodeTracker) {
        namespace = "http://ws.esb.sitech.com/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);

          if (prefix == null) {
            prefix = generatePrefix(namespace);

            xmlWriter.writeStartElement(prefix, "esbRetCode", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);

          } else {
            xmlWriter.writeStartElement(namespace, "esbRetCode");
          }

        } else {
          xmlWriter.writeStartElement("esbRetCode");
        }

        if (localEsbRetCode == java.lang.Integer.MIN_VALUE) {

          throw new org.apache.axis2.databinding.ADBException("esbRetCode cannot be null!!");

        } else {
          xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsbRetCode));
        }

        xmlWriter.writeEndElement();
      }
      if (localRetCodeTracker) {
        namespace = "http://ws.esb.sitech.com/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);

          if (prefix == null) {
            prefix = generatePrefix(namespace);

            xmlWriter.writeStartElement(prefix, "retCode", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);

          } else {
            xmlWriter.writeStartElement(namespace, "retCode");
          }

        } else {
          xmlWriter.writeStartElement("retCode");
        }

        if (localRetCode == null) {
          // write the nil attribute

          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);

        } else {

          xmlWriter.writeCharacters(localRetCode);

        }

        xmlWriter.writeEndElement();
      }
      if (localRetMatrixTracker) {
        if (localRetMatrix != null) {
          for (int i = 0; i < localRetMatrix.length; i++) {
            if (localRetMatrix[i] != null) {
              localRetMatrix[i].serialize(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retMatrix"), factory, xmlWriter);
            } else {

              // write null attribute
              java.lang.String namespace2 = "http://ws.esb.sitech.com/xsd";
              if (!namespace2.equals("")) {
                java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                if (prefix2 == null) {
                  prefix2 = generatePrefix(namespace2);

                  xmlWriter.writeStartElement(prefix2, "retMatrix", namespace2);
                  xmlWriter.writeNamespace(prefix2, namespace2);
                  xmlWriter.setPrefix(prefix2, namespace2);

                } else {
                  xmlWriter.writeStartElement(namespace2, "retMatrix");
                }

              } else {
                xmlWriter.writeStartElement("retMatrix");
              }

              // write the nil attribute
              writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
              xmlWriter.writeEndElement();

            }

          }
        } else {

          // write null attribute
          java.lang.String namespace2 = "http://ws.esb.sitech.com/xsd";
          if (!namespace2.equals("")) {
            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

            if (prefix2 == null) {
              prefix2 = generatePrefix(namespace2);

              xmlWriter.writeStartElement(prefix2, "retMatrix", namespace2);
              xmlWriter.writeNamespace(prefix2, namespace2);
              xmlWriter.setPrefix(prefix2, namespace2);

            } else {
              xmlWriter.writeStartElement(namespace2, "retMatrix");
            }

          } else {
            xmlWriter.writeStartElement("retMatrix");
          }

          // write the nil attribute
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
          xmlWriter.writeEndElement();

        }
      }
      if (localRetMsgTracker) {
        namespace = "http://ws.esb.sitech.com/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);

          if (prefix == null) {
            prefix = generatePrefix(namespace);

            xmlWriter.writeStartElement(prefix, "retMsg", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);

          } else {
            xmlWriter.writeStartElement(namespace, "retMsg");
          }

        } else {
          xmlWriter.writeStartElement("retMsg");
        }

        if (localRetMsg == null) {
          // write the nil attribute

          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);

        } else {

          xmlWriter.writeCharacters(localRetMsg);

        }

        xmlWriter.writeEndElement();
      }
      xmlWriter.writeEndElement();

    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix, java.lang.String namespace, java.lang.String attName, java.lang.String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);

      }

      xmlWriter.writeAttribute(namespace, attName, attValue);

    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace, java.lang.String attName, java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

      java.lang.String attributeNamespace = qname.getNamespaceURI();
      java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null) {
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
      }
      java.lang.String attributeValue;
      if (attributePrefix.trim().length() > 0) {
        attributeValue = attributePrefix + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      }

      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      }
    }

    /**
     * method to handle Qnames
     */

    private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
      java.lang.String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        }

        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        } else {
          // i.e this is the default namespace
          xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        }

      } else {
        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
      }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

      if (qnames != null) {
        // we have to store this data until last moment since it is not
        // possible to write any
        // namespace data after writing the charactor data
        java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
        java.lang.String namespaceURI = null;
        java.lang.String prefix = null;

        for (int i = 0; i < qnames.length; i++) {
          if (i > 0) {
            stringToWrite.append(" ");
          }
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if ((prefix == null) || (prefix.length() == 0)) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
            }
          } else {
            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
          }
        }
        xmlWriter.writeCharacters(stringToWrite.toString());
      }

    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
      java.lang.String prefix = xmlWriter.getPrefix(namespace);

      if (prefix == null) {
        prefix = generatePrefix(namespace);

        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
          prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      }

      return prefix;
    }

    /**
     * databinding method to get an XML representation of this object
     * 
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException {

      java.util.ArrayList elementList = new java.util.ArrayList();
      java.util.ArrayList attribList = new java.util.ArrayList();

      if (localEsbRetCodeTracker) {
        elementList.add(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "esbRetCode"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsbRetCode));
      }
      if (localRetCodeTracker) {
        elementList.add(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retCode"));

        elementList.add(localRetCode == null ? null : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRetCode));
      }
      if (localRetMatrixTracker) {
        if (localRetMatrix != null) {
          for (int i = 0; i < localRetMatrix.length; i++) {

            if (localRetMatrix[i] != null) {
              elementList.add(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retMatrix"));
              elementList.add(localRetMatrix[i]);
            } else {

              elementList.add(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retMatrix"));
              elementList.add(null);

            }

          }
        } else {

          elementList.add(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retMatrix"));
          elementList.add(localRetMatrix);

        }

      }
      if (localRetMsgTracker) {
        elementList.add(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retMsg"));

        elementList.add(localRetMsg == null ? null : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRetMsg));
      }

      return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());

    }

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory {

      /**
       * static method to create the object Precondition: If this object is an
       * element, the current or next start element starts this object and any
       * intervening reader events are ignorable If this object is not an
       * element, it is a complex type and the reader is at the event just after
       * the outer start element Postcondition: If this object is an element,
       * the reader is positioned at its end element If this object is a complex
       * type, the reader is positioned at the end element of its outer element
       */
      public static SrvReturnBean parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
        SrvReturnBean object = new SrvReturnBean();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";
        try {

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (fullTypeName != null) {
              java.lang.String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1) {
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
              }
              nsPrefix = nsPrefix == null ? "" : nsPrefix;

              java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

              if (!"SrvReturnBean".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (SrvReturnBean) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }

            }

          }

          // Note all attributes that were handled. Used to differ
          // normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          java.util.ArrayList list3 = new java.util.ArrayList();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "esbRetCode").equals(reader.getName())) {

            java.lang.String content = reader.getElementText();

            object.setEsbRetCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

            reader.next();

          } // End of if for expected property start element

          else {

            object.setEsbRetCode(java.lang.Integer.MIN_VALUE);

          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retCode").equals(reader.getName())) {

            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {

              java.lang.String content = reader.getElementText();

              object.setRetCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

            } else {

              reader.getElementText(); // throw away text nodes
              // if any.
            }

            reader.next();

          } // End of if for expected property start element

          else {

          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retMatrix").equals(reader.getName())) {

            // Process the array and step past its final element's
            // end.

            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              list3.add(null);
              reader.next();
            } else {
              list3.add(TxdoBuf.Factory.parse(reader));
            }
            // loop until we find a start element that is not part
            // of this array
            boolean loopDone3 = false;
            while (!loopDone3) {
              // We should be at the end element, but make sure
              while (!reader.isEndElement())
                reader.next();
              // Step out of this element
              reader.next();
              // Step to next element event.
              while (!reader.isStartElement() && !reader.isEndElement())
                reader.next();
              if (reader.isEndElement()) {
                // two continuous end elements means we are
                // exiting the xml structure
                loopDone3 = true;
              } else {
                if (new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retMatrix").equals(reader.getName())) {

                  nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
                  if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
                    list3.add(null);
                    reader.next();
                  } else {
                    list3.add(TxdoBuf.Factory.parse(reader));
                  }
                } else {
                  loopDone3 = true;
                }
              }
            }
            // call the converter utility to convert and set the
            // array

            object.setRetMatrix((TxdoBuf[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(TxdoBuf.class, list3));

          } // End of if for expected property start element

          else {

          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "retMsg").equals(reader.getName())) {

            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {

              java.lang.String content = reader.getElementText();

              object.setRetMsg(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

            } else {

              reader.getElementText(); // throw away text nodes
              // if any.
            }

            reader.next();

          } // End of if for expected property start element

          else {

          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement())
            // A start element we are not expecting indicates a
            // trailing invalid property
            throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }

    }// end of factory class

    public void serialize(QName arg0, XMLStreamWriter arg1) throws XMLStreamException, ADBException {
      // TODO Auto-generated method stub
      
    }

    public void serialize(QName arg0, XMLStreamWriter arg1, boolean arg2) throws XMLStreamException, ADBException {
      // TODO Auto-generated method stub
      
    }

  }

  public static class CallServiceResponse implements org.apache.axis2.databinding.ADBBean {

    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "callServiceResponse", "ns2");

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("http://txdoWS.esb.sitech.com")) {
        return "ns2";
      }
      return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * field for _return
     */

    protected SrvReturnBean local_return;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean local_returnTracker = false;

    /**
     * Auto generated getter method
     * 
     * @return SrvReturnBean
     */
    public SrvReturnBean get_return() {
      return local_return;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *          _return
     */
    public void set_return(SrvReturnBean param) {

      if (param != null) {
        // update the setting tracker
        local_returnTracker = true;
      } else {
        local_returnTracker = true;

      }

      this.local_return = param;

    }

    /**
     * isReaderMTOMAware
     * 
     * @return true if the reader supports MTOM
     */
    public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;

      try {
        isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
      } catch (java.lang.IllegalArgumentException e) {
        isReaderMTOMAware = false;
      }
      return isReaderMTOMAware;
    }

    /**
     * 
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory)
        throws org.apache.axis2.databinding.ADBException {

      org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this, MY_QNAME) {

        public void serialize(XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
          CallServiceResponse.this.serialize(MY_QNAME, factory, xmlWriter);
        }
      };
      return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME, factory, dataSource);

    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory, XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory, XMLStreamWriter xmlWriter,
        boolean serializeType) throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {

      java.lang.String prefix = null;
      java.lang.String namespace = null;

      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();

      if ((namespace != null) && (namespace.trim().length() > 0)) {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null) {
            prefix = generatePrefix(namespace);
          }

          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        }
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      }

      if (serializeType) {

        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://txdoWS.esb.sitech.com");
        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":callServiceResponse", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "callServiceResponse", xmlWriter);
        }

      }
      if (local_returnTracker) {
        if (local_return == null) {

          java.lang.String namespace2 = "http://txdoWS.esb.sitech.com";

          if (!namespace2.equals("")) {
            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

            if (prefix2 == null) {
              prefix2 = generatePrefix(namespace2);

              xmlWriter.writeStartElement(prefix2, "return", namespace2);
              xmlWriter.writeNamespace(prefix2, namespace2);
              xmlWriter.setPrefix(prefix2, namespace2);

            } else {
              xmlWriter.writeStartElement(namespace2, "return");
            }

          } else {
            xmlWriter.writeStartElement("return");
          }

          // write the nil attribute
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
          xmlWriter.writeEndElement();
        } else {
          local_return.serialize(new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "return"), factory, xmlWriter);
        }
      }
      xmlWriter.writeEndElement();

    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix, java.lang.String namespace, java.lang.String attName, java.lang.String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);

      }

      xmlWriter.writeAttribute(namespace, attName, attValue);

    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace, java.lang.String attName, java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

      java.lang.String attributeNamespace = qname.getNamespaceURI();
      java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null) {
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
      }
      java.lang.String attributeValue;
      if (attributePrefix.trim().length() > 0) {
        attributeValue = attributePrefix + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      }

      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      }
    }

    /**
     * method to handle Qnames
     */

    private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
      java.lang.String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        }

        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        } else {
          // i.e this is the default namespace
          xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        }

      } else {
        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
      }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

      if (qnames != null) {
        // we have to store this data until last moment since it is not
        // possible to write any
        // namespace data after writing the charactor data
        java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
        java.lang.String namespaceURI = null;
        java.lang.String prefix = null;

        for (int i = 0; i < qnames.length; i++) {
          if (i > 0) {
            stringToWrite.append(" ");
          }
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if ((prefix == null) || (prefix.length() == 0)) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
            }
          } else {
            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
          }
        }
        xmlWriter.writeCharacters(stringToWrite.toString());
      }

    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
      java.lang.String prefix = xmlWriter.getPrefix(namespace);

      if (prefix == null) {
        prefix = generatePrefix(namespace);

        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
          prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      }

      return prefix;
    }

    /**
     * databinding method to get an XML representation of this object
     * 
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException {

      java.util.ArrayList elementList = new java.util.ArrayList();
      java.util.ArrayList attribList = new java.util.ArrayList();

      if (local_returnTracker) {
        elementList.add(new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "return"));

        elementList.add(local_return == null ? null : local_return);
      }

      return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());

    }

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory {

      /**
       * static method to create the object Precondition: If this object is an
       * element, the current or next start element starts this object and any
       * intervening reader events are ignorable If this object is not an
       * element, it is a complex type and the reader is at the event just after
       * the outer start element Postcondition: If this object is an element,
       * the reader is positioned at its end element If this object is a complex
       * type, the reader is positioned at the end element of its outer element
       */
      public static CallServiceResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
        CallServiceResponse object = new CallServiceResponse();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";
        try {

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (fullTypeName != null) {
              java.lang.String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1) {
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
              }
              nsPrefix = nsPrefix == null ? "" : nsPrefix;

              java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

              if (!"callServiceResponse".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (CallServiceResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }

            }

          }

          // Note all attributes that were handled. Used to differ
          // normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "return").equals(reader.getName())) {

            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              object.set_return(null);
              reader.next();

              reader.next();

            } else {

              object.set_return(SrvReturnBean.Factory.parse(reader));

              reader.next();
            }
          } // End of if for expected property start element

          else {

          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement())
            // A start element we are not expecting indicates a
            // trailing invalid property
            throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }

    }// end of factory class

    public void serialize(QName arg0, XMLStreamWriter arg1) throws XMLStreamException, ADBException {
      // TODO Auto-generated method stub

    }

    public void serialize(QName arg0, XMLStreamWriter arg1, boolean arg2) throws XMLStreamException, ADBException {
      // TODO Auto-generated method stub

    }

  }

  public static class ExtensionMapper {

    public static java.lang.Object getTypeObject(java.lang.String namespaceURI, java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {

      if ("http://ws.esb.sitech.com/xsd".equals(namespaceURI) && "SrvReturnBean".equals(typeName)) {

        return SrvReturnBean.Factory.parse(reader);

      }

      if ("http://ws.esb.sitech.com/xsd".equals(namespaceURI) && "TxdoBuf".equals(typeName)) {

        return TxdoBuf.Factory.parse(reader);

      }

      throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
    }

  }

  public static class CallService implements org.apache.axis2.databinding.ADBBean {

    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "callService", "ns2");

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("http://txdoWS.esb.sitech.com")) {
        return "ns2";
      }
      return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * field for Params This was an Array!
     */

    protected java.lang.String[] localParams;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localParamsTracker = false;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String[]
     */
    public java.lang.String[] getParams() {
      return localParams;
    }

    /**
     * validate the array for Params
     */
    protected void validateParams(java.lang.String[] param) {

    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *          Params
     */
    public void setParams(java.lang.String[] param) {

      validateParams(param);

      if (param != null) {
        // update the setting tracker
        localParamsTracker = true;
      } else {
        localParamsTracker = true;

      }

      this.localParams = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * 
     * @param param
     *          java.lang.String
     */
    public void addParams(java.lang.String param) {
      if (localParams == null) {
        localParams = new java.lang.String[] {};
      }

      // update the setting tracker
      localParamsTracker = true;

      java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localParams);
      list.add(param);
      this.localParams = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);

    }

    /**
     * isReaderMTOMAware
     * 
     * @return true if the reader supports MTOM
     */
    public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;

      try {
        isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
      } catch (java.lang.IllegalArgumentException e) {
        isReaderMTOMAware = false;
      }
      return isReaderMTOMAware;
    }

    /**
     * 
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory)
        throws org.apache.axis2.databinding.ADBException {

      org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this, MY_QNAME) {

        public void serialize(XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
          CallService.this.serialize(MY_QNAME, factory, xmlWriter);
        }
      };
      return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME, factory, dataSource);

    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory, XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory, XMLStreamWriter xmlWriter,
        boolean serializeType) throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {

      java.lang.String prefix = null;
      java.lang.String namespace = null;

      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();

      if ((namespace != null) && (namespace.trim().length() > 0)) {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null) {
            prefix = generatePrefix(namespace);
          }

          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        }
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      }

      if (serializeType) {

        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://txdoWS.esb.sitech.com");
        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":callService", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "callService", xmlWriter);
        }

      }
      if (localParamsTracker) {
        if (localParams != null) {
          namespace = "http://txdoWS.esb.sitech.com";
          boolean emptyNamespace = namespace == null || namespace.length() == 0;
          prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);
          for (int i = 0; i < localParams.length; i++) {

            if (localParams[i] != null) {

              if (!emptyNamespace) {
                if (prefix == null) {
                  java.lang.String prefix2 = generatePrefix(namespace);

                  xmlWriter.writeStartElement(prefix2, "params", namespace);
                  xmlWriter.writeNamespace(prefix2, namespace);
                  xmlWriter.setPrefix(prefix2, namespace);

                } else {
                  xmlWriter.writeStartElement(namespace, "params");
                }

              } else {
                xmlWriter.writeStartElement("params");
              }

              xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localParams[i]));

              xmlWriter.writeEndElement();

            } else {

              // write null attribute
              namespace = "http://txdoWS.esb.sitech.com";
              if (!namespace.equals("")) {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                  prefix = generatePrefix(namespace);

                  xmlWriter.writeStartElement(prefix, "params", namespace);
                  xmlWriter.writeNamespace(prefix, namespace);
                  xmlWriter.setPrefix(prefix, namespace);

                } else {
                  xmlWriter.writeStartElement(namespace, "params");
                }

              } else {
                xmlWriter.writeStartElement("params");
              }
              writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
              xmlWriter.writeEndElement();

            }

          }
        } else {

          // write the null attribute
          // write null attribute
          java.lang.String namespace2 = "http://txdoWS.esb.sitech.com";
          if (!namespace2.equals("")) {
            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

            if (prefix2 == null) {
              prefix2 = generatePrefix(namespace2);

              xmlWriter.writeStartElement(prefix2, "params", namespace2);
              xmlWriter.writeNamespace(prefix2, namespace2);
              xmlWriter.setPrefix(prefix2, namespace2);

            } else {
              xmlWriter.writeStartElement(namespace2, "params");
            }

          } else {
            xmlWriter.writeStartElement("params");
          }

          // write the nil attribute
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
          xmlWriter.writeEndElement();

        }

      }
      xmlWriter.writeEndElement();

    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix, java.lang.String namespace, java.lang.String attName, java.lang.String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);

      }

      xmlWriter.writeAttribute(namespace, attName, attValue);

    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace, java.lang.String attName, java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

      java.lang.String attributeNamespace = qname.getNamespaceURI();
      java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null) {
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
      }
      java.lang.String attributeValue;
      if (attributePrefix.trim().length() > 0) {
        attributeValue = attributePrefix + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      }

      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      }
    }

    /**
     * method to handle Qnames
     */

    private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
      java.lang.String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        }

        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        } else {
          // i.e this is the default namespace
          xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        }

      } else {
        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
      }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

      if (qnames != null) {
        // we have to store this data until last moment since it is not
        // possible to write any
        // namespace data after writing the charactor data
        java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
        java.lang.String namespaceURI = null;
        java.lang.String prefix = null;

        for (int i = 0; i < qnames.length; i++) {
          if (i > 0) {
            stringToWrite.append(" ");
          }
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if ((prefix == null) || (prefix.length() == 0)) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
            }
          } else {
            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
          }
        }
        xmlWriter.writeCharacters(stringToWrite.toString());
      }

    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
      java.lang.String prefix = xmlWriter.getPrefix(namespace);

      if (prefix == null) {
        prefix = generatePrefix(namespace);

        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
          prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      }

      return prefix;
    }

    /**
     * databinding method to get an XML representation of this object
     * 
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException {

      java.util.ArrayList elementList = new java.util.ArrayList();
      java.util.ArrayList attribList = new java.util.ArrayList();

      if (localParamsTracker) {
        if (localParams != null) {
          for (int i = 0; i < localParams.length; i++) {

            if (localParams[i] != null) {
              elementList.add(new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "params"));
              elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localParams[i]));
            } else {

              elementList.add(new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "params"));
              elementList.add(null);

            }

          }
        } else {

          elementList.add(new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "params"));
          elementList.add(null);

        }

      }

      return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());

    }

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory {

      /**
       * static method to create the object Precondition: If this object is an
       * element, the current or next start element starts this object and any
       * intervening reader events are ignorable If this object is not an
       * element, it is a complex type and the reader is at the event just after
       * the outer start element Postcondition: If this object is an element,
       * the reader is positioned at its end element If this object is a complex
       * type, the reader is positioned at the end element of its outer element
       */
      public static CallService parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
        CallService object = new CallService();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";
        try {

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (fullTypeName != null) {
              java.lang.String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1) {
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
              }
              nsPrefix = nsPrefix == null ? "" : nsPrefix;

              java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

              if (!"callService".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (CallService) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }

            }

          }

          // Note all attributes that were handled. Used to differ
          // normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          java.util.ArrayList list1 = new java.util.ArrayList();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "params").equals(reader.getName())) {

            // Process the array and step past its final element's
            // end.

            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              list1.add(null);

              reader.next();
            } else {
              list1.add(reader.getElementText());
            }
            // loop until we find a start element that is not part
            // of this array
            boolean loopDone1 = false;
            while (!loopDone1) {
              // Ensure we are at the EndElement
              while (!reader.isEndElement()) {
                reader.next();
              }
              // Step out of this element
              reader.next();
              // Step to next element event.
              while (!reader.isStartElement() && !reader.isEndElement())
                reader.next();
              if (reader.isEndElement()) {
                // two continuous end elements means we are
                // exiting the xml structure
                loopDone1 = true;
              } else {
                if (new javax.xml.namespace.QName("http://txdoWS.esb.sitech.com", "params").equals(reader.getName())) {

                  nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
                  if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
                    list1.add(null);

                    reader.next();
                  } else {
                    list1.add(reader.getElementText());
                  }
                } else {
                  loopDone1 = true;
                }
              }
            }
            // call the converter utility to convert and set the
            // array

            object.setParams((java.lang.String[]) list1.toArray(new java.lang.String[list1.size()]));

          } // End of if for expected property start element

          else {

          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement())
            // A start element we are not expecting indicates a
            // trailing invalid property
            throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }

    }// end of factory class

    public void serialize(QName arg0, XMLStreamWriter arg1) throws XMLStreamException, ADBException {
      // TODO Auto-generated method stub

    }

    public void serialize(QName arg0, XMLStreamWriter arg1, boolean arg2) throws XMLStreamException, ADBException {
      // TODO Auto-generated method stub

    }

  }

  public static class TxdoBuf implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name = TxdoBuf
     * Namespace URI = http://ws.esb.sitech.com/xsd Namespace Prefix = ns1
     */

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("http://ws.esb.sitech.com/xsd")) {
        return "ns1";
      }
      return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * field for Buffer This was an Array!
     */

    protected java.lang.String[] localBuffer;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localBufferTracker = false;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String[]
     */
    public java.lang.String[] getBuffer() {
      return localBuffer;
    }

    /**
     * validate the array for Buffer
     */
    protected void validateBuffer(java.lang.String[] param) {

    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *          Buffer
     */
    public void setBuffer(java.lang.String[] param) {

      validateBuffer(param);

      if (param != null) {
        // update the setting tracker
        localBufferTracker = true;
      } else {
        localBufferTracker = true;

      }

      this.localBuffer = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * 
     * @param param
     *          java.lang.String
     */
    public void addBuffer(java.lang.String param) {
      if (localBuffer == null) {
        localBuffer = new java.lang.String[] {};
      }

      // update the setting tracker
      localBufferTracker = true;

      java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localBuffer);
      list.add(param);
      this.localBuffer = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);

    }

    /**
     * isReaderMTOMAware
     * 
     * @return true if the reader supports MTOM
     */
    public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;

      try {
        isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
      } catch (java.lang.IllegalArgumentException e) {
        isReaderMTOMAware = false;
      }
      return isReaderMTOMAware;
    }

    /**
     * 
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory)
        throws org.apache.axis2.databinding.ADBException {

      org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this, parentQName) {

        public void serialize(XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
          TxdoBuf.this.serialize(parentQName, factory, xmlWriter);
        }
      };
      return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName, factory, dataSource);

    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory, XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory, XMLStreamWriter xmlWriter,
        boolean serializeType) throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {

      java.lang.String prefix = null;
      java.lang.String namespace = null;

      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();

      if ((namespace != null) && (namespace.trim().length() > 0)) {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null) {
            prefix = generatePrefix(namespace);
          }

          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        }
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      }

      if (serializeType) {

        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://ws.esb.sitech.com/xsd");
        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":TxdoBuf", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "TxdoBuf", xmlWriter);
        }

      }
      if (localBufferTracker) {
        if (localBuffer != null) {
          namespace = "http://ws.esb.sitech.com/xsd";
          boolean emptyNamespace = namespace == null || namespace.length() == 0;
          prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);
          for (int i = 0; i < localBuffer.length; i++) {

            if (localBuffer[i] != null) {

              if (!emptyNamespace) {
                if (prefix == null) {
                  java.lang.String prefix2 = generatePrefix(namespace);

                  xmlWriter.writeStartElement(prefix2, "buffer", namespace);
                  xmlWriter.writeNamespace(prefix2, namespace);
                  xmlWriter.setPrefix(prefix2, namespace);

                } else {
                  xmlWriter.writeStartElement(namespace, "buffer");
                }

              } else {
                xmlWriter.writeStartElement("buffer");
              }

              xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBuffer[i]));

              xmlWriter.writeEndElement();

            } else {

              // write null attribute
              namespace = "http://ws.esb.sitech.com/xsd";
              if (!namespace.equals("")) {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                  prefix = generatePrefix(namespace);

                  xmlWriter.writeStartElement(prefix, "buffer", namespace);
                  xmlWriter.writeNamespace(prefix, namespace);
                  xmlWriter.setPrefix(prefix, namespace);

                } else {
                  xmlWriter.writeStartElement(namespace, "buffer");
                }

              } else {
                xmlWriter.writeStartElement("buffer");
              }
              writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
              xmlWriter.writeEndElement();

            }

          }
        } else {

          // write the null attribute
          // write null attribute
          java.lang.String namespace2 = "http://ws.esb.sitech.com/xsd";
          if (!namespace2.equals("")) {
            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

            if (prefix2 == null) {
              prefix2 = generatePrefix(namespace2);

              xmlWriter.writeStartElement(prefix2, "buffer", namespace2);
              xmlWriter.writeNamespace(prefix2, namespace2);
              xmlWriter.setPrefix(prefix2, namespace2);

            } else {
              xmlWriter.writeStartElement(namespace2, "buffer");
            }

          } else {
            xmlWriter.writeStartElement("buffer");
          }

          // write the nil attribute
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
          xmlWriter.writeEndElement();

        }

      }
      xmlWriter.writeEndElement();

    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix, java.lang.String namespace, java.lang.String attName, java.lang.String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);

      }

      xmlWriter.writeAttribute(namespace, attName, attValue);

    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace, java.lang.String attName, java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

      java.lang.String attributeNamespace = qname.getNamespaceURI();
      java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null) {
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
      }
      java.lang.String attributeValue;
      if (attributePrefix.trim().length() > 0) {
        attributeValue = attributePrefix + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      }

      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      }
    }

    /**
     * method to handle Qnames
     */

    private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
      java.lang.String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        }

        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        } else {
          // i.e this is the default namespace
          xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        }

      } else {
        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
      }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

      if (qnames != null) {
        // we have to store this data until last moment since it is not
        // possible to write any
        // namespace data after writing the charactor data
        java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
        java.lang.String namespaceURI = null;
        java.lang.String prefix = null;

        for (int i = 0; i < qnames.length; i++) {
          if (i > 0) {
            stringToWrite.append(" ");
          }
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if ((prefix == null) || (prefix.length() == 0)) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
            }
          } else {
            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
          }
        }
        xmlWriter.writeCharacters(stringToWrite.toString());
      }

    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
      java.lang.String prefix = xmlWriter.getPrefix(namespace);

      if (prefix == null) {
        prefix = generatePrefix(namespace);

        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
          prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      }

      return prefix;
    }

    /**
     * databinding method to get an XML representation of this object
     * 
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException {

      java.util.ArrayList elementList = new java.util.ArrayList();
      java.util.ArrayList attribList = new java.util.ArrayList();

      if (localBufferTracker) {
        if (localBuffer != null) {
          for (int i = 0; i < localBuffer.length; i++) {

            if (localBuffer[i] != null) {
              elementList.add(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "buffer"));
              elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBuffer[i]));
            } else {

              elementList.add(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "buffer"));
              elementList.add(null);

            }

          }
        } else {

          elementList.add(new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "buffer"));
          elementList.add(null);

        }

      }

      return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());

    }

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory {

      /**
       * static method to create the object Precondition: If this object is an
       * element, the current or next start element starts this object and any
       * intervening reader events are ignorable If this object is not an
       * element, it is a complex type and the reader is at the event just after
       * the outer start element Postcondition: If this object is an element,
       * the reader is positioned at its end element If this object is a complex
       * type, the reader is positioned at the end element of its outer element
       */
      public static TxdoBuf parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
        TxdoBuf object = new TxdoBuf();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";
        try {

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (fullTypeName != null) {
              java.lang.String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1) {
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
              }
              nsPrefix = nsPrefix == null ? "" : nsPrefix;

              java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

              if (!"TxdoBuf".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (TxdoBuf) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }

            }

          }

          // Note all attributes that were handled. Used to differ
          // normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          java.util.ArrayList list1 = new java.util.ArrayList();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "buffer").equals(reader.getName())) {

            // Process the array and step past its final element's
            // end.

            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              list1.add(null);

              reader.next();
            } else {
              list1.add(reader.getElementText());
            }
            // loop until we find a start element that is not part
            // of this array
            boolean loopDone1 = false;
            while (!loopDone1) {
              // Ensure we are at the EndElement
              while (!reader.isEndElement()) {
                reader.next();
              }
              // Step out of this element
              reader.next();
              // Step to next element event.
              while (!reader.isStartElement() && !reader.isEndElement())
                reader.next();
              if (reader.isEndElement()) {
                // two continuous end elements means we are
                // exiting the xml structure
                loopDone1 = true;
              } else {
                if (new javax.xml.namespace.QName("http://ws.esb.sitech.com/xsd", "buffer").equals(reader.getName())) {

                  nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
                  if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
                    list1.add(null);

                    reader.next();
                  } else {
                    list1.add(reader.getElementText());
                  }
                } else {
                  loopDone1 = true;
                }
              }
            }
            // call the converter utility to convert and set the
            // array

            object.setBuffer((java.lang.String[]) list1.toArray(new java.lang.String[list1.size()]));

          } // End of if for expected property start element

          else {

          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement())
            // A start element we are not expecting indicates a
            // trailing invalid property
            throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }

    }// end of factory class

    public void serialize(QName arg0, XMLStreamWriter arg1) throws XMLStreamException, ADBException {
      // TODO Auto-generated method stub

    }

    public void serialize(QName arg0, XMLStreamWriter arg1, boolean arg2) throws XMLStreamException, ADBException {
      // TODO Auto-generated method stub

    }

  }

  private org.apache.axiom.om.OMElement toOM(com.sitech.esb.txdows.SLoginWebQryWSStub.CallService param, boolean optimizeContent)
      throws org.apache.axis2.AxisFault {

    try {
      return param.getOMElement(com.sitech.esb.txdows.SLoginWebQryWSStub.CallService.MY_QNAME, org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }

  }

  private org.apache.axiom.om.OMElement toOM(com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse param, boolean optimizeContent)
      throws org.apache.axis2.AxisFault {

    try {
      return param.getOMElement(com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse.MY_QNAME, org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }

  }

  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.sitech.esb.txdows.SLoginWebQryWSStub.CallService param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {

    try {

      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild(param.getOMElement(com.sitech.esb.txdows.SLoginWebQryWSStub.CallService.MY_QNAME, factory));
      return emptyEnvelope;
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }

  }

  /* methods to provide back word compatibility */

  /**
   * get the default envelope
   */
  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory) {
    return factory.getDefaultEnvelope();
  }

  private java.lang.Object fromOM(org.apache.axiom.om.OMElement param, java.lang.Class type, java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault {

    try {

      if (com.sitech.esb.txdows.SLoginWebQryWSStub.CallService.class.equals(type)) {

        return com.sitech.esb.txdows.SLoginWebQryWSStub.CallService.Factory.parse(param.getXMLStreamReaderWithoutCaching());

      }

      if (com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse.class.equals(type)) {

        return com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

      }

    } catch (java.lang.Exception e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
    return null;
  }

}

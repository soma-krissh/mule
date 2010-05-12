/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package net.webservicex;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.8
 * Tue May 11 18:53:01 ART 2010
 * Generated source version: 2.2.8
 * 
 */
 
@WebService(targetNamespace = "http://www.webserviceX.NET/", name = "StockQuoteSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface StockQuoteSoap {

    @ResponseWrapper(localName = "GetQuoteResponse", targetNamespace = "http://www.webserviceX.NET/", className = "net.webservicex.GetQuoteResponse")
    @RequestWrapper(localName = "GetQuote", targetNamespace = "http://www.webserviceX.NET/", className = "net.webservicex.GetQuote")
    @WebResult(name = "GetQuoteResult", targetNamespace = "http://www.webserviceX.NET/")
    @WebMethod(operationName = "GetQuote", action = "http://www.webserviceX.NET/GetQuote")
    public java.lang.String getQuote(
        @WebParam(name = "symbol", targetNamespace = "http://www.webserviceX.NET/")
        java.lang.String symbol
    );
}

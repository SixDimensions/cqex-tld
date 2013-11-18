/*
 * Copyright 2013 - Six Dimensions 
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.taglib.ext;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import tldgen.Tag;
import tldgen.TagAttribute;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.wcm.tags.DefineObjectsTag;

/**
 * Tag for XSS safe encoding arbitrary text for HTML, XML & JS.
 * 
 * @author dklco
 */
@Tag
public class XSSEncodeTag extends TagSupport {

	/**
	 * The different types which can be encoded
	 */
	public static enum ENCODING_TYPE {
		HTML, HTML_ATTR, JS, XML, XML_ATTR
	}

	/**
	 * The Serialization UID
	 */
	private static final long serialVersionUID = -1849209667801571721L;;

	/**
	 * The current encoding type
	 */
	private ENCODING_TYPE type = ENCODING_TYPE.HTML;

	/**
	 * The value to encode
	 */
	private String value;

	/**
	 * The variable in which to save the value
	 */
	private String var;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		final XSSAPI xssAPI = XSSAPI.class.cast(this.pageContext
				.getAttribute(DefineObjectsTag.DEFAULT_XSSAPI_NAME));
		String encodedValue = "";
		switch (this.type) {
		case HTML_ATTR:
			encodedValue = xssAPI.encodeForHTMLAttr(this.value);
			break;
		case JS:
			encodedValue = xssAPI.encodeForJSString(this.value);
			break;
		case XML:
			encodedValue = xssAPI.encodeForXML(this.value);
			break;
		case XML_ATTR:
			encodedValue = xssAPI.encodeForXMLAttr(this.value);
			break;
		default:
			encodedValue = xssAPI.encodeForHTML(this.value);
			break;
		}
		if (this.var == null) {
			try {
				this.pageContext.getOut().write(encodedValue);
			} catch (final IOException e) {
				throw new JspException(
						"Exception writing encoded value to body content", e);
			}
		} else {
			this.pageContext.setAttribute(this.var, encodedValue);
		}
		return SKIP_BODY;
	}

	/**
	 * @return the type
	 */
	public ENCODING_TYPE getType() {
		return this.type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @return the var
	 */
	public String getVar() {
		return this.var;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	@TagAttribute(required = true)
	public void setType(final String type) {
		this.type = ENCODING_TYPE.valueOf(type.toUpperCase());
	}

	/**
	 * @param value
	 *            the value to set
	 */
	@TagAttribute(required = true)
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * @param var
	 *            the var to set
	 */
	@TagAttribute(required = true)
	public void setVar(final String var) {
		this.var = var;
	}
}

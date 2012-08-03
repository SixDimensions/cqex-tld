/*
 * Copyright 2012 - Six Dimensions
 */
package com.sixdimensions.wcm.cq.cqex.tags;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tldgen.annotations.Attribute;
import org.tldgen.annotations.BodyContent;
import org.tldgen.annotations.Tag;

/**
 * Retrieves the resource at the specified path and makes it available as a
 * Sling Resource.
 * 
 * @author dklco
 */
@Tag(bodyContent = BodyContent.EMPTY, example = "&lt;cqex:getResource var=\"resource\" path=\"/content/mysite\" />")
public class GetResourceTag extends AttributeSettingTag {
	private static final long serialVersionUID = 5861756752614447760L;
	private static final Logger log = LoggerFactory
			.getLogger(GetResourceTag.class);

	/**
	 * The absolute path of the resource to retrieve
	 */
	@Attribute(required = true)
	private String path;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() {
		log.trace("doEndTag");

		log.debug("Retrieving resource at path");
		SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) this.pageContext
				.getRequest();
		Resource resource = slingRequest.getResourceResolver().getResource(
				this.path);
		log.debug("Retrieved resource: "
				+ (resource != null ? resource.getPath() : "null"));
		this.setAttribute(this.var, resource);
		return javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
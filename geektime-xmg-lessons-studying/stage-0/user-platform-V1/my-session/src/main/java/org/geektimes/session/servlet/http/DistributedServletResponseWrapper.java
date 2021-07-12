package org.geektimes.session.servlet.http;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * The distributed {@link HttpServletResponse} implementation based on {@link HttpServletResponseWrapper}
 */
public class DistributedServletResponseWrapper extends HttpServletResponseWrapper {

    /**
     * Constructs a response object wrapping the given response.
     *
     * @param response HttpServletResponse
     * @throws IllegalArgumentException if the response is null
     */
    public DistributedServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

}

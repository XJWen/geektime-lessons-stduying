package org.geektimes.session.servlet.http;

import org.geektimes.session.SessionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * The distributed {@link HttpServletRequest} implementation based on {@link HttpServletRequestWrapper}
 *
 */
public class DistributedServletRequestWrapper extends HttpServletRequestWrapper {

    private final HttpServletRequest request;

    private final SessionRepository sessionRepository;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request           {@link HttpServletRequest}
     * @param sessionRepository {@link SessionRepository}
     * @throws IllegalArgumentException if the request is null
     */
    public DistributedServletRequestWrapper(HttpServletRequest request, SessionRepository sessionRepository) {
        super(request);
        this.request = request;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public HttpSession getSession(boolean create) {

        HttpSession session = super.getSession(create);

        if (session != null) {
            return new DistributedHttpSession(request, session, sessionRepository);
        } else {
            // invalidate session
            return session;
        }
    }

    /**
     * The default behavior of this method is to return getSession()
     * on the wrapped request object.
     */
    @Override
    public HttpSession getSession() {
        HttpSession session = getSession(false);
        if (session == null) {
            session = getSession(true);
        }
        return session;
    }

}

package org.geektimes.session.servlet.http;

import org.geektimes.session.SessionInfo;
import org.geektimes.session.SessionRepository;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

import static java.util.Collections.enumeration;

/**
 * The Distributed {@link HttpSession}
 *
 * */
public class DistributedHttpSession implements HttpSession {

    /**
     * The attribute name of {@link DistributedHttpSession} instance
     */
    public static final String ATTRIBUTE_NAME = "_distributedHttpSession";

    private final HttpServletRequest request;

    private final SessionRepository sessionRepository;

    private final HttpSession source;

    private final SessionInfo sessionInfo;

    public DistributedHttpSession(HttpServletRequest request, HttpSession source, SessionRepository sessionRepository) {
        this.request = request;
        this.source = source;
        this.sessionRepository = sessionRepository;
        this.sessionInfo = resolveSessionInfo();
        // set self into Request Context
        request.setAttribute(ATTRIBUTE_NAME, this);
    }

    private SessionInfo resolveSessionInfo() {
        String requestSessionId = request.getRequestedSessionId();
        SessionInfo sessionInfo = null;
        if (requestSessionId != null) { // the "requestSessionId" that the server generated was stored by HTTP client
            // Try to get the SessionInfo from the repository,
            // If the "sessionInfo" is present , it indicates that the session was created by another server
            // in the distributed cluster, or the session had been expired in the server
            sessionInfo = sessionRepository.getSessionInfo(requestSessionId);
        }
        if (sessionInfo == null) { // Maybe the first time access to the server when the "requestSessionId" is absent
            sessionInfo = new SessionInfo(source);
        }
        return sessionInfo;
    }

    public static DistributedHttpSession get(HttpServletRequest request) {
        return (DistributedHttpSession) request.getAttribute(ATTRIBUTE_NAME);
    }

    /**
     * Get the {@link SessionInfo}
     *
     * @return non-null {@link #sessionInfo}
     */
    public SessionInfo getSessionInfo() {
        return this.sessionInfo;
    }

    public void commitSessionInfo() {
        sessionRepository.saveSessionInfo(getSessionInfo());
    }

    @Override
    public long getCreationTime() {
        return sessionInfo.getCreationTime();
    }

    @Override
    public String getId() {
        return sessionInfo.getId();
    }

    @Override
    public long getLastAccessedTime() {
        return sessionInfo.getLastAccessedTime();
    }

    @Override
    public ServletContext getServletContext() {
        return source.getServletContext();
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        sessionInfo.setMaxInactiveInterval(interval);
    }

    @Override
    public int getMaxInactiveInterval() {
        return sessionInfo.getMaxInactiveInterval();
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return source.getSessionContext();
    }

    @Override
    public Object getAttribute(String name) {
        // try to find the value in local session
        Object value = source.getAttribute(name);
        if (value == null) { // If not found, try to find it in the repository
            value = sessionRepository.getAttribute(getId(), name);
            // restore the value into local session if found
            if (value != null) {
                source.setAttribute(name, value);
            }
        }
        return value;
    }

    @Override
    @Deprecated
    public Object getValue(String name) {
        return getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return enumeration(sessionRepository.getAttributeNames(getId()));
    }

    @Override
    @Deprecated
    public String[] getValueNames() {
        return source.getValueNames();
    }

    @Override
    public void setAttribute(String name, Object value) {
        source.setAttribute(name, value);
        sessionRepository.setAttribute(getId(), name, value);
    }

    @Override
    @Deprecated
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        source.removeAttribute(name);
        sessionRepository.removeAttribute(getId(), name);
    }

    @Override
    @Deprecated
    public void removeValue(String name) {
        removeAttribute(name);
    }

    @Override
    public void invalidate() {
        source.invalidate();
        invalidateSessionInfoCache();
        invalidateAttributesCache();
    }

    private void invalidateSessionInfoCache() {
    }

    private void invalidateAttributesCache() {
    }

    @Override
    public boolean isNew() {
        return source.isNew();
    }

}

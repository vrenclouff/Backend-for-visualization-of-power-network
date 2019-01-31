package cz.zcu.kiv.vps.idm.model;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * Created by Lukas Cerny.
 */
public class LoggedUser {

    private User user;

    private Session session;

    private Date expiresDate;

    public LoggedUser(User user, Session session, Date expiresDate) {
        this.user = user;
        this.session = session;
        this.expiresDate = expiresDate;
    }

    public User getUser() {
        return user;
    }

    public Session getSession() {
        return session;
    }

    public Date getExpiresDate() {
        return expiresDate;
    }

    public boolean expiredToken() {
        return getExpiresDate().before(new Date());
    }

    public void extendToken() {
        this.expiresDate = DateUtils.addMinutes(new Date(), 20);
    }
}

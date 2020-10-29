package dbService.dao;

import dbService.data.UserData;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UserDAO {

    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public UserData getUser(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UserData.class);
        return (UserData) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

    public void insertUser(String login, String password, String email) throws HibernateException {
        session.save(new UserData(login, password, email));
    }

}

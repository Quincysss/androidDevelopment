package new_Assignment;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import new_Assignment.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-14T01:19:55")
@StaticMetamodel(Credential.class)
public class Credential_ { 

    public static volatile SingularAttribute<Credential, String> password;
    public static volatile SingularAttribute<Credential, Integer> credentialId;
    public static volatile SingularAttribute<Credential, Users> userid;
    public static volatile SingularAttribute<Credential, Date> signdate;
    public static volatile SingularAttribute<Credential, String> username;

}
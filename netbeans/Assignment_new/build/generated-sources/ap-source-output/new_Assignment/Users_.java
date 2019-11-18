package new_Assignment;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import new_Assignment.Consumption;
import new_Assignment.Credential;
import new_Assignment.Report;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-14T01:19:56")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> firstname;
    public static volatile SingularAttribute<Users, String> address;
    public static volatile SingularAttribute<Users, String> gender;
    public static volatile CollectionAttribute<Users, Consumption> consumptionCollection;
    public static volatile SingularAttribute<Users, String> postcode;
    public static volatile SingularAttribute<Users, Double> weight;
    public static volatile SingularAttribute<Users, Integer> userid;
    public static volatile SingularAttribute<Users, Double> steps;
    public static volatile SingularAttribute<Users, String> eMail;
    public static volatile CollectionAttribute<Users, Report> reportCollection;
    public static volatile SingularAttribute<Users, String> surname;
    public static volatile SingularAttribute<Users, Date> dob;
    public static volatile CollectionAttribute<Users, Credential> credentialCollection;
    public static volatile SingularAttribute<Users, Integer> actlevel;
    public static volatile SingularAttribute<Users, Double> height;

}
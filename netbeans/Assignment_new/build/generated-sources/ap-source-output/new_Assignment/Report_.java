package new_Assignment;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import new_Assignment.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-14T01:19:56")
@StaticMetamodel(Report.class)
public class Report_ { 

    public static volatile SingularAttribute<Report, Double> burnedCalories;
    public static volatile SingularAttribute<Report, Integer> reportId;
    public static volatile SingularAttribute<Report, Date> reportDate;
    public static volatile SingularAttribute<Report, Integer> stepsPerMile;
    public static volatile SingularAttribute<Report, Users> userid;
    public static volatile SingularAttribute<Report, Double> caloriesConsumed;
    public static volatile SingularAttribute<Report, Double> caloriesGoal;

}
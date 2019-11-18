package new_Assignment;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import new_Assignment.Food;
import new_Assignment.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-14T01:19:56")
@StaticMetamodel(Consumption.class)
public class Consumption_ { 

    public static volatile SingularAttribute<Consumption, Date> conDate;
    public static volatile SingularAttribute<Consumption, Integer> consumptionId;
    public static volatile SingularAttribute<Consumption, Double> quantityFood;
    public static volatile SingularAttribute<Consumption, Food> foodid;
    public static volatile SingularAttribute<Consumption, Users> userid;

}
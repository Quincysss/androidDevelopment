package new_Assignment;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import new_Assignment.Consumption;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-14T01:19:56")
@StaticMetamodel(Food.class)
public class Food_ { 

    public static volatile SingularAttribute<Food, Double> totalServing;
    public static volatile SingularAttribute<Food, String> foodname;
    public static volatile SingularAttribute<Food, Double> totalCalorie;
    public static volatile CollectionAttribute<Food, Consumption> consumptionCollection;
    public static volatile SingularAttribute<Food, Integer> foodid;
    public static volatile SingularAttribute<Food, Double> fat;
    public static volatile SingularAttribute<Food, String> foodCategory;
    public static volatile SingularAttribute<Food, String> servingunit;

}
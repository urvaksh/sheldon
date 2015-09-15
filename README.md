[![Build Status](https://travis-ci.org/urvaksh/sheldon.svg?branch=master)](https://travis-ci.org/urvaksh/sheldon)
# Sheldon
## A framework that detects changes and complains about them

### What is Sheldon?
Sheldon is basically a dirty checking framework that deep compares two objects and detects any changes in the entire object graph.
```Java
//Just mark the Entity as Auditable
@Auditable(name = "Missy Cooper", comparatorFields = @AuditComparator({ "dnaSeq" }))
public class MissyCooper {

	//Used as the identifier of this record, hence added in @AuditComparator
	private DnaSequence dnaSeq;

    	//This field is dirty checked automatically
	@AuditField(fieldName = "Last Name", groups = "matrimony")
	private String lastName;
	
	//Unlikely to change hence not Audited
	private String firstName;

    	//It works on component objects.
	//Because Missy keeps tabs on Sheldon and tattles to her mom
	//And SheldonCooper is a singleton class with a one of a kind IQ!
	@AuditField(fieldName = "Sibling", comparatorFields = @AuditComparator("iq"), groups = "exceptional")
	private SheldonCooper twin;

	//We don't care about him, so not annotation
	private Person brother;

    	//And on Iterables
	@AuditableList(groups = {"standard","mischievous"}, comparatorFields = @AuditComparator("id"))
	private List<Child> children = new ArrayList<Child>();

}
```
Now dirty checking is as simple as
```Java
AuditChecker checker = new AuditChecker<MissyCooper>();
//Detects all the changes
List<AuditChangeEntry> changes = checker.checkObjects(oldMissy, newMissy);
//If you only want to detect changes in the group "exceptional"
List<AuditChangeEntry> sheldonyChanges = checker.checkObjects(oldMissy, newMissy, "exceptional");
```

### Why is it called Sheldon?
For those of you who live in a parallel universe where you have not heard of [The Big Bang Theory](http://http://the-big-bang-theory.com/), Sheldon is one of the characters in this popular sit-com. For those of you who know about The Big Bang Theory, you know Sheldon is the best thing that happened to the world!

Most prominent of Sheldon's many traits is that he does not like any sort of change and complains even about even the littlest things.
>Sheldon: Excuse me. This is not about protecting my friend. I’m a big fan of homeostasis. Do you know what that is?   
>Penny: Of course not.   
>Sheldon: Homeostasis refers to a system’s ability to regulate its internal environment and maintain a constant condition of properties like temperature or pH.     
>Penny: Worst bedtime story ever.   
>Sheldon: My point is I don’t like when things change. So, regardless of your feelings, I would like you to continue dating Leonard. And also, while we’re on the subject, you recently changed your shampoo. I’m not comfortable with the new scent. Please stop this madness and go back to green apple.

So while Sheldon solves the mysteries of the Universe, I thought a small Java framework with traits similar to him is a apt tribute to someone who brings us nerds so much fun!   

### API Overview   
The basic API to use Sheldon is covered by 4 Annotations and a class that performs the dirty checking   

| Annotation/Class | Description                                                                                              |
|------------------|----------------------------------------------------------------------------------------------------------|
| @Auditable       | Annotation must be placed on all classes that the framework is expected to dirty check                   |
| @AuditComparator | Is a special annotation that can be used if developers do not wish to provide a comparator for the class |
| @AuditField      | Annotation must be placed on each field that needs to be dirty checked.                                  |
| @AuditableList   | Annotation must be place on every List that needs to be dirty checked                                    |
| AuditChecker     | The entry-point for the framework                                                                        |

#### @Auditable   

| Property | Type | Description |
| -------- | ---- | ----------- |
| name | String | The name of the Object as you want it to appear in the Audit Path |
| comparator | Class<? extends Comparator<?>> | Defines a comparator for the class* |
| comparatorFields | @AuditComparator  | Defines a the set of fields in the class that should be used to create a dynamic comparator for the class* |
```Java
@Auditable(name = "Missy Cooper", comparatorFields = @AuditComparator({ "dnaSeq" }))
public class MissyCooper {
	...
}
```
\* You need to define one of the two comparators. The framework uses comparators over a any sort of equality so that when there are multiple records in a list, sorting and comparing ordered pairs is far more efficient O(nlogn) than comparing all the reords to each other O(n2). 

#### @AuditComparator   

| Property | Type | Description |
| -------- | ---- | ----------- |
| value | String[] | List of field names that constitute the class's identity and need to be a part of the Dynamically created comparator |
```Java
@AuditComparator("dnaSeq")
```

#### @AuditField   

| Property | Type | Description |
| -------- | ---- | ----------- |
| fieldName | String | The name of the field as you want it to appear in the Audit Path |
| comparator | Class<? extends Comparator<?>> | Defines a comparator for the class* |
| comparatorFields | @AuditComparator  | Defines a the set of fields in the class that should be used to create a dynamic comparator for the class* |
| groups | String[] | Defines the groups for this field. The framework can allow dirty checking for certain groups, if the group is in this list, it will be reported, otherwise it will be skipped |
\* You need to define one of the two comparators. 
```Java
@AuditField(fieldName = "Sibling", comparatorFields = @AuditComparator("iq"), groups = "exceptional")
private SheldonCooper twin;
```

#### @AuditableList    

| Property | Type | Description |
| -------- | ---- | ----------- |
| comparator | Class<? extends Comparator<?>> | Defines a comparator for the class* |
| comparatorFields | @AuditComparator  | Defines a the set of fields in the class that should be used to create a dynamic comparator for the class* |
| listConverter | Class<? extends ListConverter> | The class that defines a ListConverter - A ListConverter must return a List so if a Map hs to be dirty checked, the Map could be converted into a list (key order must be maintained) and returned |
| groups | String[] | Defines the groups for this field. The framework can allow dirty checking for certain groups, if the group is in this list, it will be reported, otherwise it will be skipped |
\* You need to define one of the two comparators. 
```Java
@AuditableList(groups = {"standard","mischievous"}, comparatorFields = @AuditComparator("id"))
private List<Child> children = new ArrayList<Child>();
```

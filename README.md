# Sheldon
### A framework that detects changes and complains about them

#### What is Sheldon?
Sheldon is basically a dirty checking framework that deep compares two objects and detects any changes in the entire object graph.
```Java
//Just mark the Entity as Auditable
@Auditable(name = "Missy Cooper", comparatorFields = @AuditComparator({ "firstName", "lastName" }))
public class MissyCooper {

	private String firstName;

    //This field is dirty checked automatically
	@AuditField(fieldName = "A Sample Field", groups = "standard")
	private String lastName;

    //It works on component objects.
	@AuditField(fieldName = "sibling", groups = "exceptional")
	private SheldonCooper sibling;

    //And on Iterables
	@AuditableList(groups = "standard", comparatorFields = @AuditComparator("id"))
	private List<Child> children = new ArrayList<Child>();

}
```

#### Why is it called Sheldon?
For those of you who live in a parallell glaxy where you have not heard of [The Big Bang Theory](http://http://the-big-bang-theory.com/), Sheldon is one of the characters in this popular sit-com. For those of you who know about The Big Bang Theory, you know Sheldon is the best thing that happened to the world!

Most promiment of Sheldon's many traits is that he does not like any sort of change and complains even about even the littlest things.
>Sheldon: Excuse me. This is not about protecting my friend. I’m a big fan of homeostasis. Do you know what that is?   
>Penny: Of course not.   
>Sheldon: Homeostasis refers to a system’s ability to regulate its internal environment and maintain a constant condition of properties like temperature or pH.     
>Penny: Worst bedtime story ever.   
>Sheldon: My point is I don’t like when things change. So, regardless of your feelings, I would like you to continue dating Leonard. And also, while we’re on the subject, you recently changed your shampoo. I’m not comfortable with the new scent. Please stop this madness and go back to green apple.

So while Sheldon solves the mysteries of the Universe, I thought a small Java framework with traits similar to him is a apt tribute to someone who brings us nerds so much fun!

##API Overview
The basic API to use Sheldon is covered by 4 Annotations and a class that performs the dirty checking   
| Annotation/Class | Description                                                                                              |
|------------------|----------------------------------------------------------------------------------------------------------|
| @Auditable       | Annotation must be placed on all classes that the framework is expected to dirty check                   |
| @AuditComparator | Is a special annotation that can be used if developers do not wish to provide a comparator for the class |
| @AuditField      | Annotation must be placed on each field that needs to be dirty checked.                                  |
| @AuditableList   | Annotation must be place on every List that needs to be dirty checked                                    |
| AuditChecker     | The entry-point for the framework                                                                        |

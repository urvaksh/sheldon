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

### More Information
For more information please look at the [wiki](https://github.com/urvaksh/sheldon/wiki). A good starting point is the [API Overview](https://github.com/urvaksh/sheldon/wiki/API-Overview).

You can also look at the [test cases](https://github.com/urvaksh/sheldon/tree/master/src/test/java/com/codeaspect/sheldon/intg) to see how the API is used and the dirty checking results are used. 

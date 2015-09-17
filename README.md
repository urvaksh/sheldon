[![Build Status](https://travis-ci.org/urvaksh/sheldon.svg?branch=master)](https://travis-ci.org/urvaksh/sheldon)
# Sheldon
## A framework that detects changes and complains about them

### What is Sheldon?
Sheldon is a dirty checking framework that deep compares two object graphs, detects any changes a reports (complains about) them.

A simple example:
```Java
//Just mark the Entity as Auditable
@Auditable(name = "Missy Cooper", comparatorFields = @AuditComparator({ "dnaSeq" }))
public class MissyCooper {

	//Used as the identifier of this record, hence added in @AuditComparator on the class
	private DnaSequence dnaSeq;

  //Adding @AuditField means the field is dirty checked
	@AuditField(fieldName = "Last Name", groups = "matrimony")
	private String lastName;

	//Unlikely to change hence not Audited
	private String firstName;

  //It works on component objects.
	@AuditField(fieldName = "Sibling", comparatorFields = @AuditComparator("iq"), groups = "exceptional")
	private SheldonCooper twin;
	//Because Missy keeps tabs on Sheldon and tattles to her mom
	//This could have been Person, but SheldonCooper is a singleton class with a one of a kind IQ and we are not really sure in and object of SheldonCooper passes the instanceof Person test!

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

//If you only want to detect changes in the group "mischievous" and "exceptional"
List<AuditChangeEntry> mischievousChanges = checker.checkObjects(oldMissy, newMissy, "mischievous", "exceptional");
```
### More Information
For more information please head to the [wiki](https://github.com/urvaksh/sheldon/wiki). A good starting point is the [API Overview](https://github.com/urvaksh/sheldon/wiki/API-Overview). If you are curious as to [why the project is called Sheldon](https://github.com/urvaksh/sheldon/wiki/Why-is-it-called-Sheldon%3F), there's a page dedicated to just that.

You can also look at the [test cases](https://github.com/urvaksh/sheldon/tree/master/src/test/java/com/codeaspect/sheldon/intg) to see how the API is used and the dirty checking results are used.

## Module-2 : Using-Immutable-Objects-and-Value-Objects : [here](https://github.com/HeithemLejmi/Making-Your-Java-Code-More-Object-oriented/blob/main/docs/module-2__using-immutable-objects-and-value-objects-slides.pdf)

### Requirements:

  
### New rules learned:

#### 1. Use **Comparable Interface** and its method **compareTo()** :
- **Definition**: As the name suggests, **Comparable** is an interface defining a strategy of comparing an object with other
 objects of the same type. This is called the class's “natural ordering”.
For variables of primitive types, the comparison could be made by the operators like : ==, >, <... But, to compare Objects
, we need them to implement the **Comparable** interface, and then use the **objectToCompare.compareTo
(objectToBeComparedTo)**

- **Example**:
Let's take an example of a football team – where we want to line up the players by their rankings.

    - We'll start by creating a simple Player class:

    ```java
    public class Player {
        private int ranking;
        private String name;
        private int age;
        
        // constructor, getters, setters  
    }
    ````
    - Next, let's create a PlayerSorter class to create our collection and make an attempt to sort it using Collections.sort:
    
    ```java
    public static void main(String[] args) {
        List<Player> footballTeam = new ArrayList<>();
        Player player1 = new Player(59, "John", 20);
        Player player2 = new Player(67, "Roger", 22);
        Player player3 = new Player(45, "Steven", 24);
        footballTeam.add(player1);
        footballTeam.add(player2);
        footballTeam.add(player3);
     
        System.out.println("Before Sorting : " + footballTeam);
        Collections.sort(footballTeam);
        System.out.println("After Sorting : " + footballTeam);
    }
    ````
    - Here, as expected, this results in a compile-time error:
    ````
    The method sort(List<T>) in the type Collections 
      is not applicable for the arguments (ArrayList<Player>)
    Let's understand what we did wrong here.
    ````

    - Accordingly, in order to be able to sort this custom Objects of Player Class:
    we must define our Player object as comparable by implementing the Comparable interface:
````java
public class Player implements Comparable<Player> {
 
    // same as before
 
    @Override
    public int compareTo(Player otherPlayer) {
        return Integer.compare(getRanking(), otherPlayer.getRanking());
    }
 
}
````

- The sorting order is decided by the return value of the compareTo() method. 
The method returns a number indicating whether the object being compared is less than, equal to, or greater than the object being passed as an argument.
The Integer.compare(x, y) returns :
    - -1 if x is less than y, 
    - 0 if they're equal, 
    - and returns 1 otherwise.

Finally, when we run our PlayerSorter now, we can see our Players sorted by their ranking:
````
Before Sorting : [John, Roger, Steven]
After Sorting : [Steven, John, Roger]
````
Now that we have a clear understanding of natural ordering with Comparable, let's see how we can use other types of ordering, in a more flexible manner than directly implementing an interface.

#### 2. Alias: Causing a Bug that Comes from a Mutable State
- Alias is when two references are referring to the same objects => this could cause bugs !!
- To avoid that :
  - 1) never operates changes directly on the method's arguments : create new objects/variables inside the method and use  
    these arguments only as parameters.
  - 2) when you have a method that does updates/modifications on the object: avoid to make this method as **void** and try 
    always to return the outcome (the updated object) as the return of this method, on order to allow the rest of the 
    program to be always informed of these changes.
  - 3) when you have a method that have a return : never ignore that retrun => declare it and use it !!!
```
// wrong
this.reserve(cost);

// good
Money finalCost = this.reserve(cost);
````
    
- Example with bugs:


```java
public class Demo {

  private boolean isHappyHour;

  // Bug 2: Method that makes update on the Object Money but it is void
  private void reserve(Money cost) {
    if (isHappyHour) {
      // Bug 1 : operating directly on the argument and expecting that the outside scope to be informed
      cost.scale(.5);
    }
    System.out.println("Reserving an item costing " + cost);
  }

  private void buy(Money wallet, Money cost) {
    boolean enoughMoney = wallet.compareTo(cost) >= 0;
    // Bug 3 : this method is void and it operates on the argument "cost"
    this.reserve(cost);

    if (finalEnough)
      // The "cost" here is the cost parameter on the signature of "buy()" method not the one modified by "reserve()"
      System.out.println("You will pay " + cost + " with your " + wallet);
    else
      System.out.println("You cannot pay " + cost + " with your " + wallet);
  }
}
```    

- The same example corrected :

```java
public class Demo {

  private boolean isHappyHour;

  // Bug 2 corrected: by making this method returning its result
  private Money reserve(Money cost) {
    // Bug 1 corrected: create a new Object "Money" inside the method and store the changes in it and return the result 
    // in th "return" statement
    Money finalCost = this.isHappyHour ? cost.scale(.5) : cost;
    System.out.println("Reserving an item costing " + finalCost);
    // return the result of this method, so that the rest of the program will be informed
    return finalCost;
  }

  private void buy(Money wallet, Money cost) {
    boolean enoughMoney = wallet.compareTo(cost) >= 0;
    // Bug 3 corrected: store the retrn of the "reserve()" method and use it on the follwing line to do the comparaison
    Money finalCost = this.reserve(cost);
    boolean finalEnough = wallet.compareTo(finalCost) >= 0;

    if (finalEnough && !enoughMoney)
      System.out.println("Only this time, you will pay " + finalCost + " with your " + wallet);
    else if (finalEnough)
      System.out.println("You will pay " + finalCost + " with your " + wallet);
    else
      System.out.println("You cannot pay " + finalCost + " with your " + wallet);
  }
}
```    

#### 3. Reminders: State vs Behavior of JAVA Object:
- A Java object is a combination of data and procedures working on the available data. An object has a state and behavior.

- The state of an object is stored in fields (variables), while methods (functions) display the object's behavior. 
- Objects are created from templates known as classes. In Java, an object is created using the keyword "new".

- Java objects are characterized by three features:
  - Identity : The identity is a characteristic used to uniquely identify that object
  - State : A Java object’s states are stored in fields that represent the individual characteristics of that object. 
  - Behavior : The object’s behavior is exposed through methods that operate its internal state. 
For example, a Java object representing a cat has: a state includes its color, size, gender, and age, while its behavior
 is sleeping, purring, meowing for food, or running..
Another example, in a first-person shooter video game, a pistol with an eight-bullets clip has nine states in total: 
one for each bullet (e.g. 8 bullets, 7 bullets, 5 bullets, etc.), plus another one when it’s empty (0 bullets).
The “shooting” behavior will change the state of the pistol from “8 bullets'' to “7 bullets” and so forth every time the
 player shoots with the gun. The “reloading” behavior will bring back the pistol into the original “8 bullets'' state.

#### 4. Use **Value Objects** instead of primitive values:
##### 4.1. Context:
For example, if we were talking about a Person class, we would probably see fields like firstName, lastName, or address. 
````java
public class Person {
  /** State: represented by this values: **/
  String firstName;
  String lastName;
  String address;
}
````
A person has a clear, global identity. If we were talking about a String values like “Heithem” as firstName and "Lejmi" as
 lastName, we don’t have a global scope – they are just values.

Values like the ones mentioned above can have certain logic associated with them e.g.: validation, transformations or
 calculus. 
As we’re using an OO language, it makes all the sense in the world to use its powers and combine the value and the logic
 together in an object: This is the **Value Object Pattern**.

##### 4.2. Definition of Value Objects Pattern:
- The Value Objects pattern does just that – it transforms our values into objects :

````java
/** Entity **/
public class Person {
  PersinId id; // global identity
  
  FirstName firstName;
  LastName lastName;
  Address address;
}

/** Value Objects **/
public class PersonId {
  Long value;
}

public class FirstName {
  String value;
}

public class LastName {
  String value;
}

public class Address {
  String street;
  String streetNo;
  String city;
  String zipCode;
}
````
- **Values vs Entities:** In the above block of code, we have an entity `Person` that wraps value objects as `Address
`, `FirstName`, `LastName` etc
    - Entity:
        - an entity has a history.
        - an entity usually models a physical entity.
        - we always need to track an entity over time (the key to keep track of an entity, is by using an id for this entity: persistent id)
        - Persistent Id: - allows us to reconstruct the same entity later.
                         - allows us to track history of a real physical object
    - Value Object:
        - are simple values that be thrown away and reconstructed at will
        - we don't track history on value objects (so they don't need id)
        - they are immutable :
            - all operations in a value object only return a new object
            - they only consist of other immutable components
            For example Money is a value object -> it is immutable
            and since it consist of two fields : amount and currency, both this attributes should be immutables as well.
            - Since a value object has no identity, so its whole content is its identity : (The identity of Money Object is its content: its currency && its amount) => that's why, we use equals() & hashCode() methods to compare two value objects.
##### 4.3. Good Practices while using Value Objects Pattern:
- As the value objects (like FirstName, Address...) have no identity, we compare them together by simply comparing all
the values they contain: (by using the methods: **equals** and **hashcode**)
###### a. equals:  
- Avoid using "instanceOf" in equals() method: because "instanceOf" doesn't support symmetric rule (a = b then b = a):
    - BaseType instanceOf BaseType = true
    - DerivedType instanceOf BaseType = true
    - BaseType instanceOf DerivedType = false
- Instead of "instanceOf" use : the null check "!= null" with the type check (to compare it with the cxurrent type
) "getClass() == this.getClass"
````java
// before
public boolean equals(Object obj){
return obj instanceOf Money && ...;
}

// after
public boolean equals(Object obj){
return obj != null && obj.getClass() == b.getClass() && ...;
}
````
N.B. this implies that a subclass should not be compares/equals to its parent class (that's why we put getClass method)
For exp: Euro is compares to Euro, Money is compare to mMoney, Euro cannot be compared to Money.

Rule !! two objects of different types (even a parent class and a sub class) cannot be equals !!!!
````java
public class Address {
  String street;
  String streetNo;
  String city;
  String zipCode;
  @Override
  public boolean equals(Object o){
  return o != null && o.getClass() == this.getClass() && 
         Objects.equals(this.street, o.getStreet()) && 
         Objects.equals(this.streetNo, o.getStreetNo()) &&
         Objects.equals(this.city, o.getCity()) &&
         Objects.equals(this.zipCode, o.getZipCode());
  }
  @Override
  public int hashCode(Object o){
  return Objects.hash(this.street, this.streetNo, this.city, this.zipCode);
  }
}
````
###### b. hashcode:
- Reminders: 
  - two objects are equals => therefore: they have the same hashcode.
  - two objects having the same hashcode, are not necessarily equals.
    
- When creating a Value Object, remember to define its hashcode method.
- Example: 
  - in line 2: the element <key, value> put in the hashmap, with:
    - the key is the hashcode of the `new Money(new BigDecimal(42), new Currency("USD"))`
    - the value is the string "Code of life".
  - in line 3:  we created a Money object `equals` to the one that we put in the key of the elemnt added previously in 
    the HasMap.
  - in line 4: we are trying to find the element that we pushed into the HashMap, using the object created in line 3:
    - Theoretically, this should work : since both Money Objects here, have the same hashcode. So, when invoking the 
      method `HasMap.getOrDefault()` with the same key, it should return the correct value : the string "Cost of life".
    - Practically, This will work only if we define a hashcode method on our custom Object "Money". So, this hashcode 
      method, will be called behind the scene to genrate the hash code and compare it with one stored as a key in the 
      HasMap. 

````java
Map<Money, String> costToName = new HashMap<>();
// line 2:
costToName.put(new Money(new BigDecimal(42), new Currency("USD")), "Cost of life");
// line 3:
Money cost = new Money(new BigDecimal(42), new Currency("USD"));
// line 4:
System.out.println(cost + " -> " + costToName.getOrDefault(cost, "nothing, really..."));
````
###### c. treat the Value object as immutable:
- Usually, we also make/treat the value objects as **immutable**: i.e. instead of changing the value objects, we create
new instances that wrap the new values:
    - Why ? 
    From the practical perspective, immutable types are handy, as they can be easily shared between different objects and returned by the entities without the risk of compromising consistency. 
    From the conceptual perspective, it makes sense to create a new instance of a value object when the value changes, as we’re literally assigning a new value.
````java
// wrong
this.address.setStreet(event.getStreet);

// good
this.address = new Address(event.getStreet, ...);
````

##### 4.4. Benefits of Value Objects Pattern:
- The code gets more expressive:
````java
// without
Map<Long, String> repertoire; 
// with
Map<PersonId, PhoneNumber> repertoire; 
````

- They make our code safer, as the type system prevents us from doing stupid mistakes:

````java
// without
Person(String firstName, String lastName, String email) {...}

new Person("jon.defoe@gmail.com", "jon", "defoe"); // compile even though the order of args is incorrect => BUG

// with
Person(FirstName firstName, LastName lastName, Email email) {...}

new Person(
  new Email("jon.defoe@gmail.com"),
  new Firstname("jon"),
  new Lastname("defoe")
  ); // doesn't compile because the order of args is incorrect => Good Behavior
````

- They give us the flexibility in terms of internal representation. 
For example, I could easily change **Code A** to **Code B**, without changing most of the PersonId  clients:

````java
// Code A
class PersonId {
  String Value;
}

// Code B
class PersonId {
  String Value;
  public Person(Long value){
    this.value = value.toString();
  }
}

````

- As mentioned, they also encapsulate related logic e.g. validation (**code C**) and calculus (**code D**):

````java
// Code C
class Email {
  String Value;
  public Email(String value){
    // Validation Rule:
    if(!value.contains("@")){
      throw new InvalidEmailException(value);
    }

    this.value = value;
  }

}

// Code D
class Money {
  BigDecimal amount;

  public Money(BigDecimal value){
    this.amount = value;
  }
  // Calculation
  Money addToBalance(BigDecimal amountToAdd){
    return new Money(this.amount.add(amountToAdd));
  } 
}

````

##### 4.5. Drawbacks of Value Objects Pattern:
- The obvious drawbacks of value objects are that the numbers of classes in the project might grow significantly and, 
as they’re usually immutable, the number of created objects, too
   
##### 4.6. Implementing Value Objects:
There are three real implementation examples for value objects – using plain Java, Project Lombok, and JPA.
  
- *Plain Java*:
  
We can implement value objects in plain Java. Basically, this boils down to implementing an immutable class by ourselves
and providing an equals method that compares fields by values. As a reminder, to create such an immutable class, we have to:
  - Make it `final` 
  - Make all the fields `final` (this also implies no field mutation in methods and creating new objects when someone wants a
   value object with different data)
  - Copy all mutable state during construction and retrieval.
  
````java
// (1) make the Class final
public final class Address {
  // (2) Make all the fields finals:
  private final String street;
  private final String streetNo;
  private final String city;
  private final String zipCode;
  // (2) Don't add any setter methods, instead we can use some update method that return NEW Object whenever we want a
  // value object with different data :
  public Address updateStreet(String newStreet){
    return new Address(newStreet, this.streetNo, this.city, this.zipCode);
  }

  // (3) Constructors and Getters 
  public Address(String street, String streetNo, String city, String zipCode){
    this.street = street;
    this.streetNo = streetNo;
    this.city = city;
    this.zipCode = zipCode;
  } 
  public Street getStreet(){
    return this.street;
  } 
  // the rest of getters..


  @Override
  public boolean equals(Object o){
  if (this == o) return true;
  if (o == null || getClass()!=o.getClass()) return false;
  Address address = (Address) o;
  return Objects.equals(this.street, address.getStreet()) && 
         Objects.equals(this.streetNo, address.getStreetNo()) &&
         Objects.equals(this.city, address.getCity()) &&
         Objects.equals(this.zipCode, address.getZipCode());
  }
  @Override
  public int hashCode(Object o){
  return Objects.hash(this.street, this.streetNo, this.city, this.zipCode);
  }
}
````

- *Project Lombok*:
The use of the plugins **Project Lombok** will allow us to limit the amount of boilerplate code we need to write. 
The Lombok annotation `@Value` designed specifically to support easy creation of value objects: 
    - it auto-generates `private` and `final` keywords on the Class and on all its fields, 
    - it auto-generates also a constructor, getters, and equals/hashcode methods.
However, for the custom update methods, we need to add them manually:
````java
@Value // it makes this Class final
public class Address {
  //it makes all the fields "private final":
  String street;
  String streetNo;
  String city;
  String zipCode;
  // Constructors and Getters are auto-generated 
  // equals and hashCode are auto-generated

  // We need to manually add the update methods :
  public Address updateStreet(String newStreet){
    return new Address(newStreet, this.streetNo, this.city, this.zipCode);
  }


}
````

##### 4.7. Summary of Value Objects Pattern:
- The Value Objects pattern transforms values in our projects into real objects, giving us more type safety, hiding
implementation, and giving a home to all related logic. 
- That being said, we should always evaluate if the mentioned benefits outweigh the drawbacks of creating extra classes
, which, in Java, implies extra source files and a rapidly growing size of the project. 
- To implement a value object, we simply wrap a value into an immutable class with an equals/hashcode pair that compares
the objects by values.

#### 5. Immutable Objects:

##### 5.1. Definition Immutable Objects:
An immutable object is **an object whose internal state remains constant after it has been entirely created.**
Since it cannot change state, this means that the public API of an immutable object guarantees us 
- that it will behave in the same way during its whole lifetime.
- that this immutable objet cannot be corrupted by thread interference or observed in an inconsistent state, making them
 useful in concurrent applications.
 
If we take a look at the class String, we can see that even when its API seems to provide us a mutable behavior with its replace method, the original String doesn't change:
````java
String name = "baeldung";
String newName = name.replace("dung", "----");
 
assertEquals("baeldung", name);
assertEquals("bael----", newName);
````
The API gives us read-only methods, it should never include methods that change the internal state of the object.

##### 5.2. Benefits Immutable Objects:
- Immutable objects are thread-safe: Since the internal state of an immutable object remains constant in time, we can share
 it safely among multiple threads.

- We can also use it freely, and none of the objects referencing it will notice any difference, we can say that immutable
 objects are side-effects free.

##### 5.3. How to implement "Immutable Objects":
Building the API of an immutable object requires us to guarantee that its internal state won't change no matter how we use its API.
The following rules define a simple strategy for creating immutable objects:

- (1) Make all fields final and private.
- (2) Don't provide "setter" methods — methods that might modify the attributes of our Immutable Object (to meet the
 requirements of an immutable API, our class should only have read-only methods.).
- (3) Don't allow subclasses to override methods. The simplest way to do this is to declare the class as final. 
A more sophisticated approach is to make the constructor private and construct instances in factory methods.

````java
// (3) declare the class as final to prevent the overriding of its methods
final class Money {
    // (1) declare all the fields as private and final:
    private final double amount;
    private final Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    // (2) Only-read methods (no setters)
    public Currency getCurrency() {
        return currency;
    }
    public double getAmount() {
        return amount;
    }
}
````
- N.B: *However, in our example we are only guaranteed that the currency won't change, so we must rely on the Currency API to
 protect itself from changes.*

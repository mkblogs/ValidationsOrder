# Spring Boot Custom Validations and Exception Handling
In this example, we will cover following things.
- Basic Validations
- Custom Defined Validation
- Reusing the Custom Validations for multiple classes
- Custom Validations with Group Sequence
- Control Advice Exception Class
- Response Object
## Basic Validation
Basic Validations like `@NotEmpty` , `@Size` , `@NotNull` etc.
```java
@NotEmpty(message = "{account.name.notempty}",groups = {First.class})
@Size(min = 3,max = 50,message = "{account.name.size}",groups = {Second.class})
private String name;
@NotEmpty(message = "{account.type.notempty}",groups = {First.class})
@Size(min = 2,max = 50,message = "{account.type.size}",groups = {Second.class})
private String type;
@NotNull(message = "{account.amount.notnull}",groups = {First.class})
@DecimalMin(value = "99.00",inclusive = false,message = "{account.amount.min}",groups = {Second.class})
@DecimalMax(value = "1000.00",inclusive = true,message = "{account.amount.max}",groups = {Second.class})
private BigDecimal amount;    
```
 
## Defined Custom Validation
Created one Custom Validation called `@UniqueValue`, it accepts two parameters (`methodName` & `className`) . 
```java
@UniqueValue(
methodName = "findByAccountName",
className  = "com.tech.mkblogs.account.service.AccountService"
message    = "{account.name.alreadyexists}",
groups     = Default.class
) 
```
```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Constraint(validatedBy = UniqueValueValidator.class)
public @interface UniqueValue {
    String message() default "Already exists value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};	    
    String methodName();
    String className();
}
```
### Implementation of `@UniqueValue`
  ```java
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object>{
    private String className;
    private String methodName;

   @Override
    public void initialize(UniqueValue unique) {
		this.methodName = unique.methodName();
		this.className = unique.className();
	}
	
    @Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
	 //1 Check the Object type as per requirement
     //2 using java reflection call the service and check value exists or not	
	}
  }
  ```
First validator creates object of service class then calls the given method and return db values.
After that validator check size is empty or not. It size is not empty then validator should return true otherwise false.
  



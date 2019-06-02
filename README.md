
# Welcome to Simplex: A new approach to exceptions handling in java applications

## Introduction

**Simplex**, standing for ***Simple Exception***,  is a declarative exceptions handling library for java applications.

The idea is simple: Usually when writing web applications in java we have to create some kind of framework for exception handling. It usually includes mapping the Errors, exception parameters, Http Status codes and so forth.
Some frameworks, like spring mvc, address these issues by providing exception hanling facilities, but still we have to manually create an exception.


**Simplex** goes one step further with this idea and allows defining the exceptions in a yaml file with a simple but yet very powerful DSL.

## What it really does

Then during the build process the exception source code gets generated by a maven plugin (currently the implementation supports only maven, but gradle support can be added really easily) even before the actual compilation happens. Then the code gets compiled along with generated exceptions.

Exception Source Code generation mechanism is a heart of the framework.
The exception is generated with parameters, typesafe constructor, getters for parameters and so forth.


## A  Detailed  example:

In order to declare the exception create `src/main/resources/simplex-exceptions.yml` in the application module (its also possible to create a dedicated module for all exceptions, its up to the application to decide where to place the exceptions):

    ---
    id: "OUT_OF_RANGE"
    errorCode: 1000
    httpStatus: 400
    message: "The value {{value:Integer}} is out of range: [ {{minValue:Integer}}, {{maxValue:Integer}} ]"

Note that there is an Error Code for exception, an http response statue an id and the message to be shown
Note also that there are three parameters:

 - value of type Integer
 - minValue of type Integer
 - maxValue of type Integer

Now in order to actually generate the exception add the following plugin definition:

```xml
    <build>
      <plugins>
        <plugin>
            <groupId>org.markbr.simplex</groupId>
            <artifactId>simplex-maven-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
               <exceptionsPackage>org.markbr.simplex.testapp.exceptions</exceptionsPackage>
               <baseExceptionPackage>org.markbr.simplex.testapp.exceptions</baseExceptionPackage>
               <baseExceptionName>BaseTestappException</baseExceptionName>
            </configuration>
        </plugin>
      </plugins>
    </build>
```


The goal "generate" of this plugin is already bound to the `generate-sources`phase so no need to specify the phase.

The configuration is important:
 - exceptionPackage means which package will contain the generted exceptions
 - baseExceptionPackage and baseExceptionName denote the base exceptions for all generated exceptions

Notes:
-  Its possible to not create the base exception at all, in this case, the exceptions will merely inherit from `java.lang.RuntimeException`. In order to skip base exception creation specify the following configuration parameter `<generateBaseException>false</generateBaseException>`

- Its also possible to change the placement of the input yaml file, in this case specify the parameter:
`<inputFile>PATH_TO_YAML_HERE</inputFile>` Currently only one Yaml file supported as an input

- The output folder that will contain the generated sources also can be alteredm by default its `target/generated-sources` however it can be changed by specifying the parameter: `<outputDir>PATH_TO_OUTPUT_DIRECTORY_THAT_WILL_CONTAIN_PACKAGE_STRUCTURE_HERE</outputDir>`


When running the `mvn compile` (or any other maven build command that goes beyond the `generate-sources` phase, note the following maven output in the console:

    [INFO] --- simplex-maven-plugin:1.0-SNAPSHOT:generate (default) @ testapp ---
    [INFO] Simplex - Exception Generation Framework
    [INFO] ====================================================
    [INFO] == Simplex started with the following parameters:
    [INFO] == Input File (YAML): <PATH TO simplex-exceptions.yml GOES HERE>
    [INFO] == Output Directory: <YOUR_APP generated-sources>
    [INFO] == Generate Base Exception: true
    [INFO] == Base Exception will belong to Package: org.markbr.simplex.testapp.exceptions
    [INFO] == Base Exception will be named: BaseTestappException
    [INFO] == Exceptions will belong to Package: org.markbr.simplex.testapp.exceptions
    [INFO] ====================================================


The following directories structure should exist now:

    <module>
       |__target
          |__generated-sources
             |__org
                  |__markbr
                      |__simplex
                         |__testapp
                            |__exceptions
                               |__BaseTestappException.java
                               |__OutOfRangeException.java


And now the most interesting part, lets see the generated code:

#### Here is the base class

```java
    package org.markbr.simplex.testapp.exceptions;

    import java.lang.Object;
    import java.lang.RuntimeException;
    import java.lang.String;
    import java.lang.Throwable;
    import java.util.Map;

    public abstract class BaseTestappException extends RuntimeException {
      public BaseTestappException(String message) {
        super(message);
      }

      public BaseTestappException(String message, Throwable cause) {
        super(message,cause);
      }

      public abstract int getErrorCode();

      public abstract int getHttpStatus();

      public abstract String getRawMessage();

      public abstract Map<String, Object> getParameters();
    }
```

#### Here is the exception class

```java
    package org.markbr.simplex.testapp.exceptions;

    import java.lang.Integer;
    import java.lang.Object;
    import java.lang.String;
    import java.lang.Throwable;
    import java.util.HashMap;
    import java.util.Map;

    public class OutOfRangeException extends BaseTestappException {
      private static final String value_PARAM_NAME = "value";

      private static final String minValue_PARAM_NAME = "minValue";

      private static final String maxValue_PARAM_NAME = "maxValue";

      private static final int errorCode = 1003;

      private static final int httpStatus = 400;

      private static final String rawMessage = "The value {{value:Integer}} is out of range: [ {{minValue:Integer}}, {{maxValue:Integer}} ]";

      private final Map<String, Object> parameters;

      public OutOfRangeException(Integer value, Integer minValue, Integer maxValue) {
        super("The value "+ value +" is out of range: [ "+ minValue +", "+ maxValue +" ]");
        parameters = new HashMap<>();
        parameters.put( value_PARAM_NAME , value );
        parameters.put( minValue_PARAM_NAME , minValue );
        parameters.put( maxValue_PARAM_NAME , maxValue );
      }

      public OutOfRangeException(Integer value, Integer minValue, Integer maxValue, Throwable cause) {
        super("The value "+ value +" is out of range: [ "+ minValue +", "+ maxValue +" ]", cause);
        parameters = new HashMap<>();
        parameters.put( value_PARAM_NAME , value );
        parameters.put( minValue_PARAM_NAME , minValue );
        parameters.put( maxValue_PARAM_NAME , maxValue );
      }

      public Integer getValue() {
        return (Integer)parameters.get( value_PARAM_NAME );
      }

      public Integer getMinValue() {
        return (Integer)parameters.get( minValue_PARAM_NAME );
      }

      public Integer getMaxValue() {
        return (Integer)parameters.get( maxValue_PARAM_NAME );
      }

      public int getErrorCode() {
        return errorCode;
      }

      public int getHttpStatus() {
        return httpStatus;
      }

      public String getRawMessage() {
        return rawMessage;
      }

      public Map<String, Object> getParameters() {
        return parameters;
      }
    }
```


Note the type safe getters, the constructors that will help to properly use this autogenerated exception




Since this gets compiled along with the regular code during the maven `compile` phase, its possible to use these exceptions right in the source code.

From the application standpoint the code that can use these exceptions is straightforward:

    import org.markbr.simplex.testapp.exceptions.OutOfRangeException;
    public class Main {
       public static void main(String [] args) {
          throw new OutOfRangeException(100, 20, 30);
       }
    }


Upon the change in yml, run `mvn generate-sources` again. If some files were deleted, currently run `mvn clean generate-sources` or remove the `target/generated-sources` folder

  ### Test Application
  Testapp module included into the framework denotes a real world spenario for using such a framework. Its a spring boot driven microservice with a web controller (in the example mvc is used, but webflux can be used in the same way)
  Since spring allows using `ControllerAdvice` to handle the base exceptions we can define the following:


```java
    @ControllerAdvice
    public class SimplexExceptionHandlingControllerAdvice {

     @ExceptionHandler(BaseTestappException.class)
     public ResponseEntity<ErrorInfo> handleBaseTestappException(BaseTestappException ex) {
        ErrorInfo result =
            ErrorInfo.builder()
                    .errorCode(ex.getErrorCode())
                    .parameters(ex.getParameters())
                    .rawMessage(ex.getRawMessage())
                    .message(ex.getMessage())
                    .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(result);
     }
    }
```

The `ErrorInfo` is just an object that will denote the data format that should be send to client (for example angular, react, or any other):


```java
    import lombok.Builder;
    import lombok.Value;

    import java.util.Map;


    @Builder
    @Value
    public class ErrorInfo {
        private int errorCode;
        private Map<String, Object> parameters;
        private String rawMessage;
        private String message;
    }
```

* Usage of lombok is totally optional here

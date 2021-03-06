# Unofficial Robinhood Api - Java Wrapper (AMPro Fork)
### A Java wrapper providing easy access to the [Unofficial Robinhood Api](https://github.com/sanko/Robinhood).

Thanks to [Conrad Weiser](https://github.com/ConradWeiser) for making
[the orginal wrapper project](https://github.com/ConradWeiser/Unofficial-Robinhood-Api)
. Without them, this fork would literally be impossible!

## Contents
- [Installation](#INSTALL)
    - [Dependencies](#DEPEN)
- [Usage](#USAGE)
- [RoadMap](#RDMP)
- [Contributing](#CONTR)

<a name='INSTALL'></a>
## Installation
To install & use the Robinhood API, you can either install the latest
[release](https://github.com/AquaticMasteryProductions/Robinhood-Api-Java/releases)
, download the latest source code & build the project yourself,
or use [JitPack](https://jitpack.io/) to add the repository to your
gradle, maven, sbt, or leiningen.
You can download the latest stable build
[here](https://github.com/AquaticMasteryProductions/Robinhood-Api-Java/releases)

<a name='DEPEN'></a>
#### The Robinhood API Depends on these projects & packages
- [Open Unirest](https://github.com/OpenUnirest/unirest-java)
- [Google's Gson](https://github.com/google/gson)
- [Simple Logger For java (SL4J) (optional)](https://www.slf4j.org)

Sample Gradle build.gradle file:
```gradle
plugins {
    id 'java'
    id 'application'
}
sourceCompatibility = 1.8

group 'com.ampro'
version '1.0'

repositories {
    jcenter()
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.AquaticMasteryProductions:Robinhood-Api-Java:v0.8.0-alpha'
    compile group: 'io.github.openunirest', name: 'unirest-java', version: '2.2.04'
    compile group: 'commons-io', name: 'commons-io', version: '2.6'
    compile 'com.google.code.gson:gson:2.8.5'
    compile group: 'org.slf4j', name: 'slf4j-simple', version: '1.7.25'
}
```

For Maven:
```maven
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.github.AquaticMasteryProductions</groupId>
    <artifactId>Robinhood-Api-Java</artifactId>
    <version>v0.8.0-alpha</version>
</dependency>
<dependency>
    <groupId>io.github.openunirest</groupId>
    <artifactId>unirest-java</artifactId>
    <version>2.3.04</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.25</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.5</version>
</dependency>
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.6</version>
</dependency>
```

<a name='RDMP'></a>
## Roadmap

-  ~~Logging users in and out~~
    - Multifactor Login
- ~~Get account information~~
[(JSON Response contents)](https://github.com/sanko/Robinhood/blob/master/Account.md#gather-list-of-accounts)
- ~~Getting information about tickers~~
- Orders
    - Get
        - Get full history (live and closed orders)
    - Post
        - ~~Make &~~ cancel orders (limit, stop, etc)
- Asynchronous requests & RateLimiting
- Clean up & add more JavaDocs
    - Include a more comprehensive guide on what data you are getting from
        each method.
- Improved Wiki & Walkthroughs
- Improve & clarify many vague exceptions
- ~~The structure of the ConfigManager needs to be restructured
  from static/global management to instance-based authorization. The top
  option right now is to change ConfigManager to just a "local" config
  for each API instance (this is to allow for multiple APIs logged in to
  different accounts)~~
- more...so much...more

### Things that will likely *never* happen here
- Transfering funds from bank accounts to Robinhood & visa versa.
    - That's a *lot* of security
    [we can't provide](https://www.youtube.com/watch?v=2bVhjOcz0s0)
    (at least not now) and will leave it to Robinhood & their applications.

<a name='USAGE'></a>
## Usage
[We Have Javadocs!](https://aquaticmasteryproductions.github.io/Robinhood-Api-Java/)

This library is built with the intention of making extracting information
 from the Robinhood API as easy as possible.

Say for the following example, we want to get our account number, and
 how much buying power we have available

```java
//Providing a username and password automatically logs the instance into our account!
RobinhoodApi api = new RobinhoodApi("username", "password");

//Make the request for all of the account information
AccountElement accountData = api.getAccountData();

//Extract the data we want
String accountNumber = accountData.getAccountNumber();
Float buyingPower = accountData.getBuyingPower();

//Print to console!
System.out.println(accountNumber + " : " + buyingPower);
```

### Options
The options associated with a Robinhood account can be obtained by calling the `getOptions` method on a `RobinhoodApi` object. For example:

```java
RobinhoodApi api = new RobinhoodApi("username", "password");
List<Option> options = api.getOptions();

for (Option option: options) {
    // ...Access each option...
}
```

The `Option` class includes all basic option information supported by the Robinhood API. For example, assuming the following option exists for a given Robinhood account:

> 2  Aug 17, 2018 calls of ABC with a strike price of $100 at a premium of $1.10/share (100 underlying shares)

The data associated with an `Option` object named `option` can be obtained as follows:

| Data          | Method Call | Example Result |
| ------------- | ------| ------ |
| Symbol | `option.getSymbol()` | `ABC` |
| Average Open Price | `option.getAverageOpenPrice()` | `110.0000` |
| Quantity | `option.getQuantity()` | `2.0000` |
| Type | `option.getLegs().get(0).getOptionType()` | `call` |
| Strike price | `option.getLegs().get(0).getStrikePrice()` | `100.0000` |
| Expiration Date | `option.getLegs().get(0).getExpirationDate()` | `2018-08-17` |
| Underlying Shares | `option.getTradeValueMultiplier()` | `100.0000` |

The `Option` class supports multiple legs (see [Multi-Leg Options Order](https://www.investopedia.com/terms/m/multilegorder.asp)), but in many cases, options contain only a single leg (as in the example above). In this case, the type, strike price, and expiration date of this simplified option is the type, strike price, and expiration, respectively, of its first (and only) leg. 

To remove tediousness of extracting this simplified date, a `SimpleOption` object can be obtained from an `Option` object using the `asSimpleOption()` method. In order to determine if an `Option` object is a simple option (has exactly one leg), the `isSimpleOption()` method can be used:

```java
Option option = // ...

if (option.isSimpleOption()) {
    SimpleOption simpleOption = option.asSimpleOption();
    System.out.println(simpleOption);
}
```

Assuming the `option` object contains the data from the above example, executing this snippet results in the following output:

```
2 Aug 17, 2018 calls of ABC with a strike price $100 purchased for an average price of $110 (100 underlying shares)
```

To obtain the individual option information from a `SimpleOption` object named `simpleOption`, the following method calls can be used:

| Data          | Method Call |
| ------------- | ------| 
| Symbol | `simpleOption.getSymbol()` |
| Average Open Price | `simpleOption.getAverageOpenPrice()`
| Quantity | `simpleOption.getQuantity()` |
| Type | `simpleOption.getType()` |
| Strike price | `simpleOption.getStrikePrice()` |
| Expiration Date | `simpleOption.getExpirationDate()` |
| Underlying Shares | `simpleOption.getTradeValueMultiplier()` |

### [More detailed instructions on usage](https://github.com/AquaticMasteryProductions/Robinhood-Api-Java/wiki)

<a name='CONTR'></a>
## Contributing
I am *very* new to web and REST implentations, so really...*help*.
Any pull requests will be looked over and (likely) accepted given they follow
a few guidlines:

- Documentation
    - Classes & methods must have docs, unless they are increadibly
    intuituve (e.g. ``getAccountId()``).
    - We cant accept your code if we don't know what's going on, and we don't
    have time to decrypt your methods, as good as they might be.
- Adding new Dependencies
    - If you use an external library, it should be available from the
    maven/gradle repositories or a reputable library wtih gradle compatablity,
    since this library is built from gradle.
    - Try to keep any new external dependencies to a minimum. We will accept
    most things from reputable sources (Apache, Google, etc), but we
    still reserve the right to deny any pull requests that add too many and/or
     unreliable dependencies.
- Prettyness
    - We're pretty open to coding styles here, everyone has their prefered way
    to code, but at some point...*ya gotta look consistant*.
    1. ``for``, ``if``, ``while``, etc should have curly braces. We know it
    looks so *minimalistic* without them, but it helps code reading a ton!
    3. If a method is too long (throws or multiple parameters) try and
    move part of it to the next line like this:
    ```java
    protected static syncronized SuperCoolObject
    superCoolMethod(int a, int b, boolean YOUCANTHANDLETHETRUTH) {
        ...
    }
    //or
    protected static syncronized SuperCoolObject superCoolMethod()
    throws Exception {
    ...
    }
    ```
    2. Try your best to keep lines under ``80`` characters and *must* be under
    ``90``. Yes, most moitors can handle plenty more text than that,
    but side-by-side views get to be a PItA when the lines are too long.
    If you use any modern IDE it's super easy to set an automatic
    code-wrapping or guidelines to help you stay within readable spaces.
    If a method or constructor is called and it ends up being too long,
    let it span across multiple lines, like so (no need to be perfect,
    just similar)...
    ```java
    Thing thisThing = new Thing(param, other param,
                                more param, so much param);
    //or
    thisThing.superCoolMethod(
                "Wow this param is such a stupid long string wow...maybe too long?"
    );
    ```
    - If you find anything in the existing code, feel free to correct or point
    it out, nobody's perfect...



## Disclaimer
I have never taken up a project working with APIs like this.
I intend to write this wrapper to the best of my ability, and continue to
give support/make changes as time passes.

However, that being said, I aim to make the finished project both
efficient in terms of how much computing power it requires, and easy for
other developers to build functionality on top of.

If you have any questions feel free to email AMPro at
aquaticmasteryproductions@gmail.com

-- Jono

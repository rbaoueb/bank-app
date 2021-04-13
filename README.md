# BankApp

[![codecov](https://codecov.io/gh/rbaoueb/bank-app/branch/master/graph/badge.svg)](https://codecov.io/gh/rbaoueb/bank-app)
[![Build Status](https://travis-ci.com/rbaoueb/bank-app.svg?branch=master)](https://travis-ci.com/rbaoueb/bank-app)


bank-app is a JAVA API which allows us process operations on our bank account

## Report
The javadoc report of the API can be reachable through this [link](https://rbaoueb.github.io/bank-app/)


## Installation
you can import the source project on your IDE or build the jar and push it to your maven repository :

```bash
git clone https://github.com/rbaoueb/bank-app.git
cd bank-operations
mvn clean install
```

then add the generated dependency to your project pom.xml:
```xml
<dependencies>
    <dependency>
      <groupId>com.mrbaoueb</groupId>
	  <artifactId>bank-operations</artifactId>
      <version>1.0.0</version>
    </dependency>
</dependencies>
```

## Usage

There is an example of API usage (creating new customer and process DEPOSIT operation of 500 euros) : 
```java

	public static void main(String[] args) {
        Account account = Account.builder()
                        .balance(Money.euros("1300"))
                        .number("123456").type(AccountType.PERSONAL)
                        .id(BankUtils.generayteId())
                        .build();
        Customer customer = Customer.builder()
                            .firstName("John")
                            .lastName("Doe").account(account)
                            .id(BankUtils.generayteId())
                            .build();
        OperationProxy proxy = new OperationProxyImpl();
        proxy.processOperation(OperationType.DEPOSIT, customer, Optional.of(Money.cents(50000L)));
	}
	
```

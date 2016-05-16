# AutomationExercise

Automation Exercise is a maven project which performs below task on "http://store.demoqa.com" using Java, Selenium webdriver, Junit and log4j.  
1. Submit an order for an Apple iPhone4s 16GB SIM-Free – Black (known issue with State drop-down, selecting Country is adequate) and verify the Total Price: given is correct (assume shipping cost is correct based on your choice). You may assume prices shown on product pages are the correct price.
2. Verify updating your account details is saved and retrieved after logging out and back in using the My Account link.
3. Verify removing all items from your cart produces an empty cart message.

Installation Instruction 
1. Clone AutomationExercise project
2. Go to AutomationExercise folder 
3. Run "mvn clean"
4. Run "mvn -Dtest=AllTests test"

Prerequisites:
1. All the required inputs store in data.properties file. 
2. Update properties file "AutomationExercise/src/main/resources/data.properties" for input 

Issue:
 Sometime random account fields remain blank after successfully adding/updating account detail. 
 It is not reproduce while trying manually.

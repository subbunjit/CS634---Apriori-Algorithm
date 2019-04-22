# CS634-Apriori-Algorithm
Java Implementation of Apriori Algorithm 
Apriori is an algorithm for frequent item set mining and association rule learning over transactional databases. 
It proceeds by identifying the frequent individual items in the database and extending them to larger and larger item sets as long as those item sets appear sufficiently often in the database.

# Prerequisites
Need to have Java 7/8 installed on your machine. Other version support will be provided as soon as possible.

# Usage
Here, we create  10 items usually seen in Amazon, K-mart, or any other supermarkets (e.g. diapers, clothes, etc.). I have attached the sample file containing list of items for Amazon and k-mart.

# Running the tests
The program takes data source, Minimum Support in percentage and Minimum Confidence in percentage as input.

Data Source: This is to select where the input is coming from. For this test, the data is coming from one of the five files that were created using Apriori.java.
Minimum Support: A minimum support is applied to find all frequent itemsets in a database.
Minimum Confidence: A minimum confidence is applied to these frequent itemsets in order to form rules.
Result: The result will show the association rules in the given dataset with the given minimum support and minimum confidence if there are any. If there are no association rules in the the set with the given support and confidence conditions, try to plug in some different (if you didn't get any results, try feeding some lower values) values of them.

# Running the program:

Run the program using the following command(Make sure to change the input file in the program every time you compile and run):

$ javac Apriori.java

$ java Apriori

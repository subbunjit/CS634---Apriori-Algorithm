# CS634-Apriori-Algorithm
Java Implementation of Apriori Algorithm 
Apriori is an algorithm for frequent item set mining and association rule learning over transactional databases. 
It proceeds by identifying the frequent individual items in the database and extending them to larger and larger item sets as long as those item sets appear sufficiently often in the database.

# Prerequisites
Need to have Java 7/8 installed on your machine. Other version support will be provided as soon as possible.

# Usage
Here, we create  10 items usually seen in Amazon, K-mart, or any other supermarkets (e.g. diapers, clothes, etc.). Have attached the sample file containing list of items for Amazon and k-mart.
The program first calculates the Support and Confidence for each and every item stored in the text file and then calculates the association rules with each and every item stored.

Run the program using the following command:

$ javac Apriori.java

$ java Apriori

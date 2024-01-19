#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@ProductVerification
Feature: To Add and checkout the product

  @smoke @Regression 
  Scenario: AddTheProducts
    Given Log in to application with username and password
    And Get the list of items to be added for checkout from Database
    And Add the products to cart and verify the text shown as remove

  @smoke  @Regression
  Scenario: AddTheProductsAndCheckout
    Given Log in to application with username and password
    And Get the list of items to be added for checkout from Database
    And Add the products to cart and verify the text shown as remove
    Then GoTo cart and verify product and price are matched

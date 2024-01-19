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
@Excel
Feature: Title of your feature
  I want to use this template for my feature file

  @ReadExcel @Regression
  Scenario: Login Application by Reading Excel Data
    Given Read Data From Excel To get Username
    | Demo |
    | credentials | 
    | 1 |
    | 2 |
    And Read Data From Excel To get Password
    | Demo |
    | credentials | 
    | 2 |
    | 2 |
    Then Login To application with provided Username and Password
    And Get the list of items to be added for checkout from Database
    And Add the products to cart and verify the text shown as remove
   
  @WriteExcel @Regression
  Scenario: Login Application by Reading Excel Data after writting data
    Given Write Data to Excel for Username
    | Demo |
    | Trials | 
    | 1 |
    | 1 |
    | standard_user |
    And Write Data to Excel for Password
    | Demo |
    | Trials | 
    | 2 |
    | 1 |
    | secret_sauce |
    Then Read Data From Excel To get Username
    | Demo |
    | Trials | 
    | 1 |
    | 1 |
    And Read Data From Excel To get Password
    | Demo |
    | Trials | 
    | 2 |
    | 1 |
    Then Login To application with provided Username and Password
    And Get the list of items to be added for checkout from Database
    And Add the products to cart and verify the text shown as remove
    Then GoTo cart and verify product and price are matched
    
  @RadExcel @Regression
  Scenario: Read From Excel Data
    Given Read Data from Excel to get Ages
    | Demo |
    | Details | 
    | 1 |
    | 1 |
    
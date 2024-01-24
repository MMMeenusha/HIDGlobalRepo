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
@Assesment
Feature: HID Global Assesment

	@A1 @MainConcepts
  Scenario: Texts from Main Concept
    Given Navigate to React JS site
    Then Get Text from "Main Concept" and save it in a file
    
  @A1 @AdvancedGuide
  Scenario: Texts from Advanced Guides
    Given Navigate to React JS site
    Then Get Text from "Advanced Guides" and save it in a file
   
  @A2 @ScrollVerification
  Scenario: Verify Scroll Functionality
    Given Navigate to React JS site
    Then Verify Scroll funnctionality and the respected content is Bolded on Right Navigation and Blue color line is seen

	@A3 @APIJson
  Scenario: API Response using JsonPath
    Given Get Response for API site
    Then Get the count of categories list
    And get the names and geo locations of food category
    
  @A3 @APIPOJO
  Scenario: API Response using POJO
    Given Get Response for API site using POJO
    Then Get the count of categories list using POJO
    And get the names and geo locations of food category using POJO
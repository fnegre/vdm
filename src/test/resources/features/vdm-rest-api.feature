Feature: Rest API for reading VDM

  Scenario: Calling to GET /api/posts
    Given the Rest API exists
    When the client calls /api/posts
    Then it should answer with status code 200
    And it should answer with several VDM

  Scenario: Calling to GET /api/posts/1
    Given the Rest API exists
    When the client calls /api/posts/1
    Then it should answer with status code 200
    And it should answer with 1 VDM
    And the ID of the VDM should be 1
    And the content should not be empty


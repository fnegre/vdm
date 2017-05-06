Feature: Download of the vdm

  Scenario: calling the downloader with 10 as count argument
    Given the downloader exists
    When the client calls the downloader with 10 as count argument
    Then it should answer with 10 elements


  Scenario: calling the downloader with 50 as count argument
    Given the downloader exists
    When the client calls the downloader with 50 as count argument
    Then it should answer with 50 elements
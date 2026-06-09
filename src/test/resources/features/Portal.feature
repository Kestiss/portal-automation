@all
Feature: Download a free wallpaper from Portal


  Scenario Outline: Guest downloads a wallpaper
    Given guest is on Home page
    When guest clicks on browse now link
    And guest searches for phrase <searchPhrase>
    And guest opens a <wallpaperType> wallpaper
    And guest downloads the wallpaper
    Then the wallpaper should be downloaded successfully

    Scenarios:
      | searchPhrase | wallpaperType |
      | sun          | free          |
      | moon         | premium       |


  Scenario: Guest is asked to login when premium wallpaper needs credits
    Given guest is on Home page
    When guest clicks on browse now link
    And guest searches for phrase moon
    And guest opens a premium with credits wallpaper
    And guest clicks on Download button
    Then guest sees Login modal


  Scenario: Search has no results
    Given guest is on Home page
    When guest clicks on browse now link
    And guest searches for phrase dfgsdfgfsgdf
    Then search returns no results

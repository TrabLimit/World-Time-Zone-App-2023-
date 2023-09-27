# My Personal Project #1:

## World Time Zone Tracker Application

## WARNING: To run this program, make sure you have Coretto 11.0.20 JDK installed.

### What will the application do?

- This application will allow us to add a city from a different country with different time
  zones. We can enter the city's name, coordinates (latitude and longitude i.e. 37.5 N, 127 E) on the map, and time
  difference (in respect to UTC offset value i.e. UTC +9) to our list of cities. From it, users can see the current time
  of the added city, which is updated *real-time.* **Each different cities will show different times depending on their
  time zone.**
- In addition, the user can also **set custom time (in terms of date, hour, minute, and/or second) other than
  the current time.**  This will allow comparisons and calculations for future and past dates and times, which
  is convenient for scheduling any international/global affair.
- We can also **organize the order of cities** shown on the list from top to bottom
  (move by dragging or up/down arrow button - TENTATIVE), so that we can have our home city
  on top, and have cities laid in order of preference for easy viewing. The default order of cities will be in respect
  to time of addition (oldest to newest).
- Any city that has been added to the list **will be made visible in a global map**. Using the coordinates, we can
  locate the city as a dot or icon in the map that can be clicked to view the different time.
- Finally, we can also **add people/location/event labels for any city in the list.** For example, if I have my parents
  in Tokyo, then I should be able to add both my parents in Tokyo once I put Tokyo in the list.
  I can also add a Zoom Call as an event that is standardized to Toronto
  time, simply by adding an event named "Zoom meeting location" to the Toronto entry. These labels can be added when we
  add the city to our list, or we can add the labels after. Then we can have **the list of labels in a separate UI,**
  where
  we can click on them, we can have the corresponding city in the list be highlighted. These labels can also be added,
  moved around, and erased by us.
- **Possible features:**
    1. Real-Time Current Time and Date Display for Multiple Cities or Locations in vertical list form
    2. Display of cities (as a dot) in global map with corresponding coordinates. Users are able to click on the city
       dot icon to have that particular city in the list be highlighted (or if the list is long, the list will be
       scrolled to the selected city).
    3. Display labels assigned to some cities. List of labels (i.e. Parents, friend, remote work) will be viewable at
       separate UI, where we can add, remove, or reorder labels, and we can click on each label that would direct us to
       thecity where it belongs to.
    4. Adjust and Change date
       and time (applied to all cities selected from the list). The change can be done in any city of choice. All other
       cities in the list will have their times updated accordingly.
    5. Add/Move/Delete Cities in the List, Select Home City (set
       to the top of the list as default).
    6. Toggle between AM/PM and 24-hour setting for time display, arrange date order such as YYYY/MM/DD, DD-MM-YYYY, or
       Month DD, YYYY.
- X will be city name, city coordinates, city UTC zone, label entry, and selection between current time or custom date
  and time.
- Y will be list of cities and location, list of labels, the display of time depending on the city and the time setting.

### Who will use it?

- The application will be used by people **who need to keep track of time of other
  locations.**
  Examples would be people trying to their friends or families living in different countries, scheduling a Zoom/Skype
  meeting with international associates, checking assignment due date for School while on leave from region due to
  vacation or family emergencies, and much more.

### Why is this project of interest to you?

- As someone with family and friends on the other side of the globe, it can be very cumbersome to check the time
  zone to make sure I am not calling them at late at night or in the middle of their busy hours. I also hear many
  stories about people being frustrated with scheduling a Zoom call with clients/associates abroad due to differing time
  zones. I had to attend events that were set in either Eastern Time, GMT, or other time zones and it could be a
  nuisance to calculate.
- Although default clock app in iPhone can show different time zones, it is only restricted to current time only,
  and I cannot input a different time or date to my preference. I want to design an app where I can both check
  the current time of different cities, and set my own time and date and view the different times based on that input.

### User Stories

NOTE: Different typefaces represents realization of the user stories (if realized).

**Bold = Realized in Console** \
<u> Underline = Realized in GUI</u>

#### Phase 1:

- **As a user, I want to be able to add a city and its information (i.e. name, time zone, etc.) to my list of cities.** 
- **As a user, I want to see the list where I added all my cities, with all its information visible.**

- **As a user, I also want to modify/edit the information of any city in the list.**
- **As a user, I also want to delete the city from the list.**
- **As a user, I wish to search my city within the list.**
- **As a user, I want to be able to view each city and its respective local time and date in the list of cities. I
  should
  be able to scroll around the list if the list gets long as well.**

- As a user, I want to click the city on the map to be directed to that city's time and date, which are found in the
  viewable list of cities.
- **As a user, I want to be able to add a label (i.e. pen pal at Paris, remote work, relative living in Seoul, etc.) to
  my
  added city to inject meaning to that city.**
- As a user, I would like to view the labels in a separate list of labels, but also want each label to be shown on the
  city that I have assigned to.
- **Just like the cities, as a user, I want to change and/or remove a label.**
- As a user, I want to click on the label within the list, and have it direct me to the city to which
  the label was assigned. This way, I can navigate to my labeled cities faster than scrolling down the list of cities.
- **As a user, I want an option where I can set my own date and time on one city in the list, and have all the other
  cities show their equivalent times according to the input.** I should be able to switch this custom time setting with
  current time setting at a whim.

- **As a user, I want to see the dates and times in a format I want to see. I want to switch 24 hours or AM/PM option
  for time, and I want my date format to switch around, like YYYY-MM-DD, DD/MM/YYYY, and more.**

####  Phase 2:
- **As a user, when I quit my app, I want to be reminded to save my city list to a file (choice of 3 files) and have the
option to save (or refuse).**
  
- **As a user, I want the option to load my list from the saved file (choice of 3) at the start of the application.**

#### Phase 3:

- <u> As a user, I want to be able to add a City (more than once) to a CityList and have the change visible in the List panel in the GUI.</u>
- <u>As a user, I want to select a city in the GUI panel and delete or modify it and have the changes be updated to the CityLIst panel 
 and in the map.</u>
- <u>As a user, I want to select a city in the GUI panel and move it up and down across the list and reorder CityList.</u>
- <u>As a user, I want to clear all cities in the list if I wish.</u>
- <u> As a user, I want to be able to load and save the state of the application by accessing it through the menu or shortcut.</u>
- <u> As a user, I also want to see my city on the map as an icon, based on the coordinates I entered. </u>



# Instructions for Grader

#### DISCLAIMER: For grading GUI, please run or review <u> RefactoredTimeAppGUI2.java.</u> and <u>CityIconPanel.java.</u> Those files comply with all checkstyles and contains proper documentation. 

#### NOTE: TimeAppGUI2 is a rough, unedited standalone version. It runs and behaves like the refactored version, but does not obey the checkstyle. It is a rough draft file so in case the refactored file breaks, we can rollback to this file and start over. DO NOT use TimeAppGUI2 for grading.

#### NOTE: The package "obsolete" contains a failed/abandoned classes in construction of the GUI. Their main method is disabled and the code is incomplete. They are preserved only for future uses after the semester. Please ignore them.

- You can generate the first required action related to **adding Cities** to a CityList by **clicking the 
"Add City" button** and **putting the necessary information inside the "Add city" frame and clicking "Ok". Make sure to click "Ok" to confirm addition,
 as hitting Enter key will NOT do anything.** To cancel the 
process, simply close the window. This process can be done multiple times to add multiple Cities. Note that any invalid input or duplicate city will be
rejected by the app (but the app will still run regardless). Please follow the following criteria:
  1. Your city **must have a name.**
  2. Your city **must have latitude magnitude between 0 and 90.**
  3. Your city **must have longitude magnitude between 0 and 180.**
  4. Your city **must NOT be an exact duplicate of a city already present** in CityList.
- You can generate the second required action related to **deleting Cities** from CityList by **selecting a city from 
 the scrollable CityList panel** and then **clicking "Remove City"** (which should be enabled the moment you click a city). There is a warning 
dialog pane that shows up to confirm your action. You may **click "Ok" to confirm removal.** Closing the pane or clicking "No"
will cancel the removal. 
- Alternatively, you can also just **click "Clear all Cities" to remove all Cities in CityList.**
- You can generate the third action related to **modifying Cities** in CityList by **selecting a city from
  the scrollable CityList panel** and then **clicking "Modify City"** (which should be enabled the moment you click a city).The procedures for modifying
a city is **identical to adding a city.** Make sure to **click "Ok" to confirm modification,
  as hitting Enter key will NOT do anything.** However, if you keep the city as the same way it is and hit "Ok", it will
return a duplicate city warning message and will not make any changes. Any invalid input will also result in no changes being done.
- You can generate the fourth required action related to **reordering Cities** in CityList by **selecting a city from
    the scrollable CityList panel** and then **clicking "Move Up" or "Move Down"** (both of which should be enabled the moment you click a city).
You may reorder or sort the selected city inside CityList by moving in up and down across the scrollable CityList panel.

- You can locate my visual component by viewing the right side of the panel. You will see **a global map with all the Cities 
in CityList displayed as icons in accordance to their respective names and coordinates.** Any addition, removal, or modification
of any city will **update the map** accordingly.
- My second visual component is the **City Info Panel,** located on the upper left of the frame. When no city is selected, the panel is blank. However,
when a city is **selected** from the list, **all the information about the city including label, name, UTC offset, and coordinates will
be shown.** Furthermore, **the local current time of the city will be displayed right under** as well, and it will update in real time.
- You can load the state of my application by **selecting "Menu -> Load" or pressing "Ctrl + L".** You are given **3 file choices** from which you may
load your CityList. If the chosen file is missing, there is no loading and the app will generate an error message, but the app
functions regardless as if it never happened. 
- You can save the state of my application by **selecting "Menu -> Save" or pressing "Ctrl + S".** You are given **3 file choices** to which you may
  save your CityList. If the chosen file is not present, then the app will create a new file and will write the information.
- To quit the GUI app, simply close the app window.



## Phase 4: Task 2

#### The following is an example of the printed log events for adding, removing, modifying, reordering and clearing Cities in CityList:


C:\Users\limsa\.jdks\corretto-11.0.19\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.1.3\lib\idea_rt.jar=55393:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.1.3\bin" -Dfile.encoding=UTF-8 -classpath "C:\Users\limsa\OneDrive\UBC\S2 2023\CPSC 210\Term Project\project_x4b1j\out\production\Project-Starter;C:\Users\limsa\OneDrive\UBC\S2 2023\CPSC 210\Term Project\project_x4b1j\lib\spec\json-20210307.jar" ui.RefactoredTimeAppGUI2

\<Home> Vancouver has been added.

\<School> Montreal has been added.  

\<Family> Seoul has been added.

Test has been added.

A city of index 3 has been shifted to index 2.

A city of index 2 has been shifted to index 1.

A city of index 1 has been shifted to index 0.

A city of index 0 has been shifted to index 1.

A city of index 1 has been shifted to index 2.

A city of index 2 has been shifted to index 3.

A city of index 2 has been set to Seoul.

A city of index 2 has been shifted to index 1.

A city of index 1 has been shifted to index 0.

A city of index 0 has been shifted to index 1.

A city of index 1 has been shifted to index 2.

A city of index 2 has been shifted to index 3.

\<School> Montreal has been removed.

Loading ./data/myCityList1.json...

\<Home> Vancouver has been added.

\<School> Montreal has been added.

\<Family> Seoul has been added.

\<WA, USA> Vancouver has been added.

Kelowna has been added.

\<label> name has been added.

All cities have been cleared.

\<Home> Vancouver has been added.

\<School> Montreal has been added.

\<Family> Seoul has been added.

\<WA, USA> Vancouver has been added.

Kelowna has been added.

\<label> name has been added.

A city of index 5 has been shifted to index 4.

Saving to ./data/myCityList1.json...

All cities have been cleared.

Loading ./data/myCityList3.json...

\<Trip> Melbourne has been added.

\<School> Montreal has been added.

\<Evita> Buenos Aires has been added.

\<Iceland Capital> Reykjavík has been added.

\<Southernmost> Puerto Williams has been added.

\<Dream Workplace> Singapore has been added.

Seattle has been added.

Tokyo has been added.

Regina has been added.

All cities have been cleared.

\<Trip> Melbourne has been added.

\<School> Montreal has been added.

\<Evita> Buenos Aires has been added.

\<Iceland Capital> Reykjavík has been added.

\<Southernmost> Puerto Williams has been added.

\<Dream Workplace> Singapore has been added.

Seattle has been added.

Tokyo has been added.

Regina has been added.

A city of index 1 has been shifted to index 0.

A city of index 3 has been shifted to index 2.

A city of index 2 has been shifted to index 1.

A city of index 1 has been shifted to index 0.

A city of index 0 has been shifted to index 1.

A city of index 1 has been shifted to index 2.

A city of index 2 has been shifted to index 3.

All cities have been cleared.


App closed successfully.

Process finished with exit code 0
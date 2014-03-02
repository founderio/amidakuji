![logo](http://dhelixnet.de/update/amidakuji/amidakuji.png)
## Amidakuji

#### About the Software
Amidakuji, aka Ghost Leg, is kinda like a lottery: It pairs up two predefined values.
It consists of several vertical lines, called "legs", which are connected via horizontal lines. The vertical lines connect two predefined values. For example: At the top there is the name of a player and at the bottom there is a Team. The horizontal lines are placed randomly.

##### How does it work?
You start with the first leg and follow the lines. If you encounter a horizontal line, move along the line and go to the next leg. And so on. Once you reach the bottom of a leg, you got two values paired up - the top value of the leg you started and the bottom value of the leg you finished with.

It may look as follows:

```
 Bob     Susie    Frank    Dabu
 #         #        #        #
 ###########        ##########
 #         #        #        #
 #         ##########        #
 ###########        #        #
 #         #        #        #
Team_A   Team_A   Team_B   Team_B

Results:
Bob -> Team_B
Susie -> Team_A
Frank -> Team_B
Dabu -> Team_A
```
Or:
```
 a    b    c    d
 #    #    #    #
 ######    #    #
 #    #    #    #
 #    ######    #
 #    #    ######
 #    #    #    #
win lose lose lose

Results:
a -> lose
b -> win
c -> lose
d -> lose
```
#### TODOs:
- [x] put Amidakuji in a scrollable area (scrolling with knob or click and drag)
- [x] added "CLEAR ALL" button
- [x] remove gdx freetype
- [ ] scrolling with mouse wheel
- [ ] put results into scrollable table
- [ ] a main menu
- [ ] new images for setup menu
- [ ] code clean-up + writing comments/javadocs
- [ ] enable user to define a "winner"
- [ ] animation of each path (make "winner" path(s) special)

#### News
To follow the latest news about Amidakuji, please visit the [Developer Blog](http://dhelixnet.de/de/devblog).

#### Contribution
If you want to contribute to the application feel free to create pull requests or send me suggestions (ideas and/or code).
Suggestions can also submitted to the [issue tracker](https://github.com/Persona-GD/amidakuji/issues).

#### Reporting Issues
To report issues, please use the [issue tracker](https://github.com/Persona-GD/amidakuji/issues).
But before posting an issue, please make sure of the following:
* You are not using the dev branch. The dev branch may contain some bugs causing the application to crash and will be fixed before merging to the main branch.
* Have you searched the issue tracker for your problem?

If none of those apply to you, feel free to post the issue in the issue tracker.
When reporting an issue, be sure to keep in mind the following tips (to make it easier to help you):
* use a clear title, e.g. "App ignores changed value of a leg"
* when writing the actual message
  * explain what you wanted to do in more detail, e.g. "After creating the values for one vertical line I changed the top value and then clicked the "START" button."
  * describe your problem, e.g. "Editing works fine, but on click of the "START" button, the application ignores any changes I just did and uses the old values instead."

#### Technology Used
The application uses the programming language Java and the library [libgdx](http://libgdx.badlogicgames.com/).

#### License
Amidakuji is licensed under the GPL version 3.

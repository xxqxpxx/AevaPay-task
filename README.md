Assignment - Android Developer
Instructions:
Build a native app that shows Jake Wharton Github repositories as list. Please use pagination
(request 15 items per request). During the request execution you can show a progress bar as
the last item in the list. If it's clear that there are no more items available, you should stop doing
requests and showing the progress bar. Notify the user if a request failed, but make sure that
the user will be able to see locally stored data.
What are we looking to test?
● The overall software architecture of the application
● The structure and quality of the code itself.
● The use of well-known patterns for Android development.
● Bonus points
○ Navigate to a details screen for each repo.
○ if you include tests in your solution.
The data
You can take required url like:
https://api.github.com/users/JakeWharton/repos?page=1&per_page=15
The Constraints
● Use Kotlin.
● Support API 19 and till the latest android version.
● Use a local database for storing data and make that data available offline.
● Use your Github account during development. When you are done, please give us the
link to that repo.
● We don’t expect you to be a designer. Something simple and intuitive is all we
need.
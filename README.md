# Android exercise
Write an app that allows a user to view tweets for a set of keywords with real-time updates.

### Available features

- App connects to tweeter API to fetch new tweets

- After getting data from server, populate on UI with latest tweet on top

- The list will automatically update as new tweets arrive.

- Currently adding only one tweet even though we get a list from twitter response.

- Twitter limits data to 15 results per request, and gives tweets for the last week.

- Schedule client to fetch data from server in regular intervals. Currently set to 5sec for quick updates.

- Saving downloaded data to database.

- Support offline access of tweets for user searched hashTags.

- Added ViewModels components to subscribe to the database and populate UI (TODO: Need to add full functionality)

#### Technologies used

- Android Recyclerview
- Room datbase to store user tweets
- Glide to load images
- Retrofit to fetch data from server
- RxJava2 to for async tasks, listeners, schedulers


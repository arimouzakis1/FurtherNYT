# FurtherNYT
Self-learning past NYT in tutorial content. See notes below for tutorial content.

**Week 9**
- Add GetBooksAsyncTask and InsertBooksAsyncTask to move database queries off of the main thread.
- Add AsyncTaskGetDelegate and AsyncTaskInsertDelegate interfaces to be used in the delegates for the tasks.
- Modify BookRecyclerFragment to use AsyncTasks (and implement the delegate interfaces).
- Remove .allowMainThreadQueries() from AppDatabase db instance builder.
- Handle the case for Articles not having an image causing crashes (ArticleAdapter and ArticleRecyclerFragment)

**Week 8**
- Convert Book class to a Room entity.
- Add BookDao interface defining database interactions for the Book entity.
- Add AppDatabase class containing a getInstance method to access a singleton instance of the database.
- Modify BookRecyclerFragment to save Books to the database after fetching from API, and then display books from the database.
- Update BookDetailActivity to retrieve book data from real database.

**Week 7**
- Use Volley instead of FakeAPI to get a JSON response for data.
- Change usage of ArrayList to List (to make it easier to deal with databases)
- Add res/values/secrets.xml to contain API keys
  - Normally you do NOT put a file like this on GitHub (sensitive information), but it's not that important for our apps at the moment.

**Week 6**
- Use data from JSON rather than premade objects from FakeDatabase.
- Use Gson to turn JSON into objects (which contain ArrayLists of Article/Book)
- Added TopStoriesResponse class to represent the whole JSON.
  - Modified Article class to match the fields of the JSON.
- Heavily modified FakeDatabase
  - No longer contains data in there by default; you must populate it using saveBooksToFakeDatabase
- **Implemented Books** - IMPORTANT: We will turn our focus from Articles to Books for the following weeks.

**Week 5**
- Fragments (new class ArticleRecyclerFragment, ProfileFragment).
  - And associated layout files for the fragments, fragment_article_recycler.xml and fragment_profile.xml
- Moved RecyclerView set up code from MainActivity to ArticleRecyclerFragment.
- Added BottomNavigationView, making it replace the Fragment depending on item selected.
  - Requires another layout file for the menu (see res > menu > bottom_nav_menu.xml)
- Added ProfileFragment (just exists to be another Fragment to swap to)

**Week 4**
- New class "ArticleAdapter" - our custom Adapter for the RecyclerView
- Refactor MainActivity.java to use RecyclerView
- Change activity_main.xml to be only a RecyclerView
- Same functionality as last week, but so much cleaner, efficient, and scalable.
- (NEW) Showing images (temporary way), check changes in Article, FakeDatabase, ArticleAdapter

**Week 3**
- Second activity, the detail activity that shows the user the details for a specific article.
- Explicit intent from the main activity to the detail activity, carrying over the article ID as an extra.
- Implicit intent on the share button on the main activity, to share the article.
- Using "FakeDatabase" to get Article objects to use as our data.

**Week 2**
- Main activity shows two articles in a ConstraintLayout, in a ScrollView.
- The article layout is defined in a separate file ```article.xml```
- We use ```<include>``` to reuse the article layout in our main activity
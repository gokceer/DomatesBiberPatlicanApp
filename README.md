# DomatesBiberPatlicanApp
Food Tracker and Recipe Adviser according to your ingredients

Our project is Domates Biber Patlican which can be used as food tracker and adviser. 
It brings lots of recipes according to your item selected if it is available in application database. 
Additionally, you can listen the recipes by clicking a listen button. Also, there is “Market Bul” button which provides connection to Google Maps and provide you to search for supermarkets around your location or other places. 
As it is said before, our app has two functionality: food tracker and recipe adviser.

# RECIPE ADVISER

If we talk about adviser function of the application, users will choose any item by searching from the toolbar at the top.
Toolbar involves connection of search, food tracker and menu options.
In search part, SearchView object is used to get its autocomplete property. 
User can see related items after typing the first two letters of the item, then choose the item from dropdown list menu. 

After item clicked from the dropdown menu list, it is put as a button on screen. 

To remove selected items(ingredients) from the screen, you have to make double click on the button.


![first](https://user-images.githubusercontent.com/32991962/54953230-cad23180-4f58-11e9-8efb-8c36c911c0a2.PNG)


After items selected and clicked “Hadi Pişirelim!” button, recipes or sorry not found layout will be open according to availability of related recipes in the database.

![second](https://user-images.githubusercontent.com/32991962/54953445-47fda680-4f59-11e9-98bd-d6c353988274.PNG)

In the creation of database, SQLite is used. Also to make updates, additions and deletions on database while adding recipes and items, DB Browser for SQLite was used.

When we click on one of the recipes, activity which is opened includes the ingredients and image of recipe, and how to make the recipe and “Dinle” button.
Also, you can read ingredients and how to make parts by swiping the text parts up and down. This property is provided by scroll view which is implemented into layout xml of the related activity page.

Ingredients of recipe and how to make the recipe can be listened via use of text to speech technology. If the user want to listen the recipe, she click to “DİNLE” button. Then, the recipe can be listened.

![third](https://user-images.githubusercontent.com/32991962/54953709-ef7ad900-4f59-11e9-8db4-645f2243d412.PNG)

We added the google maps to our application. User can see the markets on the around with this feature. We got Google API key to use Google Maps. We used maps, location, places and nearby services of Google Maps and the required implementations were done in build.gradle file. When the user press the "Market Bul (Find Market)" button, Google Maps automatically open and search the key "supermarkets". 

![fourth](https://user-images.githubusercontent.com/32991962/54953852-42549080-4f5a-11e9-918b-79f6c611c8bf.PNG)



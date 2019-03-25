# DomatesBiberPatlicanApp
Food Tracker and Recipe Adviser according to your ingredients

Our project is Domates Biber PatlIcan which can be used as food tracker and adviser. 
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

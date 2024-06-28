# Wagba-food-app
Wagba is a food delivery application designed to facilitate food delivery service to
customers. The app is customized for the Faculty of Engineering Community at Ainshams
University, and focuses on restaurants around Abdu-Basha and Abbaseya square. In order
to use the app, users must sign in with an active account @eng.asu.edu.eg to build a
trusted closed community.

Here are the functional requirements for the Wagba app:
1. The app allows customers to place orders for food delivery.
2. The app is only available to users with an active @eng.asu.edu.eg account.
3. The app is only available to users within a specific location (Abdu-Basha and
Abbaseya square).
4. The app has two delivery points (Gate 3 and 4) and two delivery times (12:00 noon
and 3:00 pm).
5. Orders must be placed before certain cut-off times in order to be eligible for the
specified delivery time (10:00 am for noon orders, 1:00 pm for afternoon orders).
6. The app is operated by students and is available only to the faculty of engineering
campus.
7. The cart page allows customers to view the selected items and display the total
cost of the items in the cart, including any applicable delivery costs and fees.

### Design and Architectural Description
The Wagba app has a total of seven activities and six fragments. The main menu activity,
which is displayed when the app is launched, includes the intro fragment, signup fragment,
and login fragment. The MainMenu activity includes the search fragment, account
fragment, and home fragment. The home fragment features a recycler view with a list of
available restaurants. When a user clicks on a restaurant from the list, the
restaurantActivity opens, displaying a recycler view with a list of available dishes. When a
user clicks on a dish, the dishorder activity opens, showing the image, description, and
price of the dish, as well as options for placing an order.

![image](https://github.com/ahmedsalahacc/wagabat-food-app/assets/51268352/5e72b2d3-13a9-441d-9431-f285a6ce32bf)

The cart activity, which is accessed via the cart button,
displays a summary of the selected items, along with
the total cost of the items and their delivery. It also
allows users to choose between noon and afternoon
delivery times, and to select a delivery location (either
at gate 3 or gate 4). The OrderHistory activity shows a
list of previous orders, sorted by date and time. When
a user clicks on an order, the OrderTracking activity
opens, displaying details about the order and its
tracking status at the restaurant.

In addition to the Android app, there is also a web app
called the Wagba Restaurant Center, which allows
each restaurant to view and track its incoming orders.
It also allows the restaurant manager to add new
dishes to the restaurant's menu, which will be
displayed to customers in the Android app.

The app utilizes a ROOM database to store
and manage user data. When a user registers or signs in to the app, the ROOM database is
initialized if the user's data is not already available. This helps to ensure that user data is
accurately stored and can be accessed even if the user is offline or experiencing
connectivity issues. The ROOM database is an efficient and secure way to manage user
data within the app.

In addition to using a ROOM database, the Wagba app also utilizes Google Cloud
Authentication and Google Firebase to register and authenticate users, as well as to store
and manage user data. These services help to ensure the security and privacy of user data,
and are an important part of the app's overall data management strategy. The combination
of the ROOM database and Google Cloud Authentication and Firebase helps to provide a
robust and reliable system for storing and accessing user data within the app.
### Screenshots
![image](https://github.com/ahmedsalahacc/wagabat-food-app/assets/51268352/a0280686-1f13-44e3-acdd-a033ace197a4)
![image](https://github.com/ahmedsalahacc/wagabat-food-app/assets/51268352/39cb0a41-e589-4ebd-aa1a-607e0758a364)
![image](https://github.com/ahmedsalahacc/wagabat-food-app/assets/51268352/48d2630e-5172-4b1f-a712-3e306eb16ab9)
![image](https://github.com/ahmedsalahacc/wagabat-food-app/assets/51268352/e02fe8c0-7e37-4568-8895-86f4b4029804)

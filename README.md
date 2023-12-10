<p align="center"><img src="https://i.imgur.com/lRmkSwE.png" alt="Elements"></p>

# My Food API Documentation

Welcome to the documentation for the Tsys My Food API. Below, you'll find information on how to navigate and use the available resources.

- [**Javadoc**](https://12manel123.github.io/tsys-my-food-api-1011): Explore comprehensive information about all controllers.
- [**Postman**](https://12manel123.github.io/tsys-my-food-api-1011): Test API requests conveniently using Postman.
<hr>

# Authentication Controller

## Loguin
- **Endpoint:** `POST /auth/signin`
- **Description:** Authenticates a user based on provided credentials and generates a JSON Web Token (JWT) for further authentication.
- **Permissions:** Public access.
- **JSON Example:**
```json
{
    "username": "Dani",
    "password": "password"
}
  ```

## Validate Token
- **Endpoint:** `GET /auth/validate-jwt`
- **Description:** Validates the provided JWT.
- **Permissions:** Requires a token

## Register
- **Endpoint:** `POST /auth/signup`
- **Description:** Registers a new user with the provided details.
- **Permissions:** Public access.
- **JSON Example:**
```json
{
    "username": "Dani",
    "password": "password",
    "email": "danidev@gmail.com"
}
  ```

# User Controller

## Get All Users
- **Endpoint:** `GET /api/v1/users`
- **Description:** Retrieves a paginated list of all users with simplified DTO representation.
- **Permissions:** Requires 'ADMIN' role.

## Get One User
- **Endpoint:** `GET /api/v1/user/{id}`
- **Description:** Retrieves a specific user by ID with simplified DTO representation.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the user to retrieve.

## Save User
- **Endpoint:** `POST /api/v1/user`
- **Description:** Creates a new user and assigns the default role 'USER' if available.
- **Permissions:** Requires 'ADMIN' role.
- **JSON Example:**
```json
{
    "email": "sergi@gmail.com",
    "password": "123456789",
    "username": "sergi",
    "created_at": "2023-01-01T12:00:00.000000Z",
    "updated_at": "2023-01-01T12:00:00.000000Z"
}
  ```

## Update User
- **Endpoint:** `PUT /api/v1/user/{id}`
- **Description:** Updates an existing user.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the user to update.
- **JSON Example:**
  ```json
  {
    "email":"david2Dev@gmail.com",
    "username": "david",
    "role":{
      
    	"id": 1,
    	"name": "ADMIN"
      }
  }
    ```


## Delete User
- **Endpoint:** `DELETE /api/v1/user/{id}`
- **Description:** Deletes a user by ID.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the user to delete.

# Order Controller 

## Get All Orders
- **Endpoint:** `GET /api/v1/orders`
- **Description:** Retrieves a paginated list of all orders with user details.
- **Permissions:** Requires 'ADMIN' role.
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of orders per page (default is 10).

## Get One Order
- **Endpoint:** `GET /api/v1/order/{id}`
- **Description:** Retrieves details of a specific order by its ID.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:**
  - `id` (Long): ID of the order to retrieve.

## Get All Orders For Chef
- **Endpoint:** `GET /api/v1/orders/chef`
- **Description:** Retrieves a paginated list of orders suitable for a chef, including associated dishes.
- **Permissions:** Requires 'CHEF' or 'ADMIN' role.
- **Query Parameters:**
  - `page` (int, optional): Page number for pagination (default is 0).
  - `size` (int, optional): Number of orders per page (default is 8).

## Get All Orders For User
- **Endpoint:** `GET /api/v1/orders/{userId}`
- **Description:** Retrieves a paginated list of orders for a specific user.
- **Permissions:** Requires 'USER' role.
- **Path Parameters:**
  - `userId` (Long): ID of the user for whom to retrieve orders.
- **Query Parameters:**
  - `page` (int, optional): Page number for pagination (default is 0).
  - `size` (int, optional): Number of orders per page (default is 10).

## Get Orders By Date Paginate
- **Endpoint:** `GET /api/v1/orders/date`
- **Description:** Retrieves a paginated list of orders based on specified date parameters.
- **Permissions:** Requires 'ADMIN' role.
- **Query Parameters:**
  - `page` (int, optional): Page number for pagination (default is 0).
  - `size` (int, optional): Number of orders per page (default is 10).
  - `year` (Integer, optional): The year to filter orders by.
  - `month` (Integer, optional): The month to filter orders by.
  - `day` (Integer, optional): The day to filter orders by.


## Save Order For User
- **Endpoint:** `POST /api/v1/order/{userId}`
- **Description:** Creates and saves a new order for the specified user.
- **Permissions:** Requires 'USER' role.
- **Path Parameters:**
  - `userId` (Long): ID of the user for whom to create the order.


## Update Order
- **Endpoint:** `PUT /api/v1/order/{id}`
- **Description:** Updates an existing order.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:**
  - `id` (Long): ID of the order to update.
- **Request Body Example:**
  ```json
    {
      "totalPrice": 1,
      "actualDate": "2023-12-01T14:30:06.262Z"
    }
  ```

## Mark Order As Maked
- **Endpoint:** `PUT /api/v1/order/markAsMaked/{id}`
- **Description:** Marks an order as "maked" (fulfilled).
- **Permissions:** Requires 'CHEF' or 'ADMIN' role.
- **Path Parameters:**
  - `id` (Long): ID of the order to mark as "maked".

## Update Order Slot
- **Endpoint:** `PUT /api/v1/order/finish/{orderId}/{slotId}`
- **Description:** Updates the slot of an order, confirms it, and calculates the total price.
- **Permissions:** Requires 'USER' role.
- **Path Parameters:**
  - `orderId` (Long): ID of the order to update.
  - `slotId` (Long): ID of the slot to associate with the order.

## Delete Order
- **Endpoint:** `DELETE /api/v1/order/{id}`
- **Description:** Deletes an order by its ID.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:**
  - `id` (Long): ID of the order to delete.


# ListOrders Controller

### Get All ListOrders (ADMIN)
- **Endpoint:** `GET /api/v1/list-orders`
- **Description:** Retrieves a paginated list of all list orders with user information excluding passwords.
- **Permissions:** Requires 'ADMIN' role.
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of list orders per page (default is 10).

### Get One ListOrder
- **Endpoint:** `GET /api/v1/list-order/{id}`
- **Description:** Retrieves details of a specific list order by its ID.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the list order to retrieve.

### Get ListOrders By Order ID
- **Endpoint:** `GET /api/v1/list-order/orderid/{orderId}`
- **Description:** Retrieves a paginated list of list orders associated with a specific order ID.
- **Permissions:** Public access.
- **Path Parameters:**
  - `orderId` (Long): ID of the order to retrieve list orders for.
- **Query Parameters:**
  - `page` (int, optional): Page number for pagination (default is 0).
  - `size` (int, optional): Number of list orders per page (default is 10).

### Save ListOrder by Order ID and Item ID
- **Endpoint:** `POST /api/v1/list-order/{orderid}/{itemid}`
- **Description:** Creates a new list order based on the provided order ID, item ID, and item type (dish or menu).
- **Permissions:** Public access.
- **Path Parameters:**
  - `orderid` (Long): ID of the order associated with the list order.
  - `itemid` (Long): ID of the item associated with the list order (dish or menu).
- **Request Parameters:**
  - `itemType` (String): Type of the item ("dish" or "menu").

### Update ListOrder
- **Endpoint:** `PUT /api/v1/list-order/{id}`
- **Description:** Updates an existing list order.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the list order to update.
- **Request Body Example:**
```json
    {
      "id": 1,
      "menu": null,
      "dish": {
            "id": 1,
            "name": "Croquetas de Boletus",
            "description": "Una tapa de apetitosas croquetas con trozos de boletus",
            "image": "https://i.imgur.com/croquetas.png",
            "price": 3.5,
            "category": "APPETIZER",
            "visible": true
        },
      "order": {
        "id": 1
        }
    }
    
```

### Delete ListOrder
- **Endpoint:** `DELETE /api/v1/list-order/{id}`
- **Description:** Deletes a list order by ID.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the list order to delete.


# Menu Controller

### Get All Menus
- **Endpoint:** `GET /api/v1/menus`
- **Description:** Retrieves a paginated list of all menus.
- **Permissions:** Requires 'ADMIN' role.
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of menus per page (default is 10).

### Get All Visible Menus
- **Endpoint:** `GET /api/v1/allVisibleMenus`
- **Description:** Retrieves a list of all visible menus, filtering out menus with invisible dishes.
- **Permissions:** Requires 'USER' role.

### Get One Menu (ADMIN)
- **Endpoint:** `GET /api/v1/menu/{id}`
- **Description:** Retrieves details of a specific menu by its ID.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the menu to retrieve.

### Create Menu
- **Endpoint:** `POST /api/v1/menu`
- **Description:** Creates a new menu.
- **JSON Example:**
```json
{
  "appetizer": {
            "id": 2,
            "name": "Tabla de Quesos",
            "description": "Un plato con la mejor variedad de quesos",
            "image": "https://i.imgur.com/tablaqueso.png",
            "price": 6.5,
            "category": "APPETIZER",
            "visible": true
        },
  "first": {
            "id": 1,
            "name": "Macarrones a la Boloñesa",
            "description": "Contiene salsa de tomate ",
            "image": "https://i.imgur.com/macarrones.png",
            "price": 5.0,
            "category": "FIRST",
            "visible": true
        },
  "second":{
            "id": 3,
            "name": "Meloso de Ternera",
            "description": "Acompañamiento con patatas y pimiento",
            "image": "https://i.imgur.com/ternera.png",
            "price": 7.5,
            "category": "SECOND",
            "visible": true
        },
  "dessert": {
            "id": 4,
            "name": "Crema Catalana",
            "description": "Con azucar quemado por encima",
            "image": "https://i.imgur.com/crema.png",
            "price": 7.5,
            "category": "DESSERT",
            "visible": true
        },
  "visible": true
}
```

### Update Menu
- **Endpoint:** `PUT /api/v1/menu/{id}`
- **Description:** Updates an existing menu.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the menu to update.
- **Request Body Example:**
```json
{
  "appetizer": {
            "id": 5,
            "name": "Croquetas de Jamón",
            "description": "Una tapa de apetitosas croquetas con trozos de jamón",
            "image": "https://i.imgur.com/croquetas.png",
            "price": 3.5,
            "category": "APPETIZER",
            "visible": true
        },
  "first": {
            "id": 1,
            "name": "Macarrones a la Boloñesa",
            "description": "Contiene salsa de tomate ",
            "image": "https://i.imgur.com/macarrones.png",
            "price": 5.0,
            "category": "FIRST",
            "visible": true
        },
  "second":{
            "id": 3,
            "name": "Meloso de Ternera",
            "description": "Acompañamiento con patatas y pimiento",
            "image": "https://i.imgur.com/ternera.png",
            "price": 7.5,
            "category": "SECOND",
            "visible": true
        },
  "dessert": {
            "id": 4,
            "name": "Crema Catalana",
            "description": "Con azucar quemado por encima",
            "image": "https://i.imgur.com/crema.png",
            "price": 7.5,
            "category": "DESSERT",
            "visible": true
        },
  "visible": true
}
```

### Toggle Menu Visibility
- **Endpoint:** `PUT /api/v1/menu/changeVisibility/{id}`
- **Description:** Toggles the visibility status of a menu.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the menu to update.

### Delete Menu
- **Endpoint:** `DELETE /api/v1/menu/{id}`
- **Description:** Deletes a menu by ID.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the menu to delete.

# Dish Controller

### Get All Dishes
- **Endpoint:** `GET /api/v1/dishes`
- **Description:** Retrieves a paginated list of all dishes.
- **Permissions:** Requires 'ADMIN' role.
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of dishes per page (default is 10).

### Get All Visible Dishes
- **Endpoint:** `GET /api/v1/dishes/visible`
- **Description:** Retrieves a paginated list of all visible dishes.
- **Permissions:** Requires 'USER' role.
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of visible dishes per page (default is 10).

### Get One Dish
- **Endpoint:** `GET /api/v1/dish/{id}`
- **Description:** Retrieves details of a specific dish by its ID.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the dish to retrieve.

### Get Dishes by Name
- **Endpoint:** `GET /api/v1/dish/byName/{name}`
- **Description:** Retrieves dishes by name, considering case-insensitive matching.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `name` (String): Name of the dishes to retrieve.

### Get Visible Dishes by Name
- **Endpoint:** `GET /api/v1/dish/visibleByName/{name}`
- **Description:** Retrieves visible dishes by name, considering case-insensitive matching.
- **Permissions:** Requires 'USER' role.
- **Path Parameters:** `name` (String): Name of the dishes to retrieve.

### Get Dishes by Category
- **Endpoint:** `GET /api/v1/dishes/byCategory/{category}`
- **Description:** Retrieves dishes by category.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:**
  - `category` (String): Category of dishes to retrieve. Only accepts APPETIZER, FIRST, SECOND or DESSERT
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of dishes per page (default is 10).

### Get Visible Dishes by Category
- **Endpoint:** `GET /api/v1/dishes/visibleByCategory/{category}`
- **Description:** Retrieves visible dishes by category.
- **Permissions:** Requires 'USER' role.
- **Path Parameters:**
  - `category` (String): Category of dishes to retrieve. Only accepts APPETIZER, FIRST, SECOND or DESSERT
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of visible dishes per page (default is 10).

### Create Dish
- **Endpoint:** `POST /api/v1/dish`
- **Description:** Creates a new dish.
- **Permissions:** Requires 'ADMIN' role.
- **JSON Example:**
```json
{
            "name": "Croquetas de Jamón",
            "description": "Una tapa de apetitosas croquetas con trozos de jamón",
            "image": "https://i.imgur.com/croquetas.png",
            "price": 3.5,
            "category": "APPETIZER",
            "visible": true
}
```

### Update Dish
- **Endpoint:** `PUT /api/v1/dish/{id}`
- **Description:** Updates an existing dish.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the dish to update.
- **Request Body Example:**
```json
{
            "id": 1,
            "name": "Croquetas de Boletus",
            "description": "Una tapa de apetitosas croquetas con trozos de boletus",
            "image": "https://i.imgur.com/croquetas.png",
            "price": 3.5,
            "category": "APPETIZER",
            "visible": true
}
```

### Change Dish Visibility
- **Endpoint:** `PUT /api/v1/dish/changeVisibility/{id}`
- **Description:** Toggles the visibility status of a dish.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the dish to update.

### Delete Dish
- **Endpoint:** `DELETE /api/v1/dish/{id}`
- **Description:** Deletes a dish by ID.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the dish to delete.

# Attribute-Dish Controller

### Get All Atributes with Dishes
- **Endpoint:** `GET /api/v1/atributs`
- **Description:** Gets a paginated list of all relationships to the dish associated with that attribute.
- **Permissions:** Requires 'ADMIN' role.
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of relationships per page (default is 10).

### Get One Attribute with Dishes
- **Endpoint:** `GET /api/v1/atribut/{id}`
- **Description:**  Gets an attribute with the relationships to the plate associated with that attribute.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `id` (Long): ID of the relationship to retrieve.
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of relationships per page (default is 1).

### Get All Atributes by Name with Dishes
- **Endpoint:** `GET /api/v1/atribut/ByName/{attributes}`
- **Description:**  Gets a paginated list of all relationships to the dish associated with that name for atribute.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:** `attributes` (String): Attributes to filter by. Accepted values are: CELIAC, LACTOSE, VEGAN, VEGETARIAN, NUTS.
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of relationships per page (default is 10).

### Get All Atributes by Name with Dishes for User
- **Endpoint:** `GET /api/v1/atribut/visible/{atribut}/dishes`
- **Description:** Gets a paginated list of all relationships to the dish visibles associated with that name for atribute.
- **Permissions:** Requires 'USER' role.
- **Path Parameters:** `atribut` (String): Attribute to filter dishes by. Accepted values are: CELIAC, LACTOSE, VEGAN, VEGETARIAN, NUTS.
- **Query Parameters:**
  - `page` (int, optional): Page number (default is 0).
  - `size` (int, optional): Number of dishes per page (default is 10).

### Add Attribute-Dish Relationship to Dish
- **Endpoint:** `POST /api/v1/atribut/{atributDishId}/dish/{dishId}`
- **Description:** Associates an existing attribute-dish relationship with a dish.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:**
  - `atributDishId` (Long): ID of the attribute-dish relationship to associate.
  - `dishId` (Long): ID of the dish to associate.

### Delete Attribute-Dish Relationship from Dish
- **Endpoint:** `DELETE /api/v1/atribut/{atributId}/dish/{dishId}`
- **Description:** Deletes the relationship between an attribute and a dish.
- **Permissions:** Requires 'ADMIN' role.
- **Path Parameters:**
  - `atributId` (Long): ID of the attribute-dish relationship to delete.
  - `dishId` (Long): ID of the dish associated with the attribute-dish relationship.

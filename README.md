## E-COMMERCE PROJECT ☕

### Hi everyone, i would like to present a project that was done by my internship group, this project consists of a ecommerce based on a microservice architecture. Well, we have three microservice connect by **feign** and they all have great importance in the operation of the application.

### MS-ORDERS 🚲
**Endpoints:**<br>
>CREATE_ORDER: `localhost:8000/orders`<br>
>GET_ORDER_BY_ID: `localhost:8000/orders/{id}`<br>
>CANCEL_ORDER: `localhost:8000/orders/{id}/cancel`<br>
>UPDATE_ORDER: `localhost:8000/orders/{id}`<br>
>GET_ALL_ORDERS: `localhost:8000/orders`<br>

#### Request payload to create order:

~~~~
{
    "products": [
        {
            "productId": 6,
            "quantity": 2
        },
        {
            "productId": 3,
            "quantity": 1
        }
    ],
    "address": {
        "street": "Av. do Alemão",
        "number": 2,
        "postalCode": "65919-2701"
    },
    "paymentMethod": "PIX"
}
~~~~

> [!NOTE]<br>
> product IDs in products list must exists in database and the `postalCode` can be real beacause it uses [geradordecep](https://www.geradordecep.com.br/) API to find the real postal code and bring all the informations.<br>
> `PaymentMethod` field can only be `CREDIT_CARD, BANK_TRANSFER, CRYPTOCURRENCY, GIFT_CARD, PIX or OTHER`.

#### Resquest payload to cancel order:

~~~~
{
    "cancelReason": "message"
}
~~~~

> [!IMPORTANT]<br>
> If the order that you want to cancel has `status` field as `SENT` or the creation date exceeds **90 days**, you cannot cancel that order.
> The payload of the update endpoint is the same as the creation one, but if the order you want to update has a status of `SENT` or `CANCELED`, you can update it.

### MS-PRODUCTS 👜

**Endpoints:**<br>
>CREATE_PRODUCT: `localhost:8080/products`<br>
>GET_PRODUCT_BY_ID: `localhost:8080/products/{id}`<br>
>DELETE_PRODUCT: `localhost:8080/products/{id}`<br>
>UPDATE_PRODUCT: `localhost:8080/products/{id}`<br>
>GET_ALL_PRODUCTS: `localhost:8080/products`<br>

#### Request payload to insert product:

~~~~
{
    "name": "shadow t-shirt2",
    "description": "very well tshit",
    "value": 15
}
~~~~

>[!NOTE]<br>
> The `description` must be more than 10 characteres. <br>
> No field can be empty or null. <br>
> `Update payload` is the same as create product but you have to add an id.

### MS-FEEDBACKS 🔔

**Endpoints:**<br>
>CREATE_FEEDBACK: `localhost:8100/feedbacks`<br>
>GET_FEEDBACK_BY_ID: `localhost:8100/feedbacks/{id}`<br>
>DELETE_FEEDBACK: `localhost:8100/feedbacks/{id}`<br>
>UPDATE_FEEDBACK: `localhost:8100/feedbacks/{id}`<br>
>GET_ALL_FEEDBACKS: `localhost:8100/feedbacks`<br>

#### Request payload to create a feedback:

~~~~
{
    "scale": "SATISFIED",
    "comment": "very good product",
    "orderId": 80
}
~~~~

>[!NOTE]<br>
> The field `orderId` must represent a real id in order database. <br>
> `Update payload` is the same as create product but you have to add an id. <br>
> The rest of endpoint don't require any type of payload.

>[!WARNING]<br>
> If you choose to run the project manually, i recommend running the order microservice first to avoid problems.

### Tools used in this app 🔧:
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

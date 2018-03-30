# JavaEE server API
## Description
This is the list of server API used for Uniquira

> *All the responsses are wrapped in special construction for convenience*
```
{
    isSuccess: boolean,
    body: [response]
}
```

## API
* ### `/login`
**Method**: `POST`  
**Body:**
```
{
    email: string,
    password: string
}
```
**Response:**
```
{
    userId: number,
    token: string,
    firstName: string,
    lastName: string,
    email: string
}
```

* ### `/question/ask`
**Method:** `POST`
**Body:**
```
{
    question: string,
    description: string,
    userId: number
}
```
**Response:**
```
{
    question: string,
    description: string,
    userId: number,
    id: number
}
```
* ### `/question/list/{page}`
**Method:** `GET`
**Response:**
```
[
    {
        rating: number,
        question: string,
        description: string,
        timestamp: number,
        answersCount: number,
        id: number,
        bestAnswer: {
            id: number,
            answer: string,
            timestamp: string,
            rating: number
        }
    }
]
```
* ### `/question/{id}`
**Method:** `GET`
**Response:**
```
{
    question: string,
    description: string,
    rating: number,
    timestamp: number,
    id: number,
    answers: [
        {
            id: number,
            answer: string,
            timestamp: string,
            rating: number
        }
    ]
}
```

* ### `/question/vote/{id}?isup`
**Query params:** `isup` {*boolean*} - is it positive vote or negative    
**Method:** `GET`    
**Response:**
```
{
    question: string,
    description: string,
    rating: number,
    timestamp: number,
    id: number,
    rating: number
}
```

* ### `/answer`
**Method:** `POST`
**Body:**
```
{
    questionId: number,
    answer: string,
    userId: number,
    id: number

}
```

* ### `/answer/vote?isup`
**Query params:** `isup` {*boolean*} - is it positive vote or negative   
**Method:** `GET`   
**Response:**
```
{
    id: number,
    answer: string,
    timestamp: string,
    rating: number
}
```

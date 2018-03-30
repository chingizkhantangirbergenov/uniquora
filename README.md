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
* ### `/api/login`
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

## API
* ### `/api/register`
**Method**: `POST`  
**Body:**
```
{
    email: string,
    firstName: string,
    lastName: string
}
```
**Response:**
```
{
    password: string
}
```

* ### `/api/gpa`
**Method:** `POST`
**Body:**
```
{
    userId: number,
    token: string
}
```
**Response:**
```
{
    gpa: number
}
```
* ### `/api/completed_courses`
**Method:** `POST`
**Body:**
```
{
    userId: number,
    token: string
}
```
**Response:**
```
[
    {
        abbr: string,
        name: string,
        grade: string,
        term: string,
        credit: number
    }
]
```
* ### `/api/current_courses`
**Method:** `POST`
**Body:**
```
{
    userId: number,
    token: string
}
```
**Response:**
```
[
    {
        abbr: string,
        name: string
    }
]
```

* ### `/api/feedback/edit`
**Method:** `POST`    
**Body:**
```
{
    courseId: number,
    feedback: string,
    userId: number,
    token: string
}
```
* ### `/api/courses`
**Method:** `POST`
**Body:**
```
{
    name: string,
    userId: number,
    token: string
}
```
**Response:**
```
[
    {
        name: string,
        abbr: string,
        id: number
    }
]
```
* ### `/api/users`
**Method:** `POST`
**Body:**
```
{
    name: string,
    userId: number,
    token: string
}
```
**Response:**
```
[
    {
        firstName: string,
        lastName: string,
        id: number,
        major: string
    }
]
```

* ### `/api/course`
**Method:** `POST`
**Body:**
```
{
    id: number,
    userId: number,
    token: string
}
```
**Response:**
```
[
    {
        description: string,
        credit: number,
        id: number,
        name: string,
        abbr: string,
        average: number,
        feedback: [
            string,
            string,
            string
        ]
    }
]
```

# Blog API V1
## Simple blog api V1 based on Spring Boot

### Installation guide

#### 1. Run docker command to start postgreSQL database:
``docker run --name=main-pg --env=POSTGRES_USER=postgres --env=POSTGRES_PASSWORD=postgres --env=POSRGRES_DB=postgres -p 5432:5432 postgres``

#### 2. Run BlogJpaApiApplication.class

###### Tables will create automatically by JPA / HIBERNATE

###### Path URL http://localhost:8080/api/v1



### Postman commands:

###### GET /api/v1/posts - get all posts
###### POST /api/v1/posts - add new post
###### PUT /api/v1/posts/{id} - modify post by "id"
###### DELETE /api/v1/posts/{id} - delete post by "id"
###### GET /api/v1/posts/?title={title} - find post by "title"
###### GET /api/v1/posts/?sort=title - sort posts by title
###### GET /api/v1/posts/star - get all TOP posts
###### PUT /api/v1/posts/{id}/star - mark post as TOP
###### DELETE /api/v1/posts/{id}/star - unmark post as TOP
###### PUT /api/v1/posts/{id}/tags?tag={tag1, tag2, ...} - add tag(s) to post
###### DELETE /api/v1/posts/{id}/tags?tag={tag1, tag2, ...} - remove tag(s) from post
###### GET /api/v1/posts/tag?tag={tag1, tag2, ...} - get posts with tag(s)
###### POST /api/v1/posts/{postId}/comments - add new comment by "postId"
###### GET /api/v1/posts/{postId}/comments - get all comments by "postId"
###### GET /api/v1/posts/{postId}/comment/{commentId} - get comment by "postId" and "commentId"
###### DELETE /api/v1/posts/{postId}/comment/{commentId} - remove comment by "postId" and "commentId"
###### GET /api/v1/posts/{postId}/full - get post with all comments by "postId"
###### GET /api/v1/posts/tags - get all tags
###### POST /api/v1/posts/tags - save tag to DB
###### DELETE /api/v1/posts/tags/{tagID} - remove tag from DB
###### GET /api/v1/posts/{postId}/tags - get all tags by "postId"






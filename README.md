## 项目简介
GeoSSMS-BOOT

```
.
├── README.md
├── files
│   ├── 1f646b5e96804c90844d0465d6865c45.xlsx
│   └── f1c27812916d444e93d3838cc42c7c47.xlsx
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── geossms
│   │   │               ├── GeoSsmsApplication.java
│   │   │               ├── common
│   │   │               │   ├── Constants.java
│   │   │               │   ├── Result.java
│   │   │               │   └── RoleEnum.java
│   │   │               ├── config
│   │   │               │   ├── AuthAccess.java
│   │   │               │   ├── CorsConfig.java
│   │   │               │   ├── InterceptorConfig.java
│   │   │               │   ├── MybatisPlusConfig.java
│   │   │               │   ├── SwaggerConfig.java
│   │   │               │   └── interceptor
│   │   │               │       └── JwtInterceptor.java
│   │   │               ├── controller
│   │   │               │   ├── ActivityController.java
│   │   │               │   ├── ArticleController.java
│   │   │               │   ├── BuildingController.java
│   │   │               │   ├── ChatGPTController.java
│   │   │               │   ├── ChildrenController.java
│   │   │               │   ├── CommentController.java
│   │   │               │   ├── EchartsController.java
│   │   │               │   ├── FileController.java
│   │   │               │   ├── MenuController.java
│   │   │               │   ├── RoleController.java
│   │   │               │   ├── TypicalController.java
│   │   │               │   ├── UserController.java
│   │   │               │   └── dto
│   │   │               │       ├── UserDTO.java
│   │   │               │       └── UserPasswordDTO.java
│   │   │               ├── entity
│   │   │               │   ├── Activity.java
│   │   │               │   ├── Article.java
│   │   │               │   ├── Building.java
│   │   │               │   ├── Children.java
│   │   │               │   ├── Comment.java
│   │   │               │   ├── Dict.java
│   │   │               │   ├── Files.java
│   │   │               │   ├── GPTParams.java
│   │   │               │   ├── Menu.java
│   │   │               │   ├── ParamsForm.java
│   │   │               │   ├── PredictParams.java
│   │   │               │   ├── Role.java
│   │   │               │   ├── RoleMenu.java
│   │   │               │   ├── Typical.java
│   │   │               │   └── User.java
│   │   │               ├── exception
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   └── ServiceException.java
│   │   │               ├── mapper
│   │   │               │   ├── ActivityMapper.java
│   │   │               │   ├── ArticleMapper.java
│   │   │               │   ├── BuildingMapper.java
│   │   │               │   ├── ChildrenMapper.java
│   │   │               │   ├── CommentMapper.java
│   │   │               │   ├── DictMapper.java
│   │   │               │   ├── FileMapper.java
│   │   │               │   ├── MenuMapper.java
│   │   │               │   ├── RoleMapper.java
│   │   │               │   ├── RoleMenuMapper.java
│   │   │               │   ├── TypicalMapper.java
│   │   │               │   └── UserMapper.java
│   │   │               ├── service
│   │   │               │   ├── IActivityService.java
│   │   │               │   ├── IArticleService.java
│   │   │               │   ├── IBuildingService.java
│   │   │               │   ├── IChildrenService.java
│   │   │               │   ├── ICommentService.java
│   │   │               │   ├── IMenuService.java
│   │   │               │   ├── IRoleService.java
│   │   │               │   ├── ITypicalService.java
│   │   │               │   ├── IUserService.java
│   │   │               │   └── impl
│   │   │               │       ├── ActivityServiceImpl.java
│   │   │               │       ├── ArticleServiceImpl.java
│   │   │               │       ├── BuildingServiceImpl.java
│   │   │               │       ├── ChildrenServiceImpl.java
│   │   │               │       ├── CommentServiceImpl.java
│   │   │               │       ├── MenuServiceImpl.java
│   │   │               │       ├── RoleServiceImpl.java
│   │   │               │       ├── TypicalServiceImpl.java
│   │   │               │       └── UserServiceImpl.java
│   │   │               └── utils
│   │   │                   ├── CodeGenerator.java
│   │   │                   └── TokenUtils.java
│   │   └── resources
│   │       ├── application.yml
│   │       └── mapper
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── geossms
│                       └── GeoSsmsApplicationTests.java
51 directories, 176 files
```
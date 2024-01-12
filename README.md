## 项目简介
GeoSSMS-BOOT

```
.
├── 1.txt
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
│   │           ├── ActivityMapper.xml
│   │           ├── ArticleMapper.xml
│   │           ├── BuildingMapper.xml
│   │           ├── ChildrenMapper.xml
│   │           ├── CommentMapper.xml
│   │           ├── MenuMapper.xml
│   │           ├── RoleMapper.xml
│   │           ├── TypicalMapper.xml
│   │           └── UserMapper.xml
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── geossms
│                       └── GeoSsmsApplicationTests.java
└── target
    ├── classes
    │   ├── application.yml
    │   ├── com
    │   │   └── example
    │   │       └── geossms
    │   │           ├── GeoSsmsApplication.class
    │   │           ├── common
    │   │           │   ├── Constants.class
    │   │           │   ├── Result.class
    │   │           │   └── RoleEnum.class
    │   │           ├── config
    │   │           │   ├── AuthAccess.class
    │   │           │   ├── CorsConfig.class
    │   │           │   ├── InterceptorConfig.class
    │   │           │   ├── MybatisPlusConfig.class
    │   │           │   ├── SwaggerConfig.class
    │   │           │   └── interceptor
    │   │           │       └── JwtInterceptor.class
    │   │           ├── controller
    │   │           │   ├── ActivityController.class
    │   │           │   ├── ArticleController.class
    │   │           │   ├── BuildingController.class
    │   │           │   ├── ChatGPTController.class
    │   │           │   ├── ChildrenController.class
    │   │           │   ├── CommentController.class
    │   │           │   ├── EchartsController$1.class
    │   │           │   ├── EchartsController.class
    │   │           │   ├── FileController.class
    │   │           │   ├── MenuController.class
    │   │           │   ├── RoleController.class
    │   │           │   ├── TypicalController.class
    │   │           │   ├── UserController.class
    │   │           │   └── dto
    │   │           │       ├── UserDTO.class
    │   │           │       └── UserPasswordDTO.class
    │   │           ├── entity
    │   │           │   ├── Activity.class
    │   │           │   ├── Article.class
    │   │           │   ├── Building.class
    │   │           │   ├── Children.class
    │   │           │   ├── Comment.class
    │   │           │   ├── Dict.class
    │   │           │   ├── Files.class
    │   │           │   ├── GPTParams.class
    │   │           │   ├── Menu.class
    │   │           │   ├── ParamsForm.class
    │   │           │   ├── PredictParams.class
    │   │           │   ├── Role.class
    │   │           │   ├── RoleMenu.class
    │   │           │   ├── Typical.class
    │   │           │   └── User.class
    │   │           ├── exception
    │   │           │   ├── GlobalExceptionHandler.class
    │   │           │   └── ServiceException.class
    │   │           ├── mapper
    │   │           │   ├── ActivityMapper.class
    │   │           │   ├── ArticleMapper.class
    │   │           │   ├── BuildingMapper.class
    │   │           │   ├── ChildrenMapper.class
    │   │           │   ├── CommentMapper.class
    │   │           │   ├── DictMapper.class
    │   │           │   ├── FileMapper.class
    │   │           │   ├── MenuMapper.class
    │   │           │   ├── RoleMapper.class
    │   │           │   ├── RoleMenuMapper.class
    │   │           │   ├── TypicalMapper.class
    │   │           │   └── UserMapper.class
    │   │           ├── service
    │   │           │   ├── IActivityService.class
    │   │           │   ├── IArticleService.class
    │   │           │   ├── IBuildingService.class
    │   │           │   ├── IChildrenService.class
    │   │           │   ├── ICommentService.class
    │   │           │   ├── IMenuService.class
    │   │           │   ├── IRoleService.class
    │   │           │   ├── ITypicalService.class
    │   │           │   ├── IUserService.class
    │   │           │   └── impl
    │   │           │       ├── ActivityServiceImpl.class
    │   │           │       ├── ArticleServiceImpl.class
    │   │           │       ├── BuildingServiceImpl.class
    │   │           │       ├── ChildrenServiceImpl.class
    │   │           │       ├── CommentServiceImpl.class
    │   │           │       ├── MenuServiceImpl.class
    │   │           │       ├── RoleServiceImpl.class
    │   │           │       ├── TypicalServiceImpl.class
    │   │           │       └── UserServiceImpl.class
    │   │           └── utils
    │   │               ├── CodeGenerator.class
    │   │               └── TokenUtils.class
    │   └── mapper
    │       ├── ActivityMapper.xml
    │       ├── ArticleMapper.xml
    │       ├── BuildingMapper.xml
    │       ├── ChildrenMapper.xml
    │       ├── CommentMapper.xml
    │       ├── MenuMapper.xml
    │       ├── RoleMapper.xml
    │       ├── TypicalMapper.xml
    │       └── UserMapper.xml
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    └── test-classes
        └── com
            └── example
                └── geossms
                    └── GeoSsmsApplicationTests.class

51 directories, 176 files
```